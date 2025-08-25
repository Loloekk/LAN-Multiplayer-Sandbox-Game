package io.github.terraria.logic.building;

import io.github.terraria.logic.IntRectangle;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Box2DBody;
import io.github.terraria.logic.physics.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Mockito.when(block.createBody(world, new IntVector2(0, 0))).thenReturn(body);

        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertEquals(new PhysicalBlock(block, body), container.getPhysicalAt(0, 0));
    }

    @Test
    void placeBlockAtOccupiedTest() {
        var grid = getDummyGrid();
        BlockType block = Mockito.mock(BlockType.class);
        grid.get(zeroX).get(zeroY).set(0, block);

        PlaneContainer container = getBuilder().savedGrid(grid).build();
        BlockType otherBlock = Mockito.mock(BlockType.class);
        Mockito.when(otherBlock.getLayer()).thenReturn(0);
        assertFalse(container.placeBlockAt(0, 0, otherBlock));
    }

    @Test
    void placeBlockAtTest() {
        BlockType block = Mockito.mock(BlockType.class);
        Mockito.when(block.isPhysical()).thenReturn(true);
        Mockito.when(block.getLayer()).thenReturn(0);
        Box2DBody body = Mockito.mock(Box2DBody.class);
        Mockito.when(block.createBody(world, new IntVector2(0, 0))).thenReturn(body);

        PlaneContainer container = getBuilder().build();
        container.placeBlockAt(0, 0, block);
        assertEquals(new PhysicalBlock(block, body), container.getPhysicalAt(0, 0));
    }

    PlaneContainer prepareRemoveFrontBlockAtTest(ArrayList<BlockType> point) {
        var grid = getDummyGrid();
        grid.get(zeroX).set(zeroY, point);
        return getBuilder().savedGrid(grid).build();
    }

    @Test
    void removeFrontBlockAtTest() {
        var point = new ArrayList<>(List.of(Mockito.mock(BlockType.class), Mockito.mock(BlockType.class)));
        PlaneContainer container = prepareRemoveFrontBlockAtTest(point);
        assertEquals(point.get(0), container.removeFrontBlockAt(0, 0));
    }

    @Test
    void removeFrontBlockAtBlockLeftTest() {
        var point = new ArrayList<>(List.of(Mockito.mock(BlockType.class), Mockito.mock(BlockType.class)));
        PlaneContainer container = prepareRemoveFrontBlockAtTest(point);
        container.removeFrontBlockAt(0, 0);
        assertEquals(point.get(1), container.getFrontBlockAt(0, 0));
    }

    @Test
    void removeFrontBlockAtDestroyBodyTest() {
        PlaneContainer container = getBuilder().build();
        BlockType block = Mockito.mock(BlockType.class);
        Mockito.when(block.isPhysical()).thenReturn(true);
        Mockito.when(block.getLayer()).thenReturn(0);
        Box2DBody body = Mockito.mock(Box2DBody.class);
        Mockito.when(block.createBody(world, new IntVector2(0, 0))).thenReturn(body);
        container.placeBlockAt(0, 0, block);
        container.removeFrontBlockAt(0, 0);
        Mockito.verify(world, Mockito.times(1)).destroyBody(body);
    }

    @Test
    void getLocalConversionTest() {
        PlaneContainer container = getBuilder().build();
        BlockType block = Mockito.mock(BlockType.class);
        Mockito.when(block.getLayer()).thenReturn(0);
        final int a = 0, b = 0;
        container.placeBlockAt(a, b, block);

        final int x = -1, y = -1;
        IntRectangle rectangle = new IntRectangle(x, y, width - zeroX, height - zeroY);
        LocalPlaneContainer local = container.getLocal(rectangle);
        assertEquals(block, local.getBlockAt(a - x, b - y, 0));
    }

    @Test
    void getLocalLeftBottomInclusiveTest() {
        PlaneContainer container = getBuilder().build();
        BlockType block = Mockito.mock(BlockType.class);
        Mockito.when(block.getLayer()).thenReturn(0);
        container.placeBlockAt(0, 0, block);

        IntRectangle rectangle = new IntRectangle(0, 0, width - zeroX, height - zeroY);
        LocalPlaneContainer local = container.getLocal(rectangle);
        assertEquals(block, local.getBlockAt(0, 0, 0));
    }

    @Test
    void getLocalRightTopInclusiveTest() {
        PlaneContainer container = getBuilder().build();
        IntVector2 topRight = new IntVector2(width - zeroX - 1, height - zeroY - 1);
        BlockType block = Mockito.mock(BlockType.class);
        Mockito.when(block.getLayer()).thenReturn(0);
        container.placeBlockAt(topRight, block);
        IntRectangle rectangle = new IntRectangle(new IntVector2(0, 0), topRight);
        LocalPlaneContainer local = container.getLocal(rectangle);
        assertThrows(IndexOutOfBoundsException.class, () -> local.getBlockAt(width - zeroX - 1, height - zeroY - 1, 0));
    }
}
