package io.github.sandboxGame.logic.creatures.tools;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.creatures.Creature;
import io.github.sandboxGame.logic.creatures.Tool;

public class MiningTool implements Tool {
    private final WorldInteractor interactor;
    private final int strength;
    private final float range;

    public MiningTool(WorldInteractor interactor, int strength, float range){
        this.interactor = interactor;
        this.strength = strength;
        this.range = range;
    }
    @Override
    public boolean normalAction(Creature user, Vector2 actionPosition) {
        if(user.getPosition().epsilonEquals(actionPosition, range)){
            return interactor.damageBlockAt(actionPosition, strength);
        }
        return false;
    }

    @Override
    public boolean specialAction(Creature user, Vector2 actionPosition) {
        return false;
    }
}
