package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PhysicalPlayer;
import io.github.terraria.logic.players.Player;
import io.github.terraria.logic.players.PlayerRegistry;

public abstract class PlayerActivator {
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
