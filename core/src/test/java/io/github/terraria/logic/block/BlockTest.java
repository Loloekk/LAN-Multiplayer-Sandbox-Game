package io.github.terraria.logic.block;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BlockTest {
    @Test
    void testBlockConstructor() {
        Block block = new Block(1, "stone", "stone.png", "solid");
        assertTrue(block.getId() == 1);
        assertTrue(block.getName().equals("stone"));
        assertTrue(block.getTexture().equals("stone.png"));
        assertTrue(block.getBehavior() instanceof SolidBehavior);
    }
}
