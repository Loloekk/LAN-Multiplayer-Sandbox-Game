package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

public interface SpawnRegistry {
    Vector2 getSpawnPosition(Player player);
    void setSpawnPosition(Player player, Vector2 spawnPosition);
}
