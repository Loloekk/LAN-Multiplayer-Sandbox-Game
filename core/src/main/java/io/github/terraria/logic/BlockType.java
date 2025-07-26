package io.github.terraria.logic;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import io.github.terraria.logic.block.BlockBehavior;

public interface BlockType {
    public FixtureDef getFixtureDef();
    public BlockBehavior getBlockBehavior();
}
