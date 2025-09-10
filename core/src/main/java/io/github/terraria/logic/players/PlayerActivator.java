package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.World;

public abstract class PlayerActivator {
    public static final float MAX_PLAYERS_RADIUS = 0.99f; // Z jakimś zapasem dla float błędów.
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
        PlayerRecord playerRecord = registry.getPlayer(playersId);
        activePlayers.add(new PhysicalPlayer(playerRecord, getNewPlayerBody(playerRecord.spawn())));
    }
    public void logoutPlayer(int playersId) {
        PhysicalPlayer physicalPlayer = activePlayers.remove(playersId);
        PlayerRecord playerRecord = new PlayerRecord(physicalPlayer.id(), physicalPlayer.equipment(), physicalPlayer.getPosition());
        physicalPlayer.body().destroy(); // ?
        registry.updateRecord(playerRecord.id(), playerRecord);
    }
}
