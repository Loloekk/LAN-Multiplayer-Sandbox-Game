package io.github.terraria.controler.playerNetworkData;

import io.github.terraria.common.BlockState;
import io.github.terraria.common.Config;
import io.github.terraria.controler.network.PacketServerToClient.PacketPlayerState;
import io.github.terraria.controler.network.PacketServerToClient.PacketDisappearPlayer;
import io.github.terraria.controler.network.PacketServerToClient.PacketPlayerHeldItem;
import io.github.terraria.logic.actions.GameState;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.logic.equipment.Item;
import io.github.terraria.logic.players.ObservablePhysicalPlayer;
import io.github.terraria.logic.players.PhysicalPlayer;

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
        for(Integer deletedPlayerId : playersToDelete)
        {
            players.remove(deletedPlayerId);
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
