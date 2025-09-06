package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Box2DBody implements Body {
    final com.badlogic.gdx.physics.box2d.Body body;

    Box2DBody(com.badlogic.gdx.physics.box2d.Body body) {
        this.body = body;
    }

    @Override
    public boolean liesOn(Vector2 desired) {
        for (Fixture fixture : body.getFixtureList()) {
            if (fixture.testPoint(desired)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public World getWorld() {
        return new Box2DWorld(body.getWorld());
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public void applyLinearImpulse(Vector2 impulse) {
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public void addPlayerFixture(PlayerFixture playerFixture) {
        final float cornerRadius = 0.08f;
        final float hx = playerFixture.width() / 2 - cornerRadius;
        final float hy = playerFixture.height() / 2 - cornerRadius;
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(hx, hy,
            playerFixture.offset(), 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = playerFixture.density();
        fixtureDef.friction = playerFixture.friction();
        fixtureDef.restitution = playerFixture.restitution();
        body.createFixture(fixtureDef);
        rectangle.dispose();

        CircleShape corner = new CircleShape();
        corner.setRadius(cornerRadius);

        Vector2[] offsets = new Vector2[] {
            new Vector2(-hx, -hy),
            new Vector2( hx, -hy),
            new Vector2(-hx,  hy),
            new Vector2( hx,  hy)
        };

        for (Vector2 off : offsets) {
            corner.setPosition(off);
            fixtureDef.shape = corner;
            body.createFixture(fixtureDef);
        }
        corner.dispose();
        body.setBullet(true); // Costly.
    }
}
