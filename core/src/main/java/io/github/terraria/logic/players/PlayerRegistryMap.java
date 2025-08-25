package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.Map;

public class PlayerRegistryMap extends PlayerRegistry {
    private final Vector2 firstSpawn;
    private final Map<Integer, Player> map;
    public PlayerRegistryMap(SpawnRegistry spawnRegistry, Map<Integer, Player> map, Vector2 firstSpawn) {
        super(spawnRegistry);
        this.map = map;
        this.firstSpawn = firstSpawn;
    }

    @Override
    public boolean registerPlayer(int id) {
        if(map.containsKey(id))
            return false;
        Player player = new PlayerImpl(id);
        map.putIfAbsent(id, player);
        spawnRegistry.setSpawnPosition(player, firstSpawn);
        // Initialize equipment...
        return true;
    }

    @Override
    public Player getPlayer(int id) {
        return map.get(id);
    }

    @Override
    public List<Player> getList() {
        return List.copyOf(map.values());
    }
}
