package io.github.sandboxGame.logic.creatures.tools;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.creatures.Creature;
import io.github.sandboxGame.logic.creatures.Tool;

public class NoTool implements Tool {
    @Override
    public boolean normalAction(Creature user, Vector2 actionPosition) {
        return false;
    }

    @Override
    public boolean specialAction(Creature user, Vector2 actionPosition) {
        return false;
    }
}
