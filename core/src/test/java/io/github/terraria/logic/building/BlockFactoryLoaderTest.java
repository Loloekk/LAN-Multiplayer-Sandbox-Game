package io.github.terraria.logic.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockFactoryLoaderTest {
    @Test
    void loadingTest() {
        Block block = new BlockFactoryLoader("testBlocks.json").getBlockFactory().create("Water");
        assertEquals(new BlockType(3, "Water", false, 1), block.type());
    }
    @Test
    void physicalLayerTest() {
        Block block = new BlockFactoryLoader("testBlocks.json").getBlockFactory().create("Dirt");
        assertEquals(new BlockType(2, "Dirt", true, 0), block.type());
    }
}
