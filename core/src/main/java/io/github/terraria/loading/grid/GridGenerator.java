package io.github.terraria.loading.grid;

import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockFactory;

import java.util.ArrayList;

public interface GridGenerator {
    ArrayList<ArrayList<ArrayList<Block>>> generate(int width, int height, int zeroX, int zeroY, BlockFactory blockFactory);
}
