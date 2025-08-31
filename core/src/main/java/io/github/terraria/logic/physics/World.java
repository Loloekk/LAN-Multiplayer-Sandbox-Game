package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;

public interface World {
    // left, bottom - coordinates of the bottom-left corner of the area to be enclosed
    void createBoundaries(float width, float height, float left, float bottom);

    Body createBlockBody(Vector2 v, BlockFixture blockFixture);

    Body createDynamicBody(Vector2 v);

    void destroyBody(Body body);

    void step(float timeStep, int velocityIterations, int positionIterations);

    void dispose();
}
