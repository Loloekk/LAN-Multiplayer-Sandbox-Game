package io.github.terraria.logic.creatures;

import com.badlogic.gdx.physics.box2d.*;
import io.github.terraria.logic.physics.BodyCategory;

public class CollisionHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        handleContact(contact, 1);
    }

    @Override
    public void endContact(Contact contact) {
        handleContact(contact, -1);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    private void handleContact(Contact contact, int change){
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if(A == null || B == null)return;
        if(A.getUserData() instanceof CollisionSensor sensor){
            sensor.contacts += change;
        }
        if(B.getUserData() instanceof CollisionSensor sensor){
            sensor.contacts += change;
        }
    }
}
