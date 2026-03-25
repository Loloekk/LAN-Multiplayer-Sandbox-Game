package io.github.sandboxGame.logic.creatures;

import com.badlogic.gdx.math.Vector2;

public class EmptyBody implements CreatureBody{
    @Override
    public void bindCreature(Creature creature) {

    }

    @Override
    public void applyLinearImpulse(Vector2 impulse) {

    }

    @Override
    public void applyForce(Vector2 force) {

    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(0, 0);
    }

    @Override
    public Vector2 getLinearVelocity() {
        return new Vector2(0, 0);
    }

    @Override
    public void setGravity(float scale) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isGrounded() {
        return false;
    }

    @Override
    public boolean obstacleLeft() {
        return false;
    }

    @Override
    public boolean obstacleRight() {
        return false;
    }

    @Override
    public boolean liesOn(Vector2 desired) {
        return false;
    }
}
