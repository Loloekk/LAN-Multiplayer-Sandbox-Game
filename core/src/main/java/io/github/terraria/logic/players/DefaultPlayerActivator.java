package io.github.terraria.logic.players;

import com.badlogic.gdx.physics.box2d.*;

public class DefaultPlayerActivator extends PlayerActivator {
    public DefaultPlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers) {
        super(registry, world, activePlayers);
    }

    private static final float width = 10f, height = 10f;
    private static final float density = 0.5f;
    private static final float friction = 0.4f;
    private static final float restitution = 0.6f;


    @Override
    protected Body getNewPlayerBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawn);

        Body body = world.createBody(bodyDef);

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
