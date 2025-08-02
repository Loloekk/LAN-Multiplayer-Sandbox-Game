package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import io.github.terraria.logic.block.BlockBehavior;

public class IntBlockType implements BlockType {
    private final int id;
    public IntBlockType(int id) {
        this.id = id;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return null;
    }

    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public BlockBehavior getBlockBehavior() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
