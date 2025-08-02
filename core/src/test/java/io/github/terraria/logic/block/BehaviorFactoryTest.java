/*
package io.github.terraria.logic.block;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BehaviorFactoryTest {
    @Test
    void checkCorrectCreate() {
        BlockBehavior b = BehaviorFactory.create("solid");
        assertTrue(b instanceof SolidBehavior);
    }

    @Test
    void checkIncorrectCreate() {
        try {
            BehaviorFactory.create("!@#$%");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("No such behavior: !@#$%"));
            return;
        }
        assertTrue(false);
    }
}
*/
