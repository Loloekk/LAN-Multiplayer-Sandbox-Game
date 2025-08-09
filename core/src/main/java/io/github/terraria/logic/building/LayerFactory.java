package io.github.terraria.logic.building;

import java.util.HashSet;
import java.util.Set;

public class LayerFactory {
    private static final Set<BlockType> backSet = new HashSet<>();
    static void load() {

    }
    static int get(BlockType blockType) {
        return backSet.contains(blockType) ? 1 : 0;
    }
}
