package io.github.sandboxGame.logic.creatures.movements;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.creatures.CreatureBody;
import io.github.sandboxGame.logic.creatures.Movement;
import io.github.sandboxGame.utils.IntVector2;

public class FloatingMovement implements Movement {
    private CreatureBody body;
    private final float maxVelocity;
    private final float moveImpulse;
    public FloatingMovement(float maxVelocity, float moveImpulse){
        this.maxVelocity = maxVelocity;
        this.moveImpulse = moveImpulse;
    }
    @Override
    public void bind(CreatureBody body) {
        this.body = body;
        this.body.setGravity(0.0f);
    }

    @Override
    public void move(IntVector2 direction) {
        if(direction.x() < 0 && body.getLinearVelocity().x > -maxVelocity){
            body.applyLinearImpulse(new Vector2(-moveImpulse, 0));
        }
        if(direction.x() > 0 && body.getLinearVelocity().x < maxVelocity){
            body.applyLinearImpulse(new Vector2(moveImpulse, 0));
        }

        if(direction.y() < 0 && body.getLinearVelocity().y > -maxVelocity){
            body.applyLinearImpulse(new Vector2(0, -moveImpulse));
        }
        if(direction.y() > 0 && body.getLinearVelocity().y < maxVelocity){
            body.applyLinearImpulse(new Vector2(0, moveImpulse));
        }
    }

    @Override
    public void jump() {

    }
}
