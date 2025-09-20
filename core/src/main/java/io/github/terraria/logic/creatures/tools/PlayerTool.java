package io.github.terraria.logic.creatures.tools;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.Damage;
import io.github.terraria.logic.creatures.Tool;

public class PlayerTool implements Tool {
    private final WorldInteractor interactor;
    private final Tool heldTool;
    //TODO: load constants from file
    private final static Damage BASE_DAMAGE = new Damage();
    private final static int BASE_MINING_SPEED = 35;
    private final static float BASE_RANGE = 5;

    public PlayerTool(WorldInteractor interactor, Tool tool){
        this.interactor = interactor;
        this.heldTool = tool;
    }

    @Override
    public boolean normalAction(Creature user, Vector2 actionPosition) {
        if(!heldTool.normalAction(user, actionPosition)){
            Creature hittedCreature = interactor.getCreatureAt(actionPosition, user);
            if(hittedCreature != null && hittedCreature.getPosition().dst(user.getPosition()) < BASE_RANGE){
                hittedCreature.takeDamage(BASE_DAMAGE);
                return true;
            }
            if(user.getPosition().epsilonEquals(actionPosition, BASE_RANGE)){
                interactor.damageBlockAt(actionPosition, BASE_MINING_SPEED);
            }
        }
        return true;
    }

    @Override
    public boolean specialAction(Creature user, Vector2 actionPosition) {
        if(!heldTool.normalAction(user, actionPosition)){
            interactor.placeHeldBlockAt(actionPosition);
        }
        return true;
    }
}
