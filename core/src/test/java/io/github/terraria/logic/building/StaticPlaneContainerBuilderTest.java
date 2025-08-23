package io.github.terraria.logic.building;

import io.github.terraria.logic.physics.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StaticPlaneContainerBuilderTest {
    World world = Mockito.mock(World.class);

    @Test
    void badZero() {
        assertNull(new StaticPlaneContainerBuilder().width(3).height(10).zeroX(3).zeroY(0).world(world).build());
        assertNull(new StaticPlaneContainerBuilder().width(3).height(10).zeroX(2).zeroY(-1).world(world).build());
    }

    @Test
    void degenerate() {
        assertNull(new StaticPlaneContainerBuilder().width(0).height(10).zeroX(0).zeroY(0).world(world).build());
        assertNull(new StaticPlaneContainerBuilder().width(10).height(0).zeroX(0).zeroY(0).world(world).build());
    }
}
