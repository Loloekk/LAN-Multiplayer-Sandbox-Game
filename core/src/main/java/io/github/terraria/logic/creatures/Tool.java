package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;

public interface Tool {
    void normalAction(Creature user, Vector2 actionPosition);
    void specialAction(Creature user, Vector2 actionPosition);
}
