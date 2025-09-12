package io.github.terraria.logic.physics;

public record BlockFixture(float width, float height, float friction, float restitution) {
    public static final float defaultFriction = 0.9f;
    public static final float defaultRestitution = 0.1f;
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
