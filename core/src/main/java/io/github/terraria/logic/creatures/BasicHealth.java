package io.github.sandboxGame.logic.creatures;

import java.util.function.Supplier;

public class BasicHealth implements Health{
    private float health;
    private float maxHealth;
    private Creature creature;
    public BasicHealth(float maxHealth){
        this.maxHealth = maxHealth;
        health = maxHealth;
    }
    @Override
    public void bindCreature(Creature creature) {
        this.creature = creature;
    }

    @Override
    public void takeDamage(Damage damage) {
        if(health <= 0)return;
        health -= damage.getAmount();
        if (health <= 0)creature.kill();
    }
}
