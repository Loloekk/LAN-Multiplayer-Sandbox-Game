package io.github.terraria.logic.players;

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
    public PlayerRecord registerPlayer() {
        PlayerRecord playerRecord = new PlayerRecord(list.size(), firstSpawn);
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
}
