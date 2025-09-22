package io.github.terraria.logic.creatures;

import io.github.terraria.utils.IntVector2;

public interface Movement {
    void bind(CreatureBody body);
    void move(IntVector2 direction);
    void jump();
}
