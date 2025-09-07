package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class PlayerRegistryList implements PlayerRegistry {
    private final Vector2 firstSpawn;
    private final List<Player> list;
    public PlayerRegistryList(List<Player> list, Vector2 firstSpawn) {
        this.list = list;
        this.firstSpawn = firstSpawn;
    }

    @Override
    public Player registerPlayer() {
        Player player = new Player(list.size(), firstSpawn);
        list.add(player);
        return player;
    }

    @Override
    public void updateRecord(int id, Player player) { list.set(id, player); }

    @Override
    public Player getPlayer(int id) {
        return list.get(id);
    }

    @Override
    public List<Player> getList() {
        return List.copyOf(list);
    }
}
