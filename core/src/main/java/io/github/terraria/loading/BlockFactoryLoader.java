package io.github.terraria.loading;

import io.github.terraria.logic.building.BlockFactory;
import io.github.terraria.logic.building.BlockType;

import java.util.Map;
import java.util.stream.Collectors;

public class BlockFactoryLoader {
    private final BlockFactory blockFactory;
    public BlockFactoryLoader(String jsonName) {
        var list = RecordLoader.loadList(jsonName, BlockType.class);
        Map<String, BlockType> propertiesMap = list.stream()
            .collect(Collectors.toMap(BlockType::name, b -> b));
        blockFactory = new BlockFactory(propertiesMap);
    }
    public BlockFactoryLoader() {
        this("blocks.json");
    }

    public BlockFactory getBlockFactory() {
        return blockFactory;
    }
}
