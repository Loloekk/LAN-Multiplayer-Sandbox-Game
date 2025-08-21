package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public interface Body {
    boolean liesOn(Vector2 desired);

    World getWorld();

    Vector2 getWorldCenter();

    Vector2 getPosition();

    // Not too nice...
    void createFixture(FixtureDef def);

    void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake);

    Vector2 getLinearVelocity();
}
