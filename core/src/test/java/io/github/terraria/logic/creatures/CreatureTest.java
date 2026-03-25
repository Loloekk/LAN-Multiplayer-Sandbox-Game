package io.github.sandboxGame.logic.creatures;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.inOrder;

public class CreatureTest {
    @Test
    void destroyBodyAfterDeath(){
        CreatureBody body = Mockito.mock(CreatureBody.class);
        Movement movement = Mockito.mock(Movement.class);
        Tool tool = Mockito.mock(Tool.class);
        Health health = Mockito.mock(Health.class);
        Creature creature = new Creature(0, 0, body, movement, tool, health);

        creature.kill();
        Mockito.verify(body).destroy();
    }

    @Test
    void deathEventsTest(){
        CreatureBody body = Mockito.mock(CreatureBody.class);
        Movement movement = Mockito.mock(Movement.class);
        Tool tool = Mockito.mock(Tool.class);
        Health health = Mockito.mock(Health.class);
        Creature creature = new Creature(0, 0, body, movement, tool, health);

        WorldEvent event1 = Mockito.mock(WorldEvent.class);
        WorldEvent event3 = Mockito.mock(WorldEvent.class);
        WorldEvent event2 = Mockito.mock(WorldEvent.class);
        InOrder eventOrder = inOrder(event1, event2, event3);

        creature.addDeathEvent(event1);
        creature.addDeathEvent(event2);
        creature.addDeathEvent(event3);
        creature.kill();
        eventOrder.verify(event1).trigger();
        eventOrder.verify(event2).trigger();
        eventOrder.verify(event3).trigger();
    }
}
