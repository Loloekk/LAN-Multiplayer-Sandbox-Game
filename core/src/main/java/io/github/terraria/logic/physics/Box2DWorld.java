package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

// TODO: Consider removing interface.
public class Box2DWorld implements World {
    private final com.badlogic.gdx.physics.box2d.World world;

    Box2DWorld(com.badlogic.gdx.physics.box2d.World world) {
        this.world = world;
    }

    public Box2DWorld(Vector2 gravity, boolean doSleep) {
        world = new com.badlogic.gdx.physics.box2d.World(gravity, doSleep);
    }

    // Not too nice...
    @Override
    public Box2DBody createStaticBody(Vector2 v, FixtureDef fixtureDef) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(v);
        com.badlogic.gdx.physics.box2d.Body box2DBody = world.createBody(bodyDef);
        box2DBody.createFixture(fixtureDef);
        return new Box2DBody(box2DBody);
    }

    // Not too nice...
    @Override
    public Box2DBody createDynamicBody(Vector2 v, FixtureDef fixtureDef) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(v);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        com.badlogic.gdx.physics.box2d.Body box2DBody = world.createBody(bodyDef);
        box2DBody.createFixture(fixtureDef);
        return new Box2DBody(box2DBody);
    }

    @Override
    public void destroyBody(Body body) {
        if(body instanceof Box2DBody box2DBody)
            world.destroyBody(box2DBody.body);
    }

    @Override
    public void step(float timeStep, int velocityIterations, int positionIterations) {
        world.step(timeStep, velocityIterations, positionIterations);
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    @Override
    public com.badlogic.gdx.physics.box2d.Body createBody(BodyDef def) {
        return world.createBody(def);
    }
}
