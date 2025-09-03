package io.github.terraria.logic.physics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BodyFactoryLoaderTest {
    @Test
    void testLoad() {
        var map = new BodyFactoryLoader("testBlocks.json").getBodyFactory().map;
        assertEquals(0.9f, map.get(4).width());
    }
    @Test
    void defaultTest() {
        var map = new BodyFactoryLoader("testBlocks.json").getBodyFactory().map;
        assertEquals(BlockFixture.defaultFriction, map.get(4).friction());
    }
}
