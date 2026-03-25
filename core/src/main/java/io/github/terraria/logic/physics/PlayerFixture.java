package io.github.sandboxGame.logic.physics;

import com.badlogic.gdx.math.Vector2;

// A player may consist of multiple fixtures.
public record PlayerFixture(float width, float height,
                            float density, float friction, float restitution,
                            Vector2 offset) {}
