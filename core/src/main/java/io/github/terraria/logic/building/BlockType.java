package io.github.terraria.logic.building;

import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.block.BlockBehavior;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.World;

public interface BlockType {
    boolean isPhysical();

    Body createBody(World world, IntVector2 intVector2);

    // isPhysical() true implies layer 0.
    int getLayer();

    // Na specjalnie kliknięcie:
    BlockBehavior getBlockBehavior();

    String getName();
}
