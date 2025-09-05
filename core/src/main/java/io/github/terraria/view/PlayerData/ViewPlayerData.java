package io.github.terraria.view.PlayerData;

import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.controler.Network.Network;
import io.github.terraria.controler.Network.PacketPlayerDisappear;
import io.github.terraria.controler.PlayerNetworkData.BlockState;
import io.github.terraria.controler.PlayerNetworkData.Chunk;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static java.lang.Math.abs;


public class ViewPlayerData {
    public static int CHUNK_WIDTH_RADIUS = 6;
    public static int CHUNK_HEIGHT_RADIUS = 4;
    private int playerId;
    private float x;
    private float y;
    Connection conn;

    public Map<Integer, ViewChunk> chunks = new HashMap<>();
    public Map<Integer,Network.PlayerState> players = new HashMap<>();
    public ViewPlayerData(Connection conn, int playerId)
    {
        this.conn = conn;
        this.playerId = playerId;
    }
    public void actualize(Object obj)
    {
        if(obj instanceof io.github.terraria.controler.PlayerNetworkData.BlockState block)
        {
            ViewBlockState blockState = new ViewBlockState();
            blockState.x = block.x;
            blockState.y = block.y;
            blockState.z = block.z;
            blockState.blockID = block.blockID;
//            System.out.println(blockState.x+" "+blockState.y+" "+blockState.z+" "+blockState.blockID);
            int chunkId = ViewChunk.getId(blockState.x,blockState.y);
            int X = ViewChunk.getX(blockState.x);
            int Y = ViewChunk.getY(blockState.y);
            if(!chunks.containsKey(chunkId))
                chunks.put(chunkId, new ViewChunk(X,Y));
            chunks.get(chunkId).setBlock(blockState);
        }
        else if(obj instanceof Network.PlayerState pla)
        {
            if(pla.id == playerId)
            {
                x = pla.x;
                y = pla.y;
            }
            if(!players.containsKey(pla.id))
                players.put(pla.id, new Network.PlayerState());
            Network.PlayerState player = players.get(pla.id);
            player.x = pla.x;
            player.y = pla.y;
        }
        else if(obj instanceof PacketPlayerDisappear dis)
        {
            players.remove(dis.id);
        }
    }
    public Integer getBlockId(int x, int y, int z)
    {
        int X = ViewChunk.getX(x);
        int Y = ViewChunk.getY(y);
        int chunkId = ViewChunk.getId(X,Y);
        if(chunks.containsKey(chunkId))
            return chunks.get(chunkId).getBlockId(x,y,z);
        return null;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public List<Network.PlayerState> getPlayers()
    {
        ArrayList<Network.PlayerState> playersList = new ArrayList<>();
        for(Map.Entry<Integer, Network.PlayerState> entry : players.entrySet())
        {
            playersList.add(entry.getValue());
        }
        return playersList;
    }
    public void throwTrash()
    {
        int X = ViewChunk.getX(x);
        int Y = ViewChunk.getX(y);
        List<Integer> chunksToDelete = new ArrayList<>();
        for(ViewChunk chunk : chunks.values())
        {
            if(chunk.zeroX < X - CHUNK_WIDTH_RADIUS*ViewChunk.DEFAULT_WIDTH ||
                chunk.zeroX > X + CHUNK_WIDTH_RADIUS*ViewChunk.DEFAULT_WIDTH ||
                chunk.zeroY < Y - CHUNK_HEIGHT_RADIUS*ViewChunk.DEFAULT_HEIGHT ||
                chunk.zeroY > Y + CHUNK_HEIGHT_RADIUS*ViewChunk.DEFAULT_HEIGHT
            )
            {
                chunksToDelete.add(chunk.getId());
            }
        }
        for(Integer chunkId : chunksToDelete)
        {
            chunks.remove(chunkId);
        }

    }

}
