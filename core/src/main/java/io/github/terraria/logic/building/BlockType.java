package io.github.sandboxGame.logic.building;

import io.github.sandboxGame.logic.equipment.ItemType;

public record BlockType(int id, String name, boolean isPhysical, int layer) implements ItemType {
    public BlockType {
        if(isPhysical)
            layer = 0;
    }
}
