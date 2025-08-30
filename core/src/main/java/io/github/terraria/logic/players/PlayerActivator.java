package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.World;

public abstract class PlayerActivator {
    public static final float MAX_PLAYERS_RADIUS = 1.2f; // Z jakimś zapasem dla float błędów.
    private final PlayerRegistry registry;
    protected final World world;
    private final ActivePlayers activePlayers;
    public PlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers) {
        this.registry = registry;
        this.world = world;
        this.activePlayers = activePlayers;
    }

    protected abstract Body getNewPlayerBody(Vector2 spawnPosition);

    // Sprawdzanie haseł poza modelem.
    public void loginPlayer(int playersId) {
        Player player = registry.getPlayer(playersId);
        Vector2 spawnPosition = registry.spawnRegistry.getSpawnPosition(player);
        activePlayers.add(new PhysicalPlayer(player, getNewPlayerBody(spawnPosition)));
    }
    public void logoutPlayer(int playersId) {
        PhysicalPlayer physicalPlayer = activePlayers.remove(playersId);
        Body body = physicalPlayer.body();
        registry.spawnRegistry.setSpawnPosition(physicalPlayer.player(), body.getPosition());
        // Destroy body.
        world.destroyBody(body);
    }
}
