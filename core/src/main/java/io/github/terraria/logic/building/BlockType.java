package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import io.github.terraria.logic.block.BlockBehavior;

public interface BlockType {
    public FixtureDef getFixtureDef();
    public boolean isFallable();
    // Na specjalnie kliknięcie:
    public BlockBehavior getBlockBehavior();
    public String getName();
}
