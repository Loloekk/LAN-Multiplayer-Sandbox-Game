package io.github.terraria.logic.building;

import io.github.terraria.logic.ItemType;

public record BlockType(int id, String name, boolean isPhysical, int layer) implements ItemType {
    public BlockType {
        if(isPhysical)
            layer = 0;
    }
}
