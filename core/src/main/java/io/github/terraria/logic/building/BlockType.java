package io.github.terraria.logic.building;

import io.github.terraria.logic.block.BlockBehavior;

public interface BlockType {
    int id();

    boolean isPhysical();

    // isPhysical() true implies layer 0.
    int getLayer();

    // Na specjalnie kliknięcie:
    BlockBehavior getBlockBehavior();

    String getName();
}
