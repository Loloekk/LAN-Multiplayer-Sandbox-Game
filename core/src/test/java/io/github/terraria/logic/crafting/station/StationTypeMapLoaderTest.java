package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationTypeMapLoaderTest {
    StationTypeMap factory = new StationTypeFactoryLoader("testBlocks.json").getFactory();
    @Test
    void testStation() {
        Block block = new BlockBuilder().name("Anvil").build();
        assertEquals(StationType.ANVIL, factory.get(block));
    }
    @Test
    void testBlock() {
        Block block = new BlockBuilder().name("Stone").build();
        assertNull(factory.get(block));
    }
}
