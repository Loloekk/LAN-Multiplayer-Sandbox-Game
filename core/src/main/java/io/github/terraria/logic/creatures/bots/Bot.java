package io.github.terraria.logic.creatures.bots;

import io.github.terraria.logic.creatures.Creature;

public interface Bot {
    Creature getCreature();
    void think();
}
