package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;

public interface CreatureBody {
    void bindCreature(Creature creature);
    void applyLinearImpulse(Vector2 impulse);
    Vector2 getPosition();
    Vector2 getLinearVelocity();
    void destroy();
    boolean isGrounded();
    boolean liesOn(Vector2 desired);
}
