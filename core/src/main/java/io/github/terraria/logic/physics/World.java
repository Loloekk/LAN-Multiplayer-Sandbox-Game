package io.github.sandboxGame.logic.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.creatures.CreatureBody;

public interface World {
    // left, bottom - coordinates of the bottom-left corner of the area to be enclosed
    void createBoundaries(float width, float height, float left, float bottom);

    Body createBlockBody(Vector2 v, BlockFixture blockFixture);

    Body createDynamicBody(Vector2 v);
    CreatureBody createCreatureBody(Vector2 v, float width, float height, float density, float friction, float restitution);

    void step(float timeStep, int velocityIterations, int positionIterations);

    void dispose();
}
