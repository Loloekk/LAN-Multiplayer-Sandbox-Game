package io.github.sandboxGame.loading;

import io.github.sandboxGame.logic.building.BlockFactory;
import io.github.sandboxGame.logic.building.BlockType;

public class BlockFactoryLoader {
    private final BlockFactory blockFactory;
    public BlockFactoryLoader(String jsonName) {
        var list = RecordLoader.loadList(jsonName, BlockType.class);
        blockFactory = new BlockFactory(list);
    }
    public BlockFactoryLoader() {
        this("blocks.json");
    }

    public BlockFactory getBlockFactory() {
        return blockFactory;
    }
}
