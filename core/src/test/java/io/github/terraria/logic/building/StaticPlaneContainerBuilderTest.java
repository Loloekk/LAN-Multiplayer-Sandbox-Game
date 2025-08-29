package io.github.terraria.logic.building;

import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.StaticBoundaryFactory;
import io.github.terraria.logic.physics.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StaticPlaneContainerBuilderTest {
    final StaticBoundaryFactory boundaryFactory = Mockito.mock(StaticBoundaryFactory.class);
    final World world = Mockito.mock(World.class);

    PlaneContainerBuilder getBuilder() {
        BlockFactory blockFactory = Mockito.mock(BlockFactory.class);
        BodyFactory bodyFactory = Mockito.mock(BodyFactory.class);
        return new StaticPlaneContainerBuilder().boundaryFactory(boundaryFactory).blockFactory(blockFactory).bodyFactory(bodyFactory).world(world);
    }

    @Test
    void badZero() {
        assertNull(getBuilder().width(3).height(10).zeroX(3).zeroY(0).build());
        assertNull(getBuilder().width(3).height(10).zeroX(2).zeroY(-1).build());
    }

    @Test
    void degenerate() {
        assertNull(getBuilder().width(0).height(10).zeroX(0).zeroY(0).build());
        assertNull(getBuilder().width(10).height(0).zeroX(0).zeroY(0).build());
    }

    @Test
    void boundaryTest() {
        int width = 10, height = 20, zeroX = 5, zeroY = 5;
        getBuilder().width(width).height(height).zeroX(zeroX).zeroY(zeroY).build();
        Mockito.verify(boundaryFactory, Mockito.times(1)).createBoundaries(
            width, height, -zeroX, -zeroY, world
        );
    }
}
