package io.github.terraria.logic.creatures;

import io.github.terraria.logic.creatures.projectiles.BasicProjectile;
import io.github.terraria.logic.creatures.projectiles.ProjectileBody;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;

public class BasicProjectileTest {
    ProjectileBody body = Mockito.mock(ProjectileBody.class);
    Damage damage = new Damage(10.0f);
    BasicProjectile projectile = new BasicProjectile(0, 0, body, damage);
    @Test
    void obstacleCollisionTest(){
        assertTrue(projectile.isAlive());
        projectile.hitObstacle();
        assertFalse(projectile.isAlive());
        Mockito.verify(body).destroy();
    }

    @Test
    void creatureCollisionTest(){
        assertTrue(projectile.isAlive());
        Creature creature = Mockito.mock(Creature.class);
        projectile.hitCreature(creature);

        assertFalse(projectile.isAlive());
        Mockito.verify(body).destroy();
        Mockito.verify(creature).takeDamage(damage);
    }

    @Test
    void destructionEventsTest(){
        WorldEvent event1 = Mockito.mock(WorldEvent.class);
        WorldEvent event3 = Mockito.mock(WorldEvent.class);
        WorldEvent event2 = Mockito.mock(WorldEvent.class);
        InOrder eventOrder = inOrder(event1, event2, event3);

        projectile.addDestructionEvent(event1);
        projectile.addDestructionEvent(event2);
        projectile.addDestructionEvent(event3);
        projectile.hitObstacle();
        eventOrder.verify(event1).trigger();
        eventOrder.verify(event2).trigger();
        eventOrder.verify(event3).trigger();
    }

    @Test
    void destroyOnceTest(){
        projectile.hitObstacle();
        projectile.hitObstacle();
        Mockito.verify(body, Mockito.times(1)).destroy();
    }
}
