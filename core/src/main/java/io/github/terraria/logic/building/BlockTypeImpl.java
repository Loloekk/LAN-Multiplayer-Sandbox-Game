package io.github.terraria.logic.building;

import io.github.terraria.logic.block.BlockBehavior;

// TODO: Consider removing interface.
public record BlockTypeImpl(int id, BlockProperties properties) implements BlockType {
    @Override
    public int id() { return id; }

    @Override
    public boolean isPhysical() { return properties.isPhysical(); }

    @Override
    public int getLayer() { return properties.layer(); }

    @Override
    public BlockBehavior getBlockBehavior() { return null; }

    @Override
    public String getName() { return properties.name(); }
}
