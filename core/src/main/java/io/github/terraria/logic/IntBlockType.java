package io.github.terraria.logic;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import io.github.terraria.logic.block.BlockBehavior;

public class IntBlockType implements BlockType {
    int id;
    @Override
    public FixtureDef getFixtureDef() {
        return null;
    }

    @Override
    public BlockBehavior getBlockBehavior() {
        return null;
    }
}
