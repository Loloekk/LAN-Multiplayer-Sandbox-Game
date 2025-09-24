package io.github.terraria.loading;

import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockFactory;

import java.util.ArrayList;
import java.util.Random;

public class TreeGenerator {
    private static final Random RANDOM = new Random();
    public static void apply(ArrayList<ArrayList<ArrayList<Block>>> grid, BlockFactory blockFactory, int zeroY) {
        int width = grid.size();
        int height = grid.get(0).size();
        for (int x = 1; x < width - 1; x += 3 + RANDOM.nextInt(10)) {
            int y = findGroundLevel(grid, x, zeroY, blockFactory);
            if (y >= 0) {
                placeTree(grid, x, y + 1, blockFactory, height);
            }
        }
    }
    private static int findGroundLevel(ArrayList<ArrayList<ArrayList<Block>>> grid, int x, int zeroY, BlockFactory blockFactory) {
        for (int y = zeroY; y >= 0; --y) {
            Block block = grid.get(x).get(y).get(0);
            if (block != null && block.type().name().equals("Dirt")) {
                return y;
            }
        }
        return -1;
    }
    private static void placeTree(ArrayList<ArrayList<ArrayList<Block>>> grid, int x, int y, BlockFactory blockFactory, int height) {
        int trunkHeight = 3 + RANDOM.nextInt(4);
        for (int i = y; i < y + trunkHeight && i < height; ++i) {
            grid.get(x).get(i).set(1, blockFactory.create("Wood"));
        }
        int top = y + trunkHeight - 1;
        for (int dx = -2; dx <= 2; ++dx) {
            for (int dy = 0; dy <= 2; ++dy) {
                if (top + dy >= height || x + dx < 0 || x + dx >= grid.size()) {
                    continue;
                }
                if (Math.abs(dx) + dy < 3) {
                    grid.get(x + dx).get(top + dy).set(1, blockFactory.create("Leaf"));
                }
            }
        }
    }
}
