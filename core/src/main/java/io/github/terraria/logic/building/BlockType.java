package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import io.github.terraria.logic.block.BlockBehavior;

public interface BlockType {
    FixtureDef getFixtureDef();
    boolean canFall();
    // Na specjalnie kliknięcie:
    BlockBehavior getBlockBehavior();
    String getName();
}
