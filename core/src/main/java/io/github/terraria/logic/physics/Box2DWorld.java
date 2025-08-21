package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Box2DWorld implements World {
    private final com.badlogic.gdx.physics.box2d.World world;

    Box2DWorld(com.badlogic.gdx.physics.box2d.World world) {
        this.world = world;
    }

    public Box2DWorld(Vector2 gravity, boolean doSleep) {
        world = new com.badlogic.gdx.physics.box2d.World(gravity, doSleep);
    }

    @Override
    public Box2DBody createStaticBody(Vector2 v) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(v);
        return new Box2DBody(world.createBody(bodyDef));
    }

    @Override
    public Box2DBody createDynamicBody(Vector2 v) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(v);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return new Box2DBody(world.createBody(bodyDef));
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
}
