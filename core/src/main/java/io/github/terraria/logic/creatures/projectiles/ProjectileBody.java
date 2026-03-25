package io.github.sandboxGame.logic.creatures.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.sandboxGame.logic.physics.BodyCategory;

import java.util.List;

public class ProjectileBody {
    private final com.badlogic.gdx.physics.box2d.Body body;
    private final com.badlogic.gdx.physics.box2d.Fixture fixture;
    private final List<Body> bodiesToDestroy;

    public ProjectileBody(World world, List<Body> bodiesToDestroy, Vector2 position, Vector2 velocity, float radius, float gravity){
        this.bodiesToDestroy = bodiesToDestroy;
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        def.linearVelocity.set(velocity);
        this.body = world.createBody(def);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        //TODO: do something about constants
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = BodyCategory.PROJECTILE;
        fixtureDef.filter.maskBits = (BodyCategory.BLOCK | BodyCategory.MOB | BodyCategory.PLAYER);
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);

        body.setGravityScale(gravity);
        circle.dispose();
    }

    public void bindProjectile(Projectile projectile){
        fixture.setUserData(projectile);
    }
    public void destroy(){
        bodiesToDestroy.add(body);
    }
    public Vector2 getPosition(){
        return body.getPosition();
    }
    public Vector2 getVelocity() {return body.getLinearVelocity();}
}
