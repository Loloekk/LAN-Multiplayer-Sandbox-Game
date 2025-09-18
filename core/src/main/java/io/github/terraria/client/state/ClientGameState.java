package io.github.terraria.client.state;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.common.BlockState;
import io.github.terraria.common.Config;
import io.github.terraria.controler.network.PacketServerToClient.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static io.github.terraria.common.Config.SCENE_HEIGHT;
import static io.github.terraria.common.Config.SCENE_WIDTH;
import static java.lang.Math.abs;


public class ClientGameState {
    public static int CHUNK_WIDTH_RADIUS = Config.CHUNK_WIDTH_RADIUS;
    public static int CHUNK_HEIGHT_RADIUS = Config.CHUNK_HEIGHT_RADIUS;
    private int playerId;
    private ClientMainPlayerState mainPlayerState;
    Connection conn;

    public Map<Integer, ClientChunk> chunks = new HashMap<>();
    public Map<Integer, ClientPlayerState> players = new HashMap<>();
    public ClientGameState(Connection conn)
    {
        this.conn = conn;
        mainPlayerState = new ClientMainPlayerState();
    }
    public void setPlayerId(int id)
    {
        this.playerId = id;
        mainPlayerState.setPlayerId(id);
    }
    public void actualize(Object obj)
    {
        if(obj instanceof BlockState blockState)
        {
            int chunkId = ClientChunk.getId(blockState.x,blockState.y);
            int X = ClientChunk.getX(blockState.x);
            int Y = ClientChunk.getY(blockState.y);
            if(!chunks.containsKey(chunkId))
                chunks.put(chunkId, new ClientChunk(X,Y));
            chunks.get(chunkId).setBlock(blockState);
        }
        else if(obj instanceof PacketPlayerState pla)
        {
            if(pla.id == playerId)
            {
                mainPlayerState.actualize(pla);
            }
            if(!players.containsKey(pla.id))
                players.put(pla.id, new ClientPlayerState());
            ClientPlayerState player = players.get(pla.id);
            player.id = pla.id;
            player.x = pla.x;
            player.y = pla.y;
        }
        else if(obj instanceof PacketDisappearPlayer dis)
        {
            players.remove(dis.id);
        }
        else if(obj instanceof PacketPlayerTakeItem take)
        {
            ClientPlayerState player = players.get(take.playerId);
            if(player != null)
            {
                player.heldItem = take.itemId;
                if(take.playerId == playerId)
                    mainPlayerState.actualize(obj);
            }
        }
        else if(obj instanceof PacketCollectItems || obj instanceof PacketRemoveItems)
        {
            mainPlayerState.actualize(obj);
        }
    }
    public Integer getBlockId(int x, int y, int z)
    {
        int X = ClientChunk.getX(x);
        int Y = ClientChunk.getY(y);
        int chunkId = ClientChunk.getId(X,Y);
        if(chunks.containsKey(chunkId)) {
            ClientChunk chunk = chunks.get(chunkId);
            if(chunk == null)
                return null;
            return chunk.getBlockId(x, y, z);
        }
        return null;
    }
    public List<ClientPlayerState> getPlayers()
    {
        ArrayList<ClientPlayerState> playersList = new ArrayList<>();
        for(Map.Entry<Integer, ClientPlayerState> entry : players.entrySet())
        {
            playersList.add(entry.getValue());
        }
        return playersList;
    }
    public void throwTrash()
    {
        int X = ClientChunk.getX(getX());
        int Y = ClientChunk.getX(getY());
        List<Integer> chunksToDelete = new ArrayList<>();
        for(ClientChunk chunk : chunks.values())
        {
            if(chunk.zeroX < X - CHUNK_WIDTH_RADIUS* ClientChunk.DEFAULT_WIDTH ||
                chunk.zeroX > X + CHUNK_WIDTH_RADIUS* ClientChunk.DEFAULT_WIDTH ||
                chunk.zeroY < Y - CHUNK_HEIGHT_RADIUS* ClientChunk.DEFAULT_HEIGHT ||
                chunk.zeroY > Y + CHUNK_HEIGHT_RADIUS* ClientChunk.DEFAULT_HEIGHT
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
    public Vector2 getGamePosition(Vector2 screenPos)
    {
        return new Vector2(getX() + (screenPos.x - SCENE_WIDTH/2), getY() + (screenPos.y - SCENE_HEIGHT/2));
    }
    public float getX()
    {
        return mainPlayerState.getX();
    }
    public float getY()
    {
        return mainPlayerState.getY();
    }
    public ClientMainPlayerState getMainPlayerState()
    {
        return mainPlayerState;
    }

}
