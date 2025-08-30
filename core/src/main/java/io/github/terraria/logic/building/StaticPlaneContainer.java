package io.github.terraria.logic.building;

import io.github.terraria.logic.RectangleNeighbourhood;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.World;

import java.util.ArrayList;

public class StaticPlaneContainer extends PlaneContainer {
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    public static final int layers = 2;

    // Słabe, że nie jest wysokość globalnie.
    // [width][height][layer] dla lokalności dostępu.
    // Blok pod [i][j] ma współrzędne w world i - zeroX, i - zeroY.
    // Współrzędne bloku to jego lewy dolny róg.
    // Argumenty do metod są we współrzędnych world.
    private final ArrayList<ArrayList<ArrayList<BlockType>>> grid;
    private final ArrayList<ArrayList<Body>> bodies;
    private final int width, height;
    private final int zeroX, zeroY;

    StaticPlaneContainer(int width, int height, int zeroX, int zeroY, World world, ArrayList<ArrayList<ArrayList<BlockType>>> savedGrid, BodyFactory bodyFactory) {
        super(world, bodyFactory);
        this.width = width;
        this.height = height;
        this.zeroX = zeroX;
        this.zeroY = zeroY;
        grid = savedGrid;

        bodies = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<Body> bodiesColumn = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                BlockType block = grid.get(i).get(j).get(0);
                if(block != null && block.isPhysical()) {
                    Body body = bodyFactory.create(block, world, new IntVector2(i - zeroX, j - zeroY));
                    bodiesColumn.add(body);
                }
                else bodiesColumn.add(null);
            }
            bodies.add(bodiesColumn);
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
            block.isPhysical() ? bodyFactory.create(block, world, new IntVector2(x, y)) : null);
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
    public LocalPlaneContainer getLocal(RectangleNeighbourhood neighbourhood) {
        RectangleNeighbourhood cutNeighbourhood = new RectangleNeighbourhood(
            Math.max(neighbourhood.leftBottom().x, -zeroX),
            Math.max(neighbourhood.leftBottom().y, -zeroY),
            Math.min(neighbourhood.rightTop().x, width - zeroX - 1),
            Math.min(neighbourhood.rightTop().y, height - zeroY - 1)
        );
        ArrayList<ArrayList<ArrayList<BlockType>>> localGrid = new ArrayList<>();
        final int beginX = (int) Math.floor(cutNeighbourhood.leftBottom().x);
        final int beginY = (int) Math.floor(cutNeighbourhood.leftBottom().y);
        for (int x = beginX;
             x <= (int) Math.floor(cutNeighbourhood.rightTop().x); x++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>();
            for (int y = beginY; y <= (int) Math.floor(cutNeighbourhood.rightTop().y); y++) {
                column.add(new ArrayList<>(getPointAt(x, y)));
            }
            localGrid.add(column);
        }
        return new LocalPlaneContainerImpl(-beginX, -beginY, localGrid);
    }
}
