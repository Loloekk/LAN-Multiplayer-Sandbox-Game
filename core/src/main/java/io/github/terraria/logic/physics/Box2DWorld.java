package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Box2DWorld implements World {
    private final com.badlogic.gdx.physics.box2d.World world;

    Box2DWorld(com.badlogic.gdx.physics.box2d.World world) {
        this.world = world;
    }

    public Box2DWorld(Vector2 gravity, boolean doSleep) {
        world = new com.badlogic.gdx.physics.box2d.World(gravity, doSleep);
    }

    private static final float thickness = 0.4f;
    @Override
    public void createBoundaries(float width, float height, float left, float bottom) {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        // position = (0, 0) by default
        com.badlogic.gdx.physics.box2d.Body walls = world.createBody(bd);

        PolygonShape shape = new PolygonShape();

        // Bottom wall
        shape.setAsBox(width * 0.5f, thickness * 0.5f,
            new Vector2(left + width * 0.5f, bottom - thickness * 0.5f), 0f);
        walls.createFixture(shape, 0f);

        // Top wall
        shape.setAsBox(width * 0.5f, thickness * 0.5f,
            new Vector2(left + width * 0.5f, bottom + height + thickness * 0.5f), 0f);
        walls.createFixture(shape, 0f);

        // Left wall
        shape.setAsBox(thickness * 0.5f, height * 0.5f,
            new Vector2(left - thickness * 0.5f, bottom + height * 0.5f), 0f);
        walls.createFixture(shape, 0f);

        // Right wall
        shape.setAsBox(thickness * 0.5f, height * 0.5f,
            new Vector2(left + width + thickness * 0.5f, bottom + height * 0.5f), 0f);
        walls.createFixture(shape, 0f);

        shape.dispose();
    }

    @Override
    public Box2DBody createBlockBody(Vector2 v, BlockFixture blockFixture) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(v);
        com.badlogic.gdx.physics.box2d.Body box2DBody = world.createBody(bodyDef);

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(blockFixture.width() / 2, blockFixture.height());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.friction = blockFixture.friction();
        fixtureDef.restitution = blockFixture.restitution();
        box2DBody.createFixture(fixtureDef);
        rectangle.dispose();
        return new Box2DBody(box2DBody);
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
