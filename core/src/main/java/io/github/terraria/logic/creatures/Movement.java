package io.github.sandboxGame.logic.creatures;

import io.github.sandboxGame.utils.IntVector2;

public interface Movement {
    void bind(CreatureBody body);
    void move(IntVector2 direction);
    void jump();
}
