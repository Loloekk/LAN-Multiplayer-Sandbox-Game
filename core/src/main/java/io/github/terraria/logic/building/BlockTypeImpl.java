package io.github.terraria.logic.building;

import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.block.BlockBehavior;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.World;

public record BlockTypeImpl(int id) implements BlockType {
    @Override
    public boolean isPhysical() { return BodyFactory.isPhysical(this); }

    @Override
    public Body createBody(World world, IntVector2 intVector2) { return BodyFactory.createBody(this, world, intVector2); }

    @Override
    public int getLayer() { return LayerFactory.get(this); }

    @Override
    public BlockBehavior getBlockBehavior() { return null; }

    @Override
    public String getName() { return ""; }
}
