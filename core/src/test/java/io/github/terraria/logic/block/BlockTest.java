package io.github.terraria.logic.block;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {
    @Test
    void testBlockConstructor() {
        Block block = new Block(1, "stone", "stone.png", "solid");
        assertEquals(1, block.getId());
        assertEquals("stone", block.getName());
        assertEquals("stone.png", block.getTexture());
        assertInstanceOf(SolidBehavior.class, block.getBehavior());
    }
    @Test
    void equalTest() {
        Block bl1 = new Block(1, "stone", "stone.png", "solid");
        Block bl2 = new Block(2, "stone", "stone.png", "solid");
        assertEquals(bl1, bl2);
        assertEquals(bl2, bl1);
    }
}
