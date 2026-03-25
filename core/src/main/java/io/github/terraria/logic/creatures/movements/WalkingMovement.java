package io.github.sandboxGame.logic.creatures.movements;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.creatures.CreatureBody;
import io.github.sandboxGame.logic.creatures.Movement;
import io.github.sandboxGame.utils.IntVector2;

public class WalkingMovement implements Movement {
    private CreatureBody body;
    private final float maxVelocityX;
    private final Vector2 jumpImpulse;
    private final float acceleration;

    public WalkingMovement(float maxVelocityX, float acceleration, float jumpStrength){
        this.maxVelocityX = maxVelocityX;
        this.acceleration = acceleration;
        jumpImpulse = new Vector2(0, jumpStrength);
    }
    @Override
    public void bind(CreatureBody body) {
        this.body = body;
        this.body.setGravity(1.0f);
    }

    @Override
    public void move(IntVector2 direction) {
        if(direction.x() < 0 && body.getLinearVelocity().x > - maxVelocityX){
            body.applyLinearImpulse(new Vector2(-acceleration, 0));
        }
        if(direction.x() > 0 && body.getLinearVelocity().x < maxVelocityX){
            body.applyLinearImpulse(new Vector2(acceleration, 0));
        }
    }

    @Override
    public void jump() {
        if(body.isGrounded()){
            body.applyLinearImpulse(jumpImpulse);
        }
    }
}
