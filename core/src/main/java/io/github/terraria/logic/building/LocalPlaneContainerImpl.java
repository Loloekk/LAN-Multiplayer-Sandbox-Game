package io.github.sandboxGame.logic.building;

import java.util.ArrayList;

public class LocalPlaneContainerImpl implements LocalPlaneContainer {
    // [width][height][layer] dla lokalności dostępu.
    private final int zeroX, zeroY;
    private final ArrayList<ArrayList<ArrayList<Block>>> grid;
    LocalPlaneContainerImpl(int zeroX, int zeroY, ArrayList<ArrayList<ArrayList<Block>>> grid) {
        this.zeroX = zeroX;
        this.zeroY = zeroY;
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
    public Block getBlockAt(int x, int y, int layer) {
        return grid.get(x + zeroX).get(y + zeroY).get(layer);
    }
}
