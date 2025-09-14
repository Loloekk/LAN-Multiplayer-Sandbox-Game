package io.github.terraria.logic.building;

import io.github.terraria.common.Config;
import io.github.terraria.utils.RectangleNeighbourhood;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.World;

import java.util.ArrayList;
import java.util.Collections;

public class StaticPlaneContainer extends PlaneContainer {
    public static final int DEFAULT_WIDTH = Config.STATIC_PLANE_CONTAINER_DEFAULT_WIDTH;
    public static final int DEFAULT_HEIGHT = Config.STATIC_PLANE_CONTAINER_DEFAULT_HEIGHT;
    public static final int layers = Config.STATIC_PLANE_CONTAINER_LAYERS;

    // Słabe, że nie jest wysokość globalnie.
    // [width][height][layer] dla lokalności dostępu.
    // Blok pod [i][j] ma współrzędne w world i - zeroX, i - zeroY.
    // Współrzędne bloku to jego lewy dolny róg.
    // Argumenty do metod są we współrzędnych world.
    private final ArrayList<ArrayList<ArrayList<Block>>> grid;
    private final ArrayList<ArrayList<Body>> bodies;
    private final int width, height;
    private final int zeroX, zeroY;

    StaticPlaneContainer(int width, int height, int zeroX, int zeroY, World world, ArrayList<ArrayList<ArrayList<Block>>> savedGrid, BodyFactory bodyFactory) {
        super(world, bodyFactory);
        this.width = width;
        this.height = height;
        this.zeroX = zeroX;
        this.zeroY = zeroY;
        grid = savedGrid;

        bodies = new ArrayList<>(width); for(int i = 0; i < width; i++) {
            ArrayList<Body> bodiesColumn = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                Block block = grid.get(i).get(j).get(0);
                if(block != null && block.type().isPhysical()) {
                    Body body = bodyFactory.create(block, world, new IntVector2(i - zeroX, j - zeroY));
                    bodiesColumn.add(body);
                }
                else bodiesColumn.add(null);
            }
            bodies.add(bodiesColumn);
        }
    }

    private boolean outOfBounds(int x, int y) {
        return x + zeroX < 0 || x + zeroX >= width || y + zeroY < 0 || y + zeroY >= height;
    }
    // These private methods also take world coordinates as arguments.
    private ArrayList<Block> getPointAt(int x, int y) {
        if(outOfBounds(x, y))
            return new ArrayList<>(Collections.nCopies(StaticPlaneContainer.layers, null));
        return grid.get(x + zeroX).get(y + zeroY);
    }

    private void setPointAt(int x, int y, ArrayList<Block> point) {
        if(!outOfBounds(x, y))
            grid.get(x + zeroX).set(y + zeroY, point);
    }

    private Body getBodyAt(int x, int y) {
        if(outOfBounds(x, y))
            return null;
        return bodies.get(x + zeroX).get(y + zeroY);
    }

    private void setBodyAt(int x, int y, Body body) {
        if(!outOfBounds(x, y))
            bodies.get(x + zeroX).set(y + zeroY, body);
    }

    @Override
    public Block getBlockAt(int x, int y, int layer) {
        return getPointAt(x, y).get(layer);
    }

    @Override
    public Block getFrontBlockAt(int x, int y) {
        ArrayList<Block> point = getPointAt(x, y);
        Block block = point.get(0);
        if(block != null)
            return block;
        return point.get(1);
    }

    @Override
    public PhysicalBlock getPhysicalAt(int x, int y) {
        Block block = getBlockAt(x, y, 0);
        if(block == null || !block.type().isPhysical())
            return null;
        return new PhysicalBlock(block, getBodyAt(x, y));
    }

    @Override
    public boolean placeBlockAt(int x, int y, Block block) {
        return placeBlockAt(x, y, block,
            block.type().isPhysical() ? bodyFactory.create(block, world, new IntVector2(x, y)) : null);
    }

    @Override
    public boolean placeBlockAt(int x, int y, Block block, Body body) {
        int layer = block.type().layer();
        ArrayList<Block> point = getPointAt(x, y);
        if(point.get(layer) != null)
            return false;
        point.set(layer, block);
        setPointAt(x, y, point);
        if(block.type().isPhysical())
            setBodyAt(x, y, body);
        return true;
    }

    @Override
    public Block removeFrontBlockAt(int x, int y) {
        {
            // Zniszcz body.
            Body body = getBodyAt(x, y);
            if(body != null)
                body.destroy();
            setBodyAt(x, y, null);
        }
        ArrayList<Block> point = getPointAt(x, y);
        Block block = point.get(0);
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
        ArrayList<ArrayList<ArrayList<Block>>> localGrid = new ArrayList<>();
        final int beginX = (int) Math.floor(cutNeighbourhood.leftBottom().x);
        final int beginY = (int) Math.floor(cutNeighbourhood.leftBottom().y);
        for (int x = beginX;
             x <= (int) Math.floor(cutNeighbourhood.rightTop().x); x++) {
            ArrayList<ArrayList<Block>> column = new ArrayList<>();
            for (int y = beginY; y <= (int) Math.floor(cutNeighbourhood.rightTop().y); y++) {
                column.add(new ArrayList<>(getPointAt(x, y)));
            }
            localGrid.add(column);
        }
        return new LocalPlaneContainerImpl(-beginX, -beginY, localGrid);
    }

    @Override
    public int getTopY() { return height - 1 - zeroY; }
}
