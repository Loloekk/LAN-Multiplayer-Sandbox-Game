package io.github.terraria.logic;

import io.github.terraria.logic.building.BlockFactory;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    BlockFactory blockFactory;

    public ItemRegistry(BlockFactory blockFactory) {
        this.blockFactory = blockFactory;
    }

    public Item create(String name) {
        return blockFactory.create(name);
    }
}
