package io.github.sandboxGame.logic.physics;

import io.github.sandboxGame.common.Config;

public record BlockFixture(float width, float height, float friction, float restitution) {
    public static final float defaultFriction = Config.BLOCK_DEFAULT_FRICTION;
    public static final float defaultRestitution = Config.BLOCK_DEFAULT_RESTITUTION;
    public BlockFixture {
        if(width == 0f)
            width = 1f;
        if(height == 0f)
            height = 1f;
        if(friction == 0f)
            friction = defaultFriction;
        if(restitution == 0f)
            restitution = defaultRestitution;
    }
    public BlockFixture() { this(0f, 0f, 0f, 0f); }
}
