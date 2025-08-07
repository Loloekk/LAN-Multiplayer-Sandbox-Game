package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.terraria.logic.IntRectangle;

import java.util.ArrayList;

public class StaticPlaneContainer implements PlaneContainer {
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    private static final int layers = 2;

    // Słabe, że nie jest wysokość globalnie.
    // [width][height][layer] dla lokalności dostępu.
    private final ArrayList<ArrayList<ArrayList<BlockType>>> grid;
    private final ArrayList<ArrayList<Body>> bodies;
    private final int width;
    private final int height;
    private final int zeroX, zeroY;
    private final World world;

    StaticPlaneContainer(int width, int height, int zeroX, int zeroY, World world) {
        this.width = width;
        this.height = height;
        this.zeroX = zeroX;
        this.zeroY = zeroY;
        this.world = world;

        // Add buffer blocks frame to the world.
        grid = new ArrayList<>(width);
        bodies = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            grid.add(new ArrayList<>(height));
            bodies.add(new ArrayList<>(height));
            for (int j = 0; j < height; j++) {
                ArrayList<BlockType> point = new ArrayList<>(layers);
                point.add(null);
                {
                    BlockType frontBlock = null;
                    Body body = null;
                    if (j < zeroY) {
                        frontBlock = new IntBlockType(0);
                        // Set body appropriately.
                    }
                    point.add(frontBlock);
                    bodies.get(i).add(body);
                }
                grid.get(i).add(point);
            }
        }
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
        // Zniszcz body.
        return null;
    }

    @Override
    public LocalPlaneContainer getLocal(IntRectangle neighbourhood) {
        ArrayList<ArrayList<ArrayList<BlockType>>> localGrid = new ArrayList<>();
        for (int x = neighbourhood.leftBottom().x(); x < neighbourhood.rightTop().x(); x++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>();
            for (int y = neighbourhood.leftBottom().y(); y < neighbourhood.rightTop().y(); y++)
                column.add(new ArrayList<>(grid.get(x).get(y)));
            localGrid.add(column);
        }
        return new LocalPlaneContainerImpl(localGrid);
    }
}
