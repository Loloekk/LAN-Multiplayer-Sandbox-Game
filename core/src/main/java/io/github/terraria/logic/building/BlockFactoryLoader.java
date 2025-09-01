package io.github.terraria.logic.building;

import io.github.terraria.logic.JsonLoader;

import java.util.HashMap;
import java.util.Map;

public class BlockFactoryLoader {
    private final BlockFactory blockFactory;
    public BlockFactoryLoader() {
        final Map<String, BlockType> propertiesMap = new HashMap<>();
        JsonLoader.loadJson("/blocks.json", obj -> {
            int id = obj.get("id").getAsInt();
            String name = obj.get("name").getAsString();
            final boolean isPhysical = obj.has("isPhysical");
            int layer = 1;
            if(isPhysical || obj.get("layer").getAsInt() == 0)
                layer = 0;
            BlockType properties = new BlockType(id, name, isPhysical, layer);
            propertiesMap.put(name, properties);
        });
        blockFactory = new BlockFactory(propertiesMap);
    }

    public BlockFactory getBlockFactory() {
        return blockFactory;
    }
}
