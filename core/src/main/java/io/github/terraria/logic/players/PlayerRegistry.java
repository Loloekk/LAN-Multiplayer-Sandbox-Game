package io.github.terraria.logic.players;

import java.util.List;

public abstract class PlayerRegistry {
    public final SpawnRegistry spawnRegistry;

    protected PlayerRegistry(SpawnRegistry spawnRegistry) { this.spawnRegistry = spawnRegistry; }

    // Returns false if the id is occupied.
    public abstract boolean registerPlayer(int id);// Imię trzymane na wyższym poziomie?
    public abstract Player getPlayer(int id);
    public abstract List<Player> getList();
}
