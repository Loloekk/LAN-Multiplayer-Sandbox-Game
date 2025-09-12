package io.github.terraria.loading;

import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockBuilder;
import io.github.terraria.logic.crafting.station.StationType;
import io.github.terraria.logic.crafting.station.StationTypeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationTypeMapLoaderTest {
    StationTypeMap factory = new StationTypeMapLoader("testBlocks.json").getFactory();
    @Test
    void testStation() {
        Block block = new BlockBuilder().name("Anvil").build();
        Assertions.assertEquals(StationType.ANVIL, factory.get(block));
    }
    @Test
    void testBlock() {
        Block block = new BlockBuilder().name("Stone").build();
        assertNull(factory.get(block));
    }
}
