package io.github.sandboxGame.logic.building;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class LocalPlaneContainerImplTest {
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

    @Test
    void getBlockAt() {
        var grid = getDummyGrid();
        Block block = new BlockBuilder().layer(0).build();
        grid.get(zeroX).get(zeroY).set(0, block);
        LocalPlaneContainer container = new LocalPlaneContainerImpl(zeroX, zeroY, grid);
        assertEquals(block, container.getBlockAt(0, 0, 0));
    }
}
