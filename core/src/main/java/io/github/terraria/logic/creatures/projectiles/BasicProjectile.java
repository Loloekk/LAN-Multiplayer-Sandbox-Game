package io.github.terraria.logic.creatures.projectiles;

import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.Damage;

public class BasicProjectile extends Projectile{
    private Damage damage;
    public BasicProjectile(int id, int typeId, ProjectileBody body, Damage damage){
        super(id, typeId, body);
        this.damage = damage;
    }
    @Override
    public void hitCreature(Creature creature) {
        creature.takeDamage(damage);
        destroy();
    }

    @Override
    public void hitObstacle() {
        destroy();
    }
}
