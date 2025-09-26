package io.github.terraria.loading.grid;

import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockFactory;
import io.github.terraria.logic.building.StaticPlaneContainer;
import io.github.terraria.utils.MathUtils;

import java.util.ArrayList;

public class BaseGridGenerator implements GridGenerator {
    @Override
    public ArrayList<ArrayList<ArrayList<Block>>> generate(int width, int height, int zeroX, int zeroY, BlockFactory blockFactory) {
        ArrayList<ArrayList<ArrayList<Block>>> defaultGrid = new ArrayList<>(width);
        for (int i = 0; i < width; i++) { defaultGrid.add(null); }
        final int maxDeviation = 3;
        for (int direction : new int[]{-1, 1}) {
            int groundLevel = zeroY;
            for(int i = zeroX; i >= 0 && i < width; i += direction) {
                ArrayList<ArrayList<Block>> column = new ArrayList<>(height);
                groundLevel += MathUtils.binomialRandom(maxDeviation);
                for (int j = 0; j < height; j++) {
                    ArrayList<Block> point = new ArrayList<>(StaticPlaneContainer.layers);
                    {
                        Block frontBlock;
                        frontBlock = (j <= groundLevel) ? blockFactory.create("Dirt") : null;
                        point.add(frontBlock);
                    }
                    point.add(null);
                    column.add(point);
                }
                defaultGrid.set(i, column);
            }
        }
        return defaultGrid;
    }
}
