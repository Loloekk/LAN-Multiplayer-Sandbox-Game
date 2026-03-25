package io.github.sandboxGame.controler.playerNetworkData;

import io.github.sandboxGame.common.BlockState;
import io.github.sandboxGame.common.Config;
import io.github.sandboxGame.controler.network.PacketServerToClient.*;
import io.github.sandboxGame.logic.actions.GameState;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.esotericsoftware.kryonet.Connection;
import io.github.sandboxGame.logic.equipment.Item;
import io.github.sandboxGame.logic.players.ObservablePhysicalPlayer;
import io.github.sandboxGame.logic.players.PhysicalPlayer;

import static java.lang.Math.abs;

public class PlayerData {
    public static int CHUNK_WIDTH_RADIUS = Config.CHUNK_WIDTH_RADIUS;
    public static int CHUNK_HEIGHT_RADIUS = Config.CHUNK_HEIGHT_RADIUS;
    private GameState gameState;
    private int playerId;
    private Connection conn;
    private ItemHolderObserver itemHolderObserver;
    private PhysicalPlayerObserver physicalPlayerObserver;

    public Map<Integer, Chunk> chunks = new HashMap<>();
    public Map<Integer, PacketPlayerState> players = new HashMap<>();
    public Map<Integer, PacketMobState> mobs = new HashMap<>();
    public Map<Integer, PacketProjectileState> projectiles = new HashMap<>();
    private List<Object> bufferTCP = new ArrayList<>();
//    private List<Object> bufforUDP = new ArrayList<>();
    public PlayerData(Connection conn, GameState gameState, int playerId)
    {
        this.conn = conn;
        this.gameState = gameState;
        this.playerId = playerId;
        this.itemHolderObserver = new ItemHolderObserverComm(bufferTCP);
        this.physicalPlayerObserver = new PhysicalPlayerObserverComm(bufferTCP);
    }
    public void actualize()
    {
        float x = gameState.activePlayers().get(playerId).getIntegerPosition().x();
        float y = gameState.activePlayers().get(playerId).getIntegerPosition().y();
        int X = Chunk.getX(x);
        int Y = Chunk.getY(y);
        for(int i = -CHUNK_WIDTH_RADIUS; i <= CHUNK_WIDTH_RADIUS; i ++)
            for(int j = -CHUNK_HEIGHT_RADIUS; j <= CHUNK_HEIGHT_RADIUS; j ++)
            {
                int chunkX = X + i*Chunk.DEFAULT_WIDTH;
                int chunkY = Y + j*Chunk.DEFAULT_HEIGHT;
                int chunkId = Chunk.getId(chunkX,chunkY);
                if(!chunks.containsKey(chunkId))
                {
                    chunks.put(chunkId,new Chunk(chunkX,chunkY));
                    ArrayList<BlockState> data = chunks.get(chunkId).initialize(gameState.grid());
                    bufferTCP.add(data);
                    //System.out.println("wysylam bloki");
                }
            }
        ArrayList<Chunk> chunksToDelete = new ArrayList<>();
        for(Chunk chunk : chunks.values())
        {
            if(chunk.zeroX < X - CHUNK_WIDTH_RADIUS*Chunk.DEFAULT_WIDTH ||
                chunk.zeroX > X + CHUNK_WIDTH_RADIUS*Chunk.DEFAULT_WIDTH ||
                chunk.zeroY < Y - CHUNK_HEIGHT_RADIUS*Chunk.DEFAULT_HEIGHT ||
                chunk.zeroY > Y + CHUNK_HEIGHT_RADIUS*Chunk.DEFAULT_HEIGHT
            )
            {
                chunksToDelete.add(chunk);
            }
            else
            {
                ArrayList<BlockState> data = chunk.getDifferences(gameState.grid());
                if(data.size()>0) {
                    bufferTCP.add(data);
                    //System.out.println("wysylam bloki");
                }
            }
        }
        for(Chunk chunk : chunksToDelete)
        {
            chunks.remove(chunk.getId());
        }
        List<Integer> playersToDelete = new ArrayList<>();
        for(Map.Entry<Integer, PacketPlayerState> entry : players.entrySet())
        {
            PacketPlayerState pla = entry.getValue();
            if(abs(x-pla.x) > Chunk.DEFAULT_WIDTH*CHUNK_WIDTH_RADIUS ||
                abs(y-pla.y) > Chunk.DEFAULT_HEIGHT*CHUNK_HEIGHT_RADIUS ||
                (!gameState.activePlayers().isActive(pla.id)))
                // conditions when we remove player
            {
                PacketDisappearPlayer dis = new PacketDisappearPlayer();
                dis.id = pla.id;
                bufferTCP.add(dis);
                playersToDelete.add(pla.id);
                PhysicalPlayer player = gameState.activePlayers().get(pla.id);
                if(player instanceof ObservablePhysicalPlayer observablePlayer)
                {
                    observablePlayer.removeObserver(physicalPlayerObserver);
                }
            }
        }
        List<Integer> mobsToDelete = new ArrayList<>();
        for(var entry : mobs.entrySet()){
            PacketMobState mob = entry.getValue();
            if(abs(x - mob.x) > Chunk.DEFAULT_WIDTH * CHUNK_WIDTH_RADIUS ||
                abs(y - mob.y) > Chunk.DEFAULT_HEIGHT * CHUNK_HEIGHT_RADIUS ||
                (!gameState.creatureRegistry().isMobAlive(mob.id)))
                //conditions for removing mob
            {
                PacketDisappearMob dis = new PacketDisappearMob();
                dis.id = mob.id;
                bufferTCP.add(dis);
                mobsToDelete.add(mob.id);
            }
        }
        List<Integer> projectilesToDelete = new ArrayList<>();
        for(var entry : projectiles.entrySet()){
            PacketProjectileState projectile = entry.getValue();
            if(abs(x - projectile.x) > Chunk.DEFAULT_WIDTH * CHUNK_WIDTH_RADIUS ||
                abs(y - projectile.y) > Chunk.DEFAULT_HEIGHT * CHUNK_HEIGHT_RADIUS ||
                (!gameState.projectileRegistry().isProjectileActive(projectile.id)))
                //conditions for removing projectile
            {
                PacketDisappearProjectile dis = new PacketDisappearProjectile();
                dis.id = projectile.id;
                bufferTCP.add(dis);
                projectilesToDelete.add(projectile.id);
            }
        }
        for(Integer deletedPlayerId : playersToDelete)
        {
            players.remove(deletedPlayerId);
        }
        for(Integer deletedMobId : mobsToDelete){
            mobs.remove(deletedMobId);
        }
        for(var deletedProjectileId : projectilesToDelete){
            projectiles.remove(deletedProjectileId);
        }
        for(PhysicalPlayer player : gameState.activePlayers().getList())
        {
            PacketPlayerState pla = new PacketPlayerState();
            pla.id = player.id();
            pla.x = player.getPosition().x;
            pla.y = player.getPosition().y;
            pla.name = "ala"; //TODO wpisac name prawdziwy
            if(players.containsKey(pla.id))
            {
                PacketPlayerState tmppla = players.get(pla.id);
                if(tmppla.x != pla.x || tmppla.y != pla.y)
                {
                    players.put(pla.id, pla);
                    conn.sendUDP(pla);
                }
            }
            else if(abs(x-pla.x) <= Chunk.DEFAULT_WIDTH*CHUNK_WIDTH_RADIUS &&
                abs(y-pla.y) <= Chunk.DEFAULT_HEIGHT*CHUNK_HEIGHT_RADIUS)
                // conditions when we add player
            {
                players.put(pla.id, pla);
                bufferTCP.add(pla);
                Item item = player.heldItem();
                PacketPlayerHeldItem held = new PacketPlayerHeldItem();
                held.playerId = pla.id;
                if(item == null)
                    held.itemId = null;
                else
                    held.itemId = item.type().id();
                bufferTCP.add(held);
                if(player instanceof ObservablePhysicalPlayer observablePlayer)
                {
                    observablePlayer.addObserver(physicalPlayerObserver);
                }
            }
        }
        for(var mob : gameState.creatureRegistry().aliveMobs())
        {
            PacketMobState mobState = new PacketMobState();
            mobState.id = mob.id();
            mobState.mobType = mob.mobType();
            mobState.x = mob.getPosition().x;
            mobState.y = mob.getPosition().y;
            if(mobs.containsKey(mobState.id)){
                var tmp = mobs.get(mobState.id);
                if(tmp.x != mobState.x || tmp.y != mobState.y){
                    mobs.put(mobState.id, mobState);
                    conn.sendUDP(mobState);
                }
            }
            else if(abs(x - mobState.x) <= Chunk.DEFAULT_WIDTH * CHUNK_WIDTH_RADIUS &&
                abs(y - mobState.y) <= Chunk.DEFAULT_HEIGHT * CHUNK_HEIGHT_RADIUS)
                //conditions when we add mob
            {
                mobs.put(mobState.id, mobState);
                bufferTCP.add(mobState);
            }
        }
        for(var pro : gameState.projectileRegistry().activeProjectiles()){
            PacketProjectileState projectile = new PacketProjectileState();
            projectile.id = pro.getId();
            projectile.projectileType = pro.getTypeId();
            projectile.x = pro.getPosition().x;
            projectile.y = pro.getPosition().y;
            if(projectiles.containsKey(projectile.id)){
                var tmp = projectiles.get(projectile.id);
                if(tmp.x != projectile.x || tmp.y != projectile.y){
                    projectiles.put(projectile.id, projectile);
                    conn.sendUDP(projectile);
                }
            }
            else if(abs(x - projectile.x) <= Chunk.DEFAULT_WIDTH * CHUNK_WIDTH_RADIUS &&
                abs(y - projectile.y) <= Chunk.DEFAULT_HEIGHT * CHUNK_HEIGHT_RADIUS)
                //conditions when we add projectile
            {
                projectiles.put(projectile.id, projectile);
                bufferTCP.add(projectile);
            }
        }
        broadcast();
    }
    public Integer getPlayerId()
    {
        return playerId;
    }
    public ItemHolderObserver getItemHolderObserver()
    {
        return itemHolderObserver;
    }
    public PhysicalPlayerObserver getPhysicalPlayerObserver()
    {
        return physicalPlayerObserver;
    }
    public void broadcast()
    {
        for(Object obj : bufferTCP)
        {
            conn.sendTCP(obj);
        }
        bufferTCP.clear();
    }

}
