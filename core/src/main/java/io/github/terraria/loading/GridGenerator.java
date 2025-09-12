package io.github.terraria.loading;

import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockFactory;
import io.github.terraria.logic.building.StaticPlaneContainer;
import io.github.terraria.utils.MathUtils;

import java.util.ArrayList;

public class GridGenerator {
    public static ArrayList<ArrayList<ArrayList<Block>>> getDefaultGrid(int width, int height, int zeroY, BlockFactory blockFactory) {
        ArrayList<ArrayList<ArrayList<Block>>> defaultGrid = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            ArrayList<ArrayList<Block>> column = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                ArrayList<Block> point = new ArrayList<>(StaticPlaneContainer.layers);
                {
                    Block frontBlock = (j < zeroY || i == 0 || i == width-1 || (i == 6 && j < zeroY + 3) || (i == 7 && j == zeroY + 3) || (i == 8 && j == zeroY + 3)) ? blockFactory.create("Dirt") : null;
                    point.add(frontBlock);
                }
                point.add(null);
                column.add(point);
            }
            defaultGrid.add(column);
        }
        return defaultGrid;
    }

    public static ArrayList<ArrayList<ArrayList<Block>>> getRandomGrid(int width, int height, int zeroY, BlockFactory blockFactory) {
        ArrayList<ArrayList<ArrayList<Block>>> defaultGrid = new ArrayList<>(width);
        final int maxDeviation = 3;
        int groundLevel = zeroY;
        for(int i = 0; i < width; i++) {
            ArrayList<ArrayList<Block>> column = new ArrayList<>(height);
            groundLevel += MathUtils.binomialRandom(maxDeviation);
            for (int j = 0; j < height; j++) {
                ArrayList<Block> point = new ArrayList<>(StaticPlaneContainer.layers);
                {
                    Block frontBlock = (j <= groundLevel) ? blockFactory.create("Dirt") : null;
                    point.add(frontBlock);
                }
                point.add(null);
                column.add(point);
            }
            defaultGrid.add(column);
        }
        return defaultGrid;
    }
}
