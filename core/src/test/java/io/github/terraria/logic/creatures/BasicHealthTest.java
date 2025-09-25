package io.github.terraria.logic.creatures;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BasicHealthTest {
    @Test
    void takingDamageTest(){
        Creature creature = Mockito.mock(Creature.class);
        BasicHealth health = new BasicHealth(100.0f);
        health.bindCreature(creature);
        health.takeDamage(new Damage(20.0f));
        health.takeDamage(new Damage(30.0f));
        Mockito.verify(creature, Mockito.times(0)).kill();//don't die

        health.takeDamage(new Damage(100.0f));
        health.takeDamage(new Damage(100.0f));
        Mockito.verify(creature, Mockito.times(1)).kill();//die once
    }
}
