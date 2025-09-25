package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import io.github.terraria.logic.physics.BodyCategory;

public class CollisionSensor {
    private final Body parent;
    public int contacts = 0;

    public CollisionSensor(Body body, float width, float height, Vector2 offset){
        parent = body;
        PolygonShape box = new PolygonShape();
        box.setAsBox(width/2, height/2, offset, 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BodyCategory.MOB;
        fixtureDef.filter.maskBits = BodyCategory.BLOCK;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        box.dispose();
    }

    public boolean isActive(){
        return contacts > 0;
    }
}
