package io.github.sandboxGame.logic.creatures;

public interface Health {
    void bindCreature(Creature creature);
    void takeDamage(Damage amount);
}
