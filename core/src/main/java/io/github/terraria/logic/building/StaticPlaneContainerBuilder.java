package io.github.terraria.logic.building;

import io.github.terraria.logic.physics.*;

import java.util.ArrayList;

public class StaticPlaneContainerBuilder extends PlaneContainerBuilder {
    private Integer width;
    private Integer height;
    private Integer zeroX;
    private Integer zeroY;
    private ArrayList<ArrayList<ArrayList<Block>>> savedGrid;
    private BlockFactory blockFactory;
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
    public PlaneContainerBuilder savedGrid(ArrayList<ArrayList<ArrayList<Block>>> savedGrid) {
        this.savedGrid = savedGrid;
        return this;
    }
    public StaticPlaneContainerBuilder blockFactory(BlockFactory blockFactory) {
        this.blockFactory = blockFactory;
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

    private ArrayList<ArrayList<ArrayList<Block>>> getDefaultGrid(int width, int height, int zeroY) {
        if(blockFactory == null)
            blockFactory = new BlockFactoryLoader().getBlockFactory();
        ArrayList<ArrayList<ArrayList<Block>>> defaultGrid = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<ArrayList<Block>> column = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                ArrayList<Block> point = new ArrayList<>(StaticPlaneContainer.layers);
                {
                    Block frontBlock = (j < zeroY || i == 0 || i == width-1) ? blockFactory.create("Stone") : null;
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
            zeroX = 0;
        if(zeroY == null)
            zeroY = 0;
        if(zeroX < 0 || zeroX >= width || zeroY < 0 || zeroY >= height || world == null)
            return null;
        // The above implies positivity of width and height.
        world.createBoundaries(width, height, -zeroX, -zeroY);
        if(bodyFactory == null)
            bodyFactory = new BodyFactoryLoader().getBodyFactory();
        if(savedGrid == null)
            savedGrid = getDefaultGrid(width, height, zeroY);

        return new StaticPlaneContainer(width, height, zeroX, zeroY, world, savedGrid, bodyFactory);
    }
}
