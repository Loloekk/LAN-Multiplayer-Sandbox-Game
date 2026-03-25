package io.github.sandboxGame.logic.creatures.bots;

import io.github.sandboxGame.logic.creatures.Creature;

public interface Bot {
    Creature getCreature();
    void think();
}
