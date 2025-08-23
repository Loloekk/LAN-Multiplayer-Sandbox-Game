package io.github.terraria.logic.building;

import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Box2DBody;
import io.github.terraria.logic.physics.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

class StaticPlaneContainerTest {
    final World world = Mockito.mock(World.class);
    final int width = 10;
    final int height = 20;
    final int zeroX = 5;
    final int zeroY = 5;

    @Test
    void checkBodyCreation() {
        ArrayList<ArrayList<ArrayList<BlockType>>> grid = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                column.add(new ArrayList<>(Collections.nCopies(2, null)));
            }
            grid.add(column);
        }
        BlockType block = Mockito.mock(BlockType.class);
        grid.get(zeroX).get(zeroY).set(0, block);
        Mockito.when(block.isPhysical()).thenReturn(true);
        new StaticPlaneContainer(width, height, zeroX, zeroY, world, grid);
        Mockito.verify(block, Mockito.times(1))
            .createBody(world, new IntVector2(0, 0));
    }

    void standardSetUp() {
        Mockito.when(world.createStaticBody(Mockito.any(), Mockito.any())).thenReturn(Mockito.mock(Box2DBody.class));
        new StaticPlaneContainer(width, height, 5, 5, world);
    }

    @Test
    void bufferBlocks() {
        standardSetUp();
        final int left =  -1 - zeroX, right = width - zeroX;
        final int bottom = -1 - zeroY, top = height - zeroY;
        Mockito.verify(world, Mockito.times(2*(width+height))).createStaticBody(
            Mockito.argThat(arg -> arg.x == left || arg.x == right || arg.y == bottom || arg.y == top),
            Mockito.any());
    }

    @Test
    void badZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new StaticPlaneContainer(3, 10, 3, 0, world));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new StaticPlaneContainer(3, 10, 2, -1, world));
    }

    @Test
    void degenerate() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new StaticPlaneContainer(0, 10, 0, 0, world));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new StaticPlaneContainer(10, 0, 0, 0, world));
    }
}
