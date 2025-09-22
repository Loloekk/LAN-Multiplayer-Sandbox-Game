package io.github.terraria.logic.creatures;

public interface Health {
    void bindCreature(Creature creature);
    void takeDamage(Damage amount);
}
