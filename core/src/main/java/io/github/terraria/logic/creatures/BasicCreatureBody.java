package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.terraria.logic.physics.BodyCategory;

import java.util.List;

public class BasicCreatureBody implements CreatureBody{
    private final com.badlogic.gdx.physics.box2d.Body body;
    private final com.badlogic.gdx.physics.box2d.Fixture bodyFixture;
    private final CollisionSensor feet;
    private final CollisionSensor sensorLeft;
    private final CollisionSensor sensorRight;
    private final List<Body> bodiesToDestroy;

    public BasicCreatureBody(World world, List<Body> bodiesToDestroy, Vector2 position, float width, float height, float density, float friction, float restitution){
        this.bodiesToDestroy = bodiesToDestroy;
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width/2, height/2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = BodyCategory.MOB;
        fixtureDef.filter.maskBits = (BodyCategory.BLOCK | BodyCategory.PROJECTILE);
        bodyFixture = body.createFixture(fixtureDef);

        body.setBullet(true);
        body.setFixedRotation(true);
        feet = new CollisionSensor(body, 0.9f*width, 0.1f, new Vector2(0, - (height / 2 + 0.05f)));
        sensorLeft = new CollisionSensor(body, 1.0f, 0.9f * height, new Vector2(-0.5f - 0.5f * width, 0));
        sensorRight = new CollisionSensor(body, 1.0f, 0.9f * height, new Vector2(0.5f + 0.5f * width, 0));
        rectangle.dispose();
    }

    @Override
    public void bindCreature(Creature creature){
        bodyFixture.setUserData(creature);
    }
    @Override
    public void applyLinearImpulse(Vector2 impulse) {
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

    @Override
    public void applyForce(Vector2 force) {
        body.applyForceToCenter(force, true);
    }

    @Override
    public Vector2 getPosition(){
        return  body.getPosition();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public void setGravity(float scale) {
        body.setGravityScale(scale);
    }

    @Override
    public void destroy() {
        bodiesToDestroy.add(body);
    }

    @Override
    public boolean isGrounded() {
        return feet.isActive();
    }

    @Override
    public boolean obstacleLeft(){return sensorLeft.isActive();}
    @Override
    public boolean obstacleRight(){return sensorRight.isActive();}

    @Override
    public boolean liesOn(Vector2 desired){
        for (Fixture fixture : body.getFixtureList()) {
            if (fixture.testPoint(desired)) {
                return true;
            }
        }
        return false;
    }
}
