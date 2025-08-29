package io.github.terraria.logic.physics;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import io.github.terraria.logic.JsonLoader;
import java.util.HashMap;
import java.util.Map;

public class BodyFactoryLoader {
    private final BodyFactory bodyFactory;
    public BodyFactoryLoader() {
        final Map<Integer, FixtureDef> fixtureDefMap = new HashMap<>();
        // TODO: FixtureDef must be offset by half of the diagonal in case of a rectangle.
        // Necessary for the bottom left to be at local coordinates (0, 0), i.e the ones specifies for the Body.
        JsonLoader.loadJson("/blocks.json", obj -> {
            final boolean isPhysical = obj.has("isPhysical");
        });
        bodyFactory = new BodyFactory(fixtureDefMap);
    }

    public BodyFactory getBodyFactory() {
        return bodyFactory;
    }
}
