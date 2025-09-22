package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import io.github.terraria.logic.creatures.BasicCreatureBody;
import io.github.terraria.logic.creatures.CollisionHandler;
import io.github.terraria.logic.creatures.CreatureBody;
import io.github.terraria.logic.players.PlayerActivator;

public class Box2DWorld implements World {
    private final com.badlogic.gdx.physics.box2d.World world;

    public Box2DWorld(com.badlogic.gdx.physics.box2d.World world) {
        this.world = world;
    }

//    public Box2DWorld(Vector2 gravity, boolean doSleep) {
//        world = new com.badlogic.gdx.physics.box2d.World(gravity, doSleep);
//        world.setContactListener(new CollisionHandler());
//    }

    private static final float thickness = 0.4f;
    @Override
    public void createBoundaries(float width, float height, float left, float bottom) {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        // position = (0, 0) by default
        com.badlogic.gdx.physics.box2d.Body walls = world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();
        fixture.density = 0.0f;
        fixture.filter.categoryBits = BodyCategory.BLOCK;
        fixture.filter.categoryBits = (BodyCategory.MOB | BodyCategory.PROJECTILE | BodyCategory.PLAYER);
        fixture.shape = shape;

        // Bottom wall
        shape.setAsBox(width * 0.5f, thickness * 0.5f,
            new Vector2(left + width * 0.5f, bottom - thickness * 0.5f), 0f);
        walls.createFixture(fixture);

        // Top wall
        shape.setAsBox(width * 0.5f, thickness * 0.5f,
            new Vector2(left + width * 0.5f,
                bottom + height + thickness * 0.5f + PlayerActivator.MAX_PLAYERS_RADIUS * 2f), 0f);
        walls.createFixture(fixture);

        // Left wall
        shape.setAsBox(thickness * 0.5f, height * 0.5f + PlayerActivator.MAX_PLAYERS_RADIUS,
            new Vector2(left - thickness * 0.5f,
                bottom + height * 0.5f + PlayerActivator.MAX_PLAYERS_RADIUS), 0f);
        walls.createFixture(fixture);

        // Right wall
        shape.setAsBox(thickness * 0.5f, height * 0.5f + PlayerActivator.MAX_PLAYERS_RADIUS,
            new Vector2(left + width + thickness * 0.5f,
                bottom + height * 0.5f + PlayerActivator.MAX_PLAYERS_RADIUS), 0f);
        walls.createFixture(fixture);

        shape.dispose();
    }

    @Override
    public Box2DBody createBlockBody(Vector2 v, BlockFixture blockFixture) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(v);
        com.badlogic.gdx.physics.box2d.Body box2DBody = world.createBody(bodyDef);

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(blockFixture.width() / 2, blockFixture.height() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.friction = blockFixture.friction();
        fixtureDef.restitution = blockFixture.restitution();
        fixtureDef.filter.categoryBits = BodyCategory.BLOCK;
        fixtureDef.filter.maskBits = (BodyCategory.MOB | BodyCategory.PLAYER | BodyCategory.PROJECTILE);
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
    public CreatureBody createCreatureBody(Vector2 v, float width, float height, float density, float friction, float restitution){
        return null;
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
