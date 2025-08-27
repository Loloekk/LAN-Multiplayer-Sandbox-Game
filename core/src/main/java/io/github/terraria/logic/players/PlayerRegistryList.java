package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class PlayerRegistryList extends PlayerRegistry {
    private final Vector2 firstSpawn;
    private final List<Player> list;
    public PlayerRegistryList(SpawnRegistry spawnRegistry, List<Player> list, Vector2 firstSpawn) {
        super(spawnRegistry);
        this.list = list;
        this.firstSpawn = firstSpawn;
    }

    @Override
    public Player registerPlayer() {
        // TODO: Player factory and equipment initialization.
        Player player = new PlayerImpl(list.size());
        list.add(player);
        spawnRegistry.setSpawnPosition(player, firstSpawn);
        return player;
    }

    @Override
    public Player getPlayer(int id) {
        return list.get(id);
    }

    @Override
    public List<Player> getList() {
        return List.copyOf(list);
    }
}
