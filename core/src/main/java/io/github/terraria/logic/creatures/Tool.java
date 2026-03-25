package io.github.sandboxGame.logic.creatures;

import com.badlogic.gdx.math.Vector2;

public interface Tool {
    boolean normalAction(Creature user, Vector2 actionPosition); //returns true if action was successfully performed
    boolean specialAction(Creature user, Vector2 actionPosition);
}
