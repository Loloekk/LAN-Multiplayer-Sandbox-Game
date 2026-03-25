package io.github.sandboxGame.loading.grid;

import io.github.sandboxGame.logic.building.Block;
import io.github.sandboxGame.logic.building.BlockFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OreGeneratorDecorator extends GridGeneratorDecorator {
    Random RANDOM = new Random();

    public OreGeneratorDecorator(GridGenerator generator) {
        super(generator);
    }

    @Override
    public ArrayList<ArrayList<ArrayList<Block>>> generate(int width, int height, int zeroX, int zeroY, BlockFactory blockFactory) {
        var grid = generator.generate(width, height, zeroX, zeroY, blockFactory);
        for (int x = 1; x < width - 1; ++x) {
            int y0 = 0, y1 = findDirtLevel(grid, x, height);
            if (y1 >= 0) {
                fillOres(grid, x, y0, y1, blockFactory);
            }
        }
        return grid;
    }
    private int findDirtLevel(ArrayList<ArrayList<ArrayList<Block>>> grid, int x, int height) {
        for (int y = height - 1; y >= 0; --y) {
            Block block = grid.get(x).get(y).get(0);
            if (block != null && block.type().name().equals("Dirt")) {
                return y;
            }
        }
        return -1;
    }
    private void fillOres(ArrayList<ArrayList<ArrayList<Block>>> grid, int x, int y0, int y1, BlockFactory blockFactory) {
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
