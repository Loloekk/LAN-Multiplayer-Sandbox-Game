package io.github.sandboxGame.logic.creatures.tools;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.creatures.Creature;
import io.github.sandboxGame.logic.creatures.Damage;
import io.github.sandboxGame.logic.creatures.Tool;

public class MeleeWeapon implements Tool {
    private final WorldInteractor interactor;
    private Damage damage;
    private float range;

    public MeleeWeapon(WorldInteractor interactor, Damage damage, float range){
        this.interactor = interactor;
        this.damage = damage;
        this.range = range;
    }
    @Override
    public boolean normalAction(Creature user, Vector2 actionPosition) {
        if(user.getPosition().dst(actionPosition) > range)return false;
        Creature target = interactor.getCreatureAt(actionPosition, user);
        if(target != null){
            target.takeDamage(damage);
            if(user.getPosition().x < target.getPosition().x){
                target.applyImpulse(new Vector2(5, 5));
            }else{
                target.applyImpulse(new Vector2(-5, 5));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean specialAction(Creature user, Vector2 actionPosition) {
        return false;
    }
}
