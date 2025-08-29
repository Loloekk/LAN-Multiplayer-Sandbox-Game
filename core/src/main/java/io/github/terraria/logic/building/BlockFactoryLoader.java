package io.github.terraria.logic.building;

import io.github.terraria.logic.JsonLoader;

import java.util.HashMap;
import java.util.Map;

public class BlockFactoryLoader {
    private final BlockFactory blockFactory;
    public BlockFactoryLoader() {
        final Map<Integer, BlockProperties> propertiesMap = new HashMap<>();
        JsonLoader.loadJson("/blocks.json", obj -> {
            final boolean isPhysical = obj.has("isPhysical");
            int layer = 1;
            if(isPhysical || obj.get("layer").getAsInt() == 0)
                layer = 0;
            BlockProperties properties = new BlockProperties(isPhysical, layer,
                obj.get("name").getAsString());
            propertiesMap.put(obj.get("id").getAsInt(), properties);
        });
        blockFactory = new BlockFactory(propertiesMap);
    }

    public BlockFactory getBlockFactory() {
        return blockFactory;
    }
}
