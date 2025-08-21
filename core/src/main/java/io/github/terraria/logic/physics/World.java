package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;

public interface World {
    Box2DBody createStaticBody(Vector2 v);

    Box2DBody createDynamicBody(Vector2 v);

    void destroyBody(Body body);

    void step(float timeStep, int velocityIterations, int positionIterations);

    void dispose();
}
