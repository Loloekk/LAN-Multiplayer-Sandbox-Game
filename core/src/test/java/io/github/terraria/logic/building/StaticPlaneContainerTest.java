package io.github.terraria.logic.building;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.utils.RectangleNeighbourhood;
import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.Box2DBody;
import io.github.terraria.logic.physics.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaticPlaneContainerTest {
    final BodyFactory bodyFactory = Mockito.mock(BodyFactory.class);
    final World world = Mockito.mock(World.class);
    final int width = 10;
    final int height = 20;
    final int zeroX = 5;
    final int zeroY = 5;

    ArrayList<ArrayList<ArrayList<Block>>> getDummyGrid() {
        ArrayList<ArrayList<ArrayList<Block>>> grid = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<ArrayList<Block>> column = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                column.add(new ArrayList<>(Collections.nCopies(StaticPlaneContainer.layers, null)));
            }
            grid.add(column);
        }
        return grid;
    }

    StaticPlaneContainerBuilder getBuilder() {
        BlockFactory blockFactory = Mockito.mock(BlockFactory.class);
        Mockito.when(blockFactory.create("Stone")).thenReturn(new BlockBuilder().build());
        return new StaticPlaneContainerBuilder().blockFactory(blockFactory).savedGrid(getDummyGrid()).bodyFactory(bodyFactory).width(width).height(height).zeroX(zeroX).zeroY(zeroY).world(world);
    }

    @Test
    void checkBodyCreation() {
        var grid = getDummyGrid();
        Block block = new BlockBuilder().layer(0).build();
        grid.get(zeroX).get(zeroY).set(0, block);
        getBuilder().savedGrid(grid).build();
        Mockito.verify(bodyFactory, Mockito.times(1))
            .create(block, world, new IntVector2(0, 0));
    }

    @Test
    void conversionTest() {
        var grid = getDummyGrid();
        Block block = new BlockBuilder().layer(0).build();
        grid.get(zeroX).get(zeroY).set(0, block);
        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertEquals(block, container.getBlockAt(0, 0, 0));
    }

    @Test
    void getFrontBlockAtTest() {
        var grid = getDummyGrid();
        Block block = new BlockBuilder().layer(1).build();
        grid.get(zeroX).get(zeroY).set(1, block);
        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertEquals(block, container.getFrontBlockAt(0, 0));
    }

    @Test
    void getPhysicalNoFrontBlockTest() {
        var grid = getDummyGrid();
        Block block = new BlockBuilder().layer(1).build();
        grid.get(zeroX).get(zeroY).set(1, block);
        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertNull(container.getPhysicalAt(0, 0));
    }

    @Test
    void getPhysicalAtTest() {
        var grid = getDummyGrid();
        Block block = new BlockBuilder().isPhysical(true).build();
        grid.get(zeroX).get(zeroY).set(0, block);

        Box2DBody body = Mockito.mock(Box2DBody.class);
        Mockito.when(bodyFactory.create(block, world, new IntVector2(0, 0))).thenReturn(body);

        PlaneContainer container = getBuilder().savedGrid(grid).build();
        assertEquals(new PhysicalBlock(block, body), container.getPhysicalAt(0, 0));
    }

    @Test
    void placeBlockAtOccupiedTest() {
        var grid = getDummyGrid();
        Block block = new BlockBuilder().layer(0).build();
        grid.get(zeroX).get(zeroY).set(0, block);

        PlaneContainer container = getBuilder().savedGrid(grid).build();
        Block otherBlock = new BlockBuilder().layer(0).build();
        assertFalse(container.placeBlockAt(0, 0, otherBlock));
    }

    @Test
    void placeBlockAtTest() {
        Block block = new BlockBuilder().isPhysical(true).build();
        Box2DBody body = Mockito.mock(Box2DBody.class);
        Mockito.when(bodyFactory.create(block, world, new IntVector2(0, 0))).thenReturn(body);

        PlaneContainer container = getBuilder().build();
        container.placeBlockAt(0, 0, block);
        assertEquals(new PhysicalBlock(block, body), container.getPhysicalAt(0, 0));
    }

    PlaneContainer prepareRemoveFrontBlockAtTest(ArrayList<Block> point) {
        var grid = getDummyGrid();
        grid.get(zeroX).set(zeroY, point);
        return getBuilder().savedGrid(grid).build();
    }
    ArrayList<Block> getDummyPoint() {
        return new ArrayList<>(List.of(new BlockBuilder().layer(0).build(),
            new BlockBuilder().layer(1).build()));
    }

    @Test
    void removeFrontBlockAtTest() {
        var point = getDummyPoint();
        PlaneContainer container = prepareRemoveFrontBlockAtTest(point);
        assertEquals(point.get(0), container.removeFrontBlockAt(0, 0));
    }

    @Test
    void removeFrontBlockAtBlockLeftTest() {
        var point = getDummyPoint();
        PlaneContainer container = prepareRemoveFrontBlockAtTest(point);
        container.removeFrontBlockAt(0, 0);
        assertEquals(point.get(1), container.getFrontBlockAt(0, 0));
    }

    @Test
    void removeFrontBlockAtDestroyBodyTest() {
        PlaneContainer container = getBuilder().build();
        Block block = new BlockBuilder().isPhysical(true).build();
        Box2DBody body = Mockito.mock(Box2DBody.class);
        Mockito.when(bodyFactory.create(block, world, new IntVector2(0, 0))).thenReturn(body);
        container.placeBlockAt(0, 0, block);
        container.removeFrontBlockAt(0, 0);
        Mockito.verify(body).destroy();
    }

    @Test
    void getLocalConversionTest() {
        PlaneContainer container = getBuilder().build();
        Block block = new BlockBuilder().layer(0).build();
        final int a = 0, b = 0;
        container.placeBlockAt(a, b, block);

        final int x = -1, y = -2;
        RectangleNeighbourhood rectangle = new RectangleNeighbourhood(x, y, width - zeroX, height - zeroY);
        LocalPlaneContainer local = container.getLocal(rectangle);
        assertEquals(block, local.getBlockAt(a, b, 0));
    }

    @Test
    void getLocalLeftBottomInclusiveTest() {
        PlaneContainer container = getBuilder().build();
        Block block = new BlockBuilder().layer(0).build();
        container.placeBlockAt(0, 0, block);

        RectangleNeighbourhood rectangle = new RectangleNeighbourhood(0.5f, 0.6f, width - zeroX, height - zeroY);
        LocalPlaneContainer local = container.getLocal(rectangle);
        assertEquals(block, local.getBlockAt(0, 0, 0));
    }

    @Test
    void getLocalOutOfBoundsTest() {
        PlaneContainer container = getBuilder().build();
        Vector2 outOfBounds = new Vector2(-zeroX - 1, -zeroY - 1);
        RectangleNeighbourhood rectangle = new RectangleNeighbourhood(outOfBounds, new Vector2());
        container.getLocal(rectangle);
    }
}
