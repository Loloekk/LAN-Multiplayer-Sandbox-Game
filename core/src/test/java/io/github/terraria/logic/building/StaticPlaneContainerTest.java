package io.github.terraria.logic.building;

import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Box2DBody;
import io.github.terraria.logic.physics.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StaticPlaneContainerTest {
    final World world = Mockito.mock(World.class);
    final int width = 10;
    final int height = 20;
    final int zeroX = 5;
    final int zeroY = 5;

    ArrayList<ArrayList<ArrayList<BlockType>>> getDummyGrid() {
        ArrayList<ArrayList<ArrayList<BlockType>>> grid = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                column.add(new ArrayList<>(Collections.nCopies(StaticPlaneContainer.layers, null)));
            }
            grid.add(column);
        }
        return grid;
    }

    PlaneContainerBuilder getBuilder() {
        return new StaticPlaneContainerBuilder().width(width).height(height).zeroX(zeroX).zeroY(zeroY).world(world);
    }

    @Test
    void checkBodyCreation() {
        var grid = getDummyGrid();
        BlockType block = Mockito.mock(BlockType.class);
        grid.get(zeroX).get(zeroY).set(0, block);
        Mockito.when(block.isPhysical()).thenReturn(true);
        getBuilder().savedGrid(grid).build();
        Mockito.verify(block, Mockito.times(1))
            .createBody(world, new IntVector2(0, 0));
    }

    @Test
    void bufferBlocks() {
        getBuilder().build();
        final int left =  -1 - zeroX, right = width - zeroX;
        final int bottom = -1 - zeroY, top = height - zeroY;
        Mockito.verify(world, Mockito.times(2*(width+height))).createStaticBody(
            Mockito.argThat(arg -> arg.x == left || arg.x == right || arg.y == bottom || arg.y == top),
            Mockito.any());
    }

    @Test
    void conversionTest() {
        var grid = getDummyGrid();
        BlockType block = Mockito.mock(BlockType.class);
        grid.get(zeroX).get(zeroY).set(0, block);
        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertEquals(block, container.getBlockAt(0, 0, 0));
    }

    @Test
    void getFrontBlockAtTest() {
        var grid = getDummyGrid();
        BlockType block = Mockito.mock(BlockType.class);
        grid.get(zeroX).get(zeroY).set(1, block);
        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertEquals(block, container.getFrontBlockAt(0, 0));
    }

    @Test
    void getPhysicalNoFrontBlockTest() {
        var grid = getDummyGrid();
        BlockType block = Mockito.mock(BlockType.class);
        grid.get(zeroX).get(zeroY).set(1, block);
        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertNull(container.getPhysicalAt(0, 0));
    }

    @Test
    void getPhysicalAtTest() {
        var grid = getDummyGrid();
        BlockType block = Mockito.mock(BlockType.class);
        grid.get(zeroX).get(zeroY).set(0, block);

        Mockito.when(block.isPhysical()).thenReturn(true);
        Box2DBody body = Mockito.mock(Box2DBody.class);
        Mockito.when(block.createBody(Mockito.any(), Mockito.any())).thenReturn(body);

        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertEquals(new PhysicalBlock(block, body), container.getPhysicalAt(0, 0));
    }
}
