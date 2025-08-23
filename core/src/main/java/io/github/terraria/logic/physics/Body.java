package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;

public interface Body {
    boolean liesOn(Vector2 desired);

    World getWorld();

    Vector2 getWorldCenter();

    Vector2 getPosition();

    void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake);

    Vector2 getLinearVelocity();
}
