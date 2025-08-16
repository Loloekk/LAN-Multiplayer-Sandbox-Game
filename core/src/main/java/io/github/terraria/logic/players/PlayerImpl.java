package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

class PlayerImpl implements Player {
    private final int id;
    private Vector2 spawnPosition;
    public PlayerImpl(int id, Vector2 spawnPosition) {
        this.id = id;
        this.spawnPosition = spawnPosition;
    }
    public int getId() {
        return id;
    }

    @Override
    public void setSpawnPosition(Vector2 spawnPosition) { this.spawnPosition = spawnPosition; }

    @Override
    public Vector2 getSpawnPosition() { return spawnPosition; }
}
