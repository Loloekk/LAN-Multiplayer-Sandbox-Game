package io.github.terraria.logic.building;

import io.github.terraria.logic.IntRectangle;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.World;

import java.util.ArrayList;

public class StaticPlaneContainer implements PlaneContainer {
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    public static final int layers = 2;

    // Słabe, że nie jest wysokość globalnie.
    // [width][height][layer] dla lokalności dostępu.
    // Blok pod [i][j] ma współrzędne w world i - zeroX, i - zeroY.
    // TODO - rozważyć: Blok ma wymiary 1x1 w world. To raczej nie jest restrykcyjne, ale można ewentualnie uwzględniać skalowanie wszędzie.
    // Argumenty do metod są we współrzędnych world.
    private final ArrayList<ArrayList<ArrayList<BlockType>>> grid;
    private final ArrayList<ArrayList<Body>> bodies;
    private final int zeroX, zeroY;
    private final World world;

    StaticPlaneContainer(int width, int height, int zeroX, int zeroY, World world, ArrayList<ArrayList<ArrayList<BlockType>>> savedGrid) {
        this.zeroX = zeroX;
        this.zeroY = zeroY;
        this.world = world;
        grid = savedGrid;

        bufferBlocks(width, height, zeroX, zeroY, world);

        bodies = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<Body> bodiesColumn = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                BlockType block = grid.get(i).get(j).get(0);
                if(block != null && block.isPhysical()) {
                    Body body = block.createBody(world, new IntVector2(i - zeroX, j - zeroY));
                    bodiesColumn.add(body);
                }
                else bodiesColumn.add(null);
            }
            bodies.add(bodiesColumn);
        }
    }

    private static void bufferBlocks(int width, int height, int zeroX, int zeroY, World world) {
        // Adding buffer blocks around the map.
        {
            final int bufferBlockId = -1;
            BlockType block;
            final int left =  -1 - zeroX, right = width - zeroX;
            final int bottom = -1 - zeroY, top = height - zeroY;
            for (int x = left + 1; x < right; x++) {
                block = new BlockTypeImpl(bufferBlockId);
                block.createBody(world, new IntVector2(x, bottom));
                block = new BlockTypeImpl(bufferBlockId);
                block.createBody(world, new IntVector2(x, top));
            }
            for (int y = bottom + 1; y < top; y++) {
                block = new BlockTypeImpl(bufferBlockId);
                block.createBody(world, new IntVector2(left, y));
                block = new BlockTypeImpl(bufferBlockId);
                block.createBody(world, new IntVector2(right, y));
            }
        }
    }

    // These private methods also take world coordinates as arguments.
    private ArrayList<BlockType> getPointAt(int x, int y) {
        return grid.get(x + zeroX).get(y + zeroY);
    }

    private void setPointAt(int x, int y, ArrayList<BlockType> point) {
        grid.get(x + zeroX).set(y + zeroY, point);
    }

    private Body getBodyAt(int x, int y) {
        return bodies.get(x + zeroX).get(y + zeroY);
    }

    private void setBodyAt(int x, int y, Body body) {
        bodies.get(x + zeroX).set(y + zeroY, body);
    }

    // Best thin, so can be used efficiently in other methods.
    @Override
    public BlockType getBlockAt(int x, int y, int layer) {
        return getPointAt(x, y).get(layer);
    }

    @Override
    public BlockType getFrontBlockAt(int x, int y) {
        ArrayList<BlockType> point = getPointAt(x, y);
        BlockType block = point.get(0);
        if(block != null)
            return block;
        return point.get(1);
    }

    @Override
    public PhysicalBlock getPhysicalAt(int x, int y) {
        BlockType block = getBlockAt(x, y, 0);
        if(block == null || !block.isPhysical())
            return null;
        return new PhysicalBlock(block, getBodyAt(x, y));
    }

    @Override
    public boolean placeBlockAt(int x, int y, BlockType block) {
        return placeBlockAt(x, y, block,
            block.isPhysical() ? block.createBody(world, new IntVector2(x, y)) : null);
    }

    @Override
    public boolean placeBlockAt(int x, int y, BlockType block, Body body) {
        int layer = block.getLayer();
        ArrayList<BlockType> point = getPointAt(x, y);
        if(point.get(layer) != null)
            return false;
        point.set(layer, block);
        setPointAt(x, y, point);
        if(block.isPhysical())
            setBodyAt(x, y, body);
        return true;
    }

    @Override
    public BlockType removeFrontBlockAt(int x, int y) {
        {
            // Zniszcz body.
            Body body = getBodyAt(x, y);
            if(body != null)
                world.destroyBody(body);
            setBodyAt(x, y, null);
        }
        ArrayList<BlockType> point = getPointAt(x, y);
        BlockType block = point.get(0);
        if(block != null) {
            point.set(0, null);
        }
        else {
            block = point.get(1);
            point.set(1, null);
        }
        setPointAt(x, y, point);
        return block;
    }

    @Override
    public LocalPlaneContainer getLocal(IntRectangle neighbourhood) {
        // Consider checking for out of bounds?
        ArrayList<ArrayList<ArrayList<BlockType>>> localGrid = new ArrayList<>();
        for (int x = neighbourhood.leftBottom().x(); x < neighbourhood.rightTop().x(); x++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>();
            for (int y = neighbourhood.leftBottom().y(); y < neighbourhood.rightTop().y(); y++)
                column.add(new ArrayList<>(getPointAt(x, y)));
            localGrid.add(column);
        }
        return new LocalPlaneContainerImpl(localGrid);
    }
}
