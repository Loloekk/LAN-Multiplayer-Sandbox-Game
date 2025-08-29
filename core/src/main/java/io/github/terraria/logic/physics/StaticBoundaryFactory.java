package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class StaticBoundaryFactory {
    private static final float thickness = 0.4f;
    // left, bottom - coordinates of the bottom-left corner of the area to be enclosed
    public void createBoundaries(float width, float height, float left, float bottom, World world) {
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
}
