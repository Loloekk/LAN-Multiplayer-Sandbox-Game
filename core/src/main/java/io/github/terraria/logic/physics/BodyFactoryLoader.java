package io.github.terraria.logic.physics;

import com.google.gson.JsonObject;
import io.github.terraria.logic.JsonLoader;
import java.util.HashMap;
import java.util.Map;

public class BodyFactoryLoader {
    private static final float defaultFriction = 0.4f;
    private static final float defaultRestitution = 0.6f;
    private static float getOrDefault(JsonObject obj, String key, float defaultValue) {
        return obj.has(key) ? obj.get(key).getAsFloat() : defaultValue;
    }

    private final BodyFactory bodyFactory;
    public BodyFactoryLoader() {
        final Map<Integer, BlockFixture> fixtureMap = new HashMap<>();
        JsonLoader.loadJson("/blocks.json", obj -> {
            // Clean up explicit constants?
            if(obj.has("isPhysical")) {
                int id = obj.get("id").getAsInt();
                float width = getOrDefault(obj, "width", 1f);
                float height = getOrDefault(obj, "height", 1f);
                float friction = getOrDefault(obj, "friction", defaultFriction);
                float restitution = getOrDefault(obj, "restitution", defaultRestitution);
                fixtureMap.put(id, new BlockFixture(width, height, friction, restitution));
            }
        });
        bodyFactory = new BodyFactory(fixtureMap);
    }

    public BodyFactory getBodyFactory() {
        return bodyFactory;
    }
}
