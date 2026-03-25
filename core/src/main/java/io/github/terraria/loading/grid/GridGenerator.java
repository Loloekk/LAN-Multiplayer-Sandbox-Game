package io.github.sandboxGame.loading.grid;

import io.github.sandboxGame.logic.building.Block;
import io.github.sandboxGame.logic.building.BlockFactory;

import java.util.ArrayList;

public interface GridGenerator {
    ArrayList<ArrayList<ArrayList<Block>>> generate(int width, int height, int zeroX, int zeroY, BlockFactory blockFactory);
}
