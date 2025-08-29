package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public interface World {
    Box2DBody createStaticBody(Vector2 v, FixtureDef fixtureDef);

    Box2DBody createDynamicBody(Vector2 v, FixtureDef fixtureDef);

    void destroyBody(Body body);

    void step(float timeStep, int velocityIterations, int positionIterations);

    void dispose();

    com.badlogic.gdx.physics.box2d.Body createBody(BodyDef def);
}
