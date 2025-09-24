package io.github.terraria.loading;

import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OreGenerator {
    private static final Random RANDOM = new Random();
    public static void apply(ArrayList<ArrayList<ArrayList<Block>>> grid, BlockFactory blockFactory, int zeroY) {
        int width = grid.size();
        int height = grid.get(0).size();
        for (int x = 1; x < width - 1; ++x) {
            int y0 = 0, y1 = findGroundLevel(grid, x, zeroY);
            if (y1 >= 0) {
                fillOres(grid, x, y0, y1, blockFactory);
            }
        }
    }
    private static int findGroundLevel(ArrayList<ArrayList<ArrayList<Block>>> grid, int x, int zeroY) {
        for (int y = zeroY; y >= 0; --y) {
            Block block = grid.get(x).get(y).get(0);
            if (block != null && block.type().name().equals("Dirt")) {
                return y;
            }
        }
        return -1;
    }

    private static void fillOres(ArrayList<ArrayList<ArrayList<Block>>> grid, int x, int y0, int y1, BlockFactory blockFactory) {
        List<Block> ores = List.of(
            blockFactory.create("Coal"),
            blockFactory.create("Gold Ore"),
            blockFactory.create("Iron Ore")
            );
        for (int y = y0; y < y1; ++y) {
            if (RANDOM.nextDouble() < 0.1) {
                grid.get(x).get(y).set(0, ores.get(RANDOM.nextInt(ores.size())));
            }
        }
    }
}
