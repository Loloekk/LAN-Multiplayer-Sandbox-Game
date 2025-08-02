package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

public class StaticPlaneContainer implements PlaneContainer {
    // Słabe, że nie jest wysokość globalnie.
    private final ArrayList<BlockType[]>[] grid = new ArrayList[2];
    private int height;
    private int zeroX, zeroY;

    @Override
    public void init(int width, int height, int zeroX, int zeroY) {
        this.zeroX = zeroX;
        this.zeroY = zeroY;
        this.height = height;
        for(int l = 0; l < 2; l++) {
            for (int i = 0; i < width; i++) {
                grid[l].add(new BlockType[height]);
            }
        }
    }

    public void init(int width, int height) {
        init(width, height, width / 2, height / 2);
    }

    @Override
    public BlockType getBlockAt(int x, int y, int layer) {
        return null;
    }

    @Override
    public BlockType getFrontBlockAt(int x, int y) {
        return null;
    }

    @Override
    public PhysicalBlock getPhysicalAt(int x, int y) {
        return null;
    }

    @Override
    public boolean placeBlockAt(int x, int y, BlockType block) {
        // Jeżeli block jest fizyczny, to oddeleguj tworzenie body gdzie indziej.
        return false;
    }

    @Override
    public boolean placeBlockAt(int x, int y, BlockType block, Body body) {
        return false;
    }

    @Override
    public BlockType removeFrontBlockAt(int x, int y) {
        return null;
    }
}
