package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;

// TODO: Clean-up interface.
public interface World {
    // left, bottom - coordinates of the bottom-left corner of the area to be enclosed
    void createBoundaries(float width, float height, float left, float bottom);

    Body createStaticBody(Vector2 v, FixtureDef fixtureDef);

    Body createDynamicBody(Vector2 v, FixtureDef fixtureDef);

    void destroyBody(Body body);

    void step(float timeStep, int velocityIterations, int positionIterations);

    void dispose();
}
