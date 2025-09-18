package io.github.terraria.logic.creatures;

import com.badlogic.gdx.physics.box2d.*;
import io.github.terraria.logic.physics.BodyCategory;

public class CollisionHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        System.out.println("Begin contact");
        handleContact(contact, 1);
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("End contact");
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
        System.out.println("A " + A.getUserData() + " B " + B.getUserData());
        if(A.getUserData() instanceof CollisionSensor sensor){
            System.out.println("other " + B.getFilterData().categoryBits);
            sensor.contacts += change;
        }
        if(B.getUserData() instanceof CollisionSensor sensor){
            System.out.println("other " + A.getFilterData().categoryBits);
            sensor.contacts += change;
        }
    }
}
