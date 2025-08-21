package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

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
    public void createFixture(FixtureDef def) { body.createFixture(def); }

    @Override
    public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake) {
        body.applyLinearImpulse(impulseX, impulseY, pointX, pointY, wake);
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }
}
