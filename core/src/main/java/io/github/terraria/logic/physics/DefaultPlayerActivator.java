package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PlayerRegistry;

public class DefaultPlayerActivator extends PlayerActivator {
    public DefaultPlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers) {
        super(registry, world, activePlayers);
    }

    private static final float width = 1f, height = 2f;
    private static final float density = 0.5f;
    private static final float friction = 0.4f;
    private static final float restitution = 0.6f;


    @Override
    protected Body getNewPlayerBody(Vector2 spawnPosition) {
        Body body = world.createDynamicBody(spawnPosition);

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        rectangle.dispose();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);
        return body;
    }
}
