package io.github.terraria.logic.building;

import io.github.terraria.logic.physics.BodyFactoryLoader;
import io.github.terraria.logic.physics.StaticBoundaryFactory;

import java.util.ArrayList;

public class StaticPlaneContainerBuilder extends PlaneContainerBuilder {
    // TODO: Clean up the StaticPlaneContainerBuilder : PlaneContainerBuilder hierarchy.
    private BlockFactory blockFactory;
    public StaticPlaneContainerBuilder blockFactory(BlockFactory blockFactory) {
        this.blockFactory = blockFactory;
        return this;
    }
    private StaticBoundaryFactory staticBoundaryFactory;
    public StaticPlaneContainerBuilder boundaryFactory(StaticBoundaryFactory staticBoundaryFactory) {
        this.staticBoundaryFactory = staticBoundaryFactory;
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
