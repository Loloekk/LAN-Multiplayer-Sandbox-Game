package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Box2DBody implements Body {
    final com.badlogic.gdx.physics.box2d.Body body;

    Box2DBody(com.badlogic.gdx.physics.box2d.Body body) {
        this.body = body;
    }

    @Override
    public boolean liesOn(Vector2 desired) {
        for (Fixture fixture : body.getFixtureList()) {
            if (fixture.testPoint(desired)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public World getWorld() {
        return new Box2DWorld(body.getWorld());
    }

    @Override
    public Vector2 getWorldCenter() {
        return body.getWorldCenter();
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public void applyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake) {
        body.applyLinearImpulse(impulse, point, wake);
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }
}
