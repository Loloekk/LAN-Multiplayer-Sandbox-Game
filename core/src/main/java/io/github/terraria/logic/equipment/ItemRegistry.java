package io.github.terraria.logic.equipment;

import io.github.terraria.logic.building.BlockFactory;

public class ItemRegistry {
    BlockFactory blockFactory;

    public ItemRegistry(BlockFactory blockFactory) {
        this.blockFactory = blockFactory;
    }

    public Item create(String name) {
        return blockFactory.create(name);
    }
}
