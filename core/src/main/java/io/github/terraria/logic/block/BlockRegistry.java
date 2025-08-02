/*
package io.github.terraria.logic.block;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {
    private static final Map<Integer, Block> blocks = new HashMap<>();

    public static void registerBlock(Block block) {
        blocks.put(block.getId(), block);
    }

    public static Block getBlock(int id) {
        return blocks.get(id);
    }

    public static void loadAll() {
        for (Block block : BlockJsonLoader.loadAllBlocks()) {
            registerBlock(block);
        }
    }
}*/
