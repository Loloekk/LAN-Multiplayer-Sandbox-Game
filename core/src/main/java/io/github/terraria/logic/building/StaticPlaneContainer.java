package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.terraria.logic.IntRectangle;
import io.github.terraria.logic.IntVector2;

import java.util.ArrayList;

public class StaticPlaneContainer implements PlaneContainer {
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    private static final int layers = 2;

    // Słabe, że nie jest wysokość globalnie.
    // [width][height][layer] dla lokalności dostępu.
    // Blok pod [i][j] ma współrzędne w world i - zeroX, i - zeroY.
    // Argumenty do metod są we współrzędnych world.
    // TODO: Weryfikacja powyższej konwencji (metody do konwersji współrzędnych).
    private final ArrayList<ArrayList<ArrayList<BlockType>>> grid;
    private final ArrayList<ArrayList<Body>> bodies;
    private final int zeroX, zeroY;
    private final World world;

    StaticPlaneContainer(int width, int height, int zeroX, int zeroY, World world) {
        this.zeroX = zeroX;
        this.zeroY = zeroY;
        this.world = world;

        // Add buffer blocks frame to the world.
        grid = new ArrayList<>(width);
        bodies = new ArrayList<>(width);
        for(int x = 0; x < width; x++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>(height);
            ArrayList<Body> bodiesColumn = new ArrayList<>(height);
            for (int y = 0; y < height; y++) {
                ArrayList<BlockType> point = new ArrayList<>(layers);
                {
                    BlockType frontBlock = null;
                    Body body = null;
                    if (y < zeroY) {
                        frontBlock = new BlockType(0);
                        body = frontBlock.createBody(world, new IntVector2(x - zeroX, y - zeroY));
                    }
                    point.add(frontBlock);
                    bodiesColumn.add(body);
                }
                point.add(null);
                column.add(point);
            }
            grid.add(column);
            bodies.add(bodiesColumn);
        }
    }

    // Best thin, so can be used efficiently in other methods.
    @Override
    public BlockType getBlockAt(int x, int y, int layer) {
        return grid.get(x + zeroX).get(y + zeroY).get(layer);
    }

    @Override
    public BlockType getFrontBlockAt(int x, int y) {
        BlockType block = getBlockAt(x, y, 0);
        if(block != null)
            return block;
        return getBlockAt(x, y, 1);
    }

    @Override
    public PhysicalBlock getPhysicalAt(int x, int y) {
        return new PhysicalBlock(getBlockAt(x, y, 0), bodies.get(x + zeroX).get(y + zeroY));
    }

    @Override
    public boolean placeBlockAt(int x, int y, BlockType block) {
        return placeBlockAt(x, y, block,
            block.isPhysical() ? null : block.createBody(world, new IntVector2(x, y)));
    }

    @Override
    public boolean placeBlockAt(int x, int y, BlockType block, Body body) {
        int layer = block.getLayer();
        if(getBlockAt(x, y, layer) != null)
            return false;
        grid.get(x + zeroX).get(y + zeroY).set(layer, block);
        if(block.isPhysical()) {
            Body oldBody = bodies.get(x + zeroX).get(y + zeroY);
            if (oldBody != null)
                oldBody.getWorld().destroyBody(oldBody);
            bodies.get(x + zeroX).set(y + zeroY, body);
        }
        return true;
    }

    @Override
    public BlockType removeFrontBlockAt(int x, int y) {
        {
            // Zniszcz body.
            Body body = bodies.get(x + zeroX).get(y + zeroY);
            if(body != null)
                body.getWorld().destroyBody(body);
            bodies.get(x + zeroX).set(y + zeroY, null);
        }
        BlockType block = getBlockAt(x, y, 0);
        grid.get(x + zeroX).get(y + zeroY).set(0, null);
        if(block != null)
            return block;
        grid.get(x + zeroX).get(y + zeroY).set(1, null);
        return getBlockAt(x, y, 1);
    }

    @Override
    public LocalPlaneContainer getLocal(IntRectangle neighbourhood) {
        ArrayList<ArrayList<ArrayList<BlockType>>> localGrid = new ArrayList<>();
        for (int x = neighbourhood.leftBottom().x(); x < neighbourhood.rightTop().x(); x++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>();
            for (int y = neighbourhood.leftBottom().y(); y < neighbourhood.rightTop().y(); y++)
                column.add(new ArrayList<>(grid.get(x + zeroX).get(y + zeroY)));
            localGrid.add(column);
        }
        return new LocalPlaneContainerImpl(localGrid);
    }
}
