package io.github.terraria.loading;

import io.github.terraria.logic.physics.BlockFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BodyFactoryLoaderTest {
    @Test
    void testLoad() {
        var map = new BodyFactoryLoader("testBlocks.json").map;
        assertEquals(0.9f, map.get(4).width());
    }
    @Test
    void defaultTest() {
        var map = new BodyFactoryLoader("testBlocks.json").map;
        Assertions.assertEquals(BlockFixture.defaultFriction, map.get(4).friction());
    }
}
