package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;

public interface Body {
    boolean liesOn(Vector2 desired);

    World getWorld();

    Vector2 getPosition();

    void applyLinearImpulse(Vector2 impulse);

    Vector2 getLinearVelocity();

    void addPlayerFixture(PlayerFixture playerFixture);
}
