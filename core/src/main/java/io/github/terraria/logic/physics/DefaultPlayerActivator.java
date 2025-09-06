package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PlayerActivator;
import io.github.terraria.logic.players.PlayerRegistry;

public class DefaultPlayerActivator extends PlayerActivator {
    public DefaultPlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers) {
        super(registry, world, activePlayers);
    }

    private static final PlayerFixture playerFixture = new PlayerFixture(0.8f, 2f,
        2f, 1.3f, 0.1f, new Vector2());

    @Override
    protected Body getNewPlayerBody(Vector2 spawnPosition) {
        Body body = world.createDynamicBody(spawnPosition);
        body.addPlayerFixture(playerFixture);
        return body;
    }
}
