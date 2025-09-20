package io.github.terraria.logic.creatures.movements;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.CreatureBody;
import io.github.terraria.logic.creatures.Movement;
import io.github.terraria.utils.IntVector2;

public class FlyingMovement implements Movement {
    private CreatureBody body;
    private final float maxVelocityX;
    private final float moveImpulseX;
    private final float maxVelocityUp;
    private final float moveUpAcceleration;

    public FlyingMovement(float maxVelocityX, float moveImpulseX, float maxVelocityUp, float moveUpAcceleration){
        this.maxVelocityX = maxVelocityX;
        this.moveImpulseX = moveImpulseX;
        this.maxVelocityUp = maxVelocityUp;
        this.moveUpAcceleration = moveUpAcceleration;
    }
    @Override
    public void bind(CreatureBody body) {
        this.body = body;
        this.body.setGravity(1.0f);
    }

    @Override
    public void move(IntVector2 direction) {
        if(direction.x() < 0 && body.getLinearVelocity().x > - maxVelocityX){
            body.applyLinearImpulse(new Vector2(-moveImpulseX, 0));
        }
        if(direction.x() > 0 && body.getLinearVelocity().x < maxVelocityX){
            body.applyLinearImpulse(new Vector2(moveImpulseX, 0));
        }
    }

    @Override
    public void jump() {
        if(body.getLinearVelocity().y < maxVelocityUp)body.applyLinearImpulse(new Vector2(0, moveUpAcceleration));
    }
}
