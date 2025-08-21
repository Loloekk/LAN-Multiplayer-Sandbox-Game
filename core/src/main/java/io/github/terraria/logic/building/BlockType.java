package io.github.terraria.logic.building;

import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.block.BlockBehavior;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.World;

public class BlockType {
    private final int id;
    // Dostępne tylko dla craftingu i renderowania.
    BlockType(int id) {
        this.id = id;
    }
    public boolean isPhysical() { return BodyFactory.isPhysical(this); }
    public Body createBody(World world, IntVector2 intVector2) { return BodyFactory.createBody(this, world, intVector2); }
    // isPhysical() true implies layer 0.
    public int getLayer() { return LayerFactory.get(this); }
    // To na razie można odpuścić sobie.
    public boolean canFall() { return false; }

    // Na specjalnie kliknięcie:
    public BlockBehavior getBlockBehavior() { return null; }
    public String getName() { return ""; }
}
