package io.github.terraria.logic.building;

import java.util.ArrayList;

public class LocalPlaneContainerImpl implements LocalPlaneContainer {
    // [width][height][layer] dla lokalności dostępu.
    private final ArrayList<ArrayList<ArrayList<BlockType>>> grid;
    LocalPlaneContainerImpl(ArrayList<ArrayList<ArrayList<BlockType>>> grid) {
        this.grid = grid;
    }

    @Override
    public int getWidth() {
        return grid.size();
    }

    @Override
    public int getHeight() {
        if(grid.isEmpty())
            return 0;
        return grid.get(0).size();
    }

    @Override
    public BlockType getBlockAt(int x, int y, int layer) {
        return grid.get(x).get(y).get(layer);
    }
}
