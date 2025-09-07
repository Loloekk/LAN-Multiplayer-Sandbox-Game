package io.github.terraria.controler.PlayerNetworkData;

import io.github.terraria.controler.Network.Network;
import io.github.terraria.controler.Network.PacketPlayerDisappear;
import io.github.terraria.logic.GameState;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.logic.players.PhysicalPlayer;

import static java.lang.Math.abs;

public class PlayerData {
    public static int CHUNK_WIDTH_RADIUS = 3;
    public static int CHUNK_HEIGHT_RADIUS = 2;
    private GameState gameState;
    private int playerId;
    Connection conn;

    public Map<Integer, Chunk> chunks = new HashMap<>();
    public Map<Integer,Network.PlayerState> players = new HashMap<>();
    public PlayerData(Connection conn, GameState gameState, int playerId)
    {
        this.conn = conn;
        this.gameState = gameState;
        this.playerId = playerId;
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
                    conn.sendUDP(data);
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
                    conn.sendUDP(data);
                    //System.out.println("wysylam bloki");
                }
            }
        }
        for(Chunk chunk : chunksToDelete)
        {
            chunks.remove(chunk);
        }
        List<Integer> playersToDelete = new ArrayList<>();
        for(Map.Entry<Integer, Network.PlayerState> entry : players.entrySet())
        {
            Network.PlayerState pla = entry.getValue();
            if(abs(x-pla.x) > Chunk.DEFAULT_WIDTH*CHUNK_WIDTH_RADIUS ||
                abs(y-pla.y) > Chunk.DEFAULT_HEIGHT*CHUNK_HEIGHT_RADIUS ||
                (!gameState.activePlayers().isActive(pla.id)))
            {
                PacketPlayerDisappear dis = new PacketPlayerDisappear();
                dis.id = pla.id;
                conn.sendUDP(dis);
                playersToDelete.add(pla.id);
            }
        }
        for(Integer deletedPlayerId : playersToDelete)
        {
            players.remove(deletedPlayerId);
        }
        for(PhysicalPlayer player : gameState.activePlayers().getList())
        {
            Network.PlayerState pla = new Network.PlayerState();
            pla.id = player.id();
            pla.x = player.getPosition().x;
            pla.y = player.getPosition().y;
            pla.name = "ala";
            if(abs(x-pla.x) <= Chunk.DEFAULT_WIDTH*CHUNK_WIDTH_RADIUS &&
                abs(y-pla.y) <= Chunk.DEFAULT_HEIGHT*CHUNK_HEIGHT_RADIUS)
            {
                if(players.containsKey(pla.id))
                {
                    Network.PlayerState tmppla = players.get(pla.id);
                    if(tmppla.x != pla.x || tmppla.y != pla.y)
                    {
                        players.put(pla.id, pla);
                        conn.sendUDP(pla);
                    }
                }
                else
                {
                    players.put(pla.id, pla);
                    conn.sendUDP(pla);
                }
            }
        }
    }
    public Integer getPlayerId()
    {
        return playerId;
    }

}
