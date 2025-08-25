package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

import java.util.Map;

public class SpawnRegistryMap implements SpawnRegistry {
    private final Map<Player, Vector2> map;

    public SpawnRegistryMap(Map<Player, Vector2> map) {
        this.map = map;
    }

    @Override
    public Vector2 getSpawnPosition(Player player) { return map.get(player); }

    @Override
    public void setSpawnPosition(Player player, Vector2 spawnPosition) { map.put(player, spawnPosition); }
}
