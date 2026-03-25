package io.github.sandboxGame.loading.grid;

import io.github.sandboxGame.logic.building.Block;
import io.github.sandboxGame.logic.building.BlockFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StoneGeneratorDecorator extends GridGeneratorDecorator {
    Random RANDOM = new Random();

    public StoneGeneratorDecorator(GridGenerator generator) {
        super(generator);
    }

    @Override
    public ArrayList<ArrayList<ArrayList<Block>>> generate(int width, int height, int zeroX, int zeroY, BlockFactory blockFactory) {
        var grid = generator.generate(width, height, zeroX, zeroY, blockFactory);
        for (int x = 0; x < width; ++x) {
            int y0 = 0, y1 = findDirtLevel(grid, x, height);
            if (y1 >= 0) {
                fillStones(grid, x, y0, y1 - 5, blockFactory);
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
    private void fillStones(ArrayList<ArrayList<ArrayList<Block>>> grid, int x, int y0, int y1, BlockFactory blockFactory) {
        for (int y = y0; y < y1; ++y) {
            String name = (RANDOM.nextDouble() < 0.1 ? "Dirt" : "Stone");
            grid.get(x).get(y).set(0, blockFactory.create(name));
        }
    }
}
