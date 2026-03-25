package io.github.sandboxGame.logic.players;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class PlayerRegistryList implements PlayerRegistry {
    private final Vector2 firstSpawn;
    private final List<PlayerRecord> list;
    public PlayerRegistryList(List<PlayerRecord> list, Vector2 firstSpawn) {
        this.list = list;
        this.firstSpawn = firstSpawn;
    }

    @Override
    public PlayerRecord registerPlayer(String name) {
        PlayerRecord playerRecord = new PlayerRecord(list.size(), name, firstSpawn, firstSpawn);
        list.add(playerRecord);
        return playerRecord;
    }

    @Override
    public void updateRecord(int id, PlayerRecord playerRecord) { list.set(id, playerRecord); }

    @Override
    public PlayerRecord getPlayer(int id) {
        return list.get(id);
    }

    @Override
    public List<PlayerRecord> getList() {
        return List.copyOf(list);
    }
    @Override
    public boolean hasPlayer(int id)
    {
        return id < list.size();
    }
    @Override
    public boolean hasPlayer(String name)
    {
        for(var pla : list){
            if(pla.name().equals(name))return true;
        }
        return false;
    }
    @Override
    public int getId(String name)
    {
        for(var pla : list){
            if(pla.name().equals(name))return pla.id();
        }
        return -1;
    }
}
