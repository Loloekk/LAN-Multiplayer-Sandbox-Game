package io.github.terraria.logic.building;

import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.BodyFactoryLoader;
import io.github.terraria.logic.physics.StaticBoundaryFactory;
import io.github.terraria.logic.physics.World;

import java.util.ArrayList;

public class StaticPlaneContainerBuilder extends PlaneContainerBuilder {
    private Integer width;
    private Integer height;
    private Integer zeroX;
    private Integer zeroY;
    private ArrayList<ArrayList<ArrayList<BlockType>>> savedGrid;
    private BlockFactory blockFactory;
    private StaticBoundaryFactory staticBoundaryFactory;
    public StaticPlaneContainerBuilder width(int width) {
        this.width = width;
        return this;
    }
    public StaticPlaneContainerBuilder height(int height) {
        this.height = height;
        return this;
    }
    public StaticPlaneContainerBuilder zeroX(int zeroX) {
        this.zeroX = zeroX;
        return this;
    }
    public StaticPlaneContainerBuilder zeroY(int zeroY) {
        this.zeroY = zeroY;
        return this;
    }
    public PlaneContainerBuilder savedGrid(ArrayList<ArrayList<ArrayList<BlockType>>> savedGrid) {
        this.savedGrid = savedGrid;
        return this;
    }
    public StaticPlaneContainerBuilder blockFactory(BlockFactory blockFactory) {
        this.blockFactory = blockFactory;
        return this;
    }
    public StaticPlaneContainerBuilder boundaryFactory(StaticBoundaryFactory staticBoundaryFactory) {
        this.staticBoundaryFactory = staticBoundaryFactory;
        return this;
    }

    @Override
    public StaticPlaneContainerBuilder world(World world) {
        this.world = world;
        return this;
    }
    @Override
    public StaticPlaneContainerBuilder bodyFactory(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
        return this;
    }

    private ArrayList<ArrayList<ArrayList<BlockType>>> getDefaultGrid(int width, int height, int zeroY) {
        if(blockFactory == null)
            blockFactory = new BlockFactoryLoader().getBlockFactory();
        ArrayList<ArrayList<ArrayList<BlockType>>> defaultGrid = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<ArrayList<BlockType>> column = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                ArrayList<BlockType> point = new ArrayList<>(StaticPlaneContainer.layers);
                {
                    BlockType frontBlock = (j < zeroY) ? blockFactory.create(1) : null;
                    point.add(frontBlock);
                }
                point.add(null);
                column.add(point);
            }
            defaultGrid.add(column);
        }
        return defaultGrid;
    }

    @Override
    public StaticPlaneContainer build() {
        if(width == null)
            width = StaticPlaneContainer.DEFAULT_WIDTH;
        if(height == null)
            height = StaticPlaneContainer.DEFAULT_HEIGHT;
        if(zeroX == null)
            zeroX = width / 2;
        if(zeroY == null)
            zeroY = height / 2;
        if(zeroX < 0 || zeroX >= width || zeroY < 0 || zeroY >= height || world == null)
            return null;
        // The above implies positivity of width and height.
        if(staticBoundaryFactory == null)
            staticBoundaryFactory = new StaticBoundaryFactory();
        staticBoundaryFactory.createBoundaries(width, height, -zeroX, -zeroY, world);
        if(bodyFactory == null)
            bodyFactory = new BodyFactoryLoader().getBodyFactory();
        if(savedGrid == null)
            savedGrid = getDefaultGrid(width, height, zeroY);

        return new StaticPlaneContainer(width, height, zeroX, zeroY, world, savedGrid, bodyFactory);
    }
}
