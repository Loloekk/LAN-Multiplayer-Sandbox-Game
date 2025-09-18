package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.terraria.logic.physics.BodyCategory;

public class BasicCreatureBody implements CreatureBody{
    private final com.badlogic.gdx.physics.box2d.Body body;

    public BasicCreatureBody(World world, Vector2 position, float width, float height, float density, float friction, float restitution){
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width/2, height/2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        rectangle.dispose();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = BodyCategory.MOB;
        fixtureDef.filter.maskBits = (BodyCategory.BLOCK);
        body.createFixture(fixtureDef);

        body.setBullet(true);
        body.setFixedRotation(true);
    }
    @Override
    public void applyLinearImpulse(Vector2 impulse) {
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
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
    public void destroy() {
        body.getWorld().destroyBody(body);
    }

    @Override
    public boolean isGrounded() {
        return false;
    }

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
