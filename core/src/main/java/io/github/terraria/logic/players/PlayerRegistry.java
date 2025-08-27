package io.github.terraria.logic.players;

import java.util.List;

public abstract class PlayerRegistry {
    public final SpawnRegistry spawnRegistry;

    protected PlayerRegistry(SpawnRegistry spawnRegistry) { this.spawnRegistry = spawnRegistry; }

    public abstract Player registerPlayer();
    public abstract Player getPlayer(int id);
    public abstract List<Player> getList();
}
