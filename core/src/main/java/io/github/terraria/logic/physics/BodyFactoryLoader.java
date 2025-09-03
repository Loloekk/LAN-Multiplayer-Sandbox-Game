package io.github.terraria.logic.physics;

import io.github.terraria.logic.RecordLoader;

import java.util.Map;
import java.util.stream.Collectors;

public class BodyFactoryLoader {
    private final BodyFactory bodyFactory;
    public BodyFactoryLoader(String jsonName) {
        record BlockFixtureId(int id, boolean isPhysical, BlockFixture fixture) {
            BlockFixtureId {
                if(isPhysical && fixture == null)
                    fixture = new BlockFixture();
            }
        }
        var list = RecordLoader.loadList(jsonName, BlockFixtureId.class);
        Map<Integer, BlockFixture> map = list.stream()
            .filter(b -> b.isPhysical)
            .collect(Collectors.toMap(BlockFixtureId::id, BlockFixtureId::fixture));
        bodyFactory = new BodyFactory(map);
    }
    public BodyFactoryLoader() {
        this("blocks.json");
    }

    public BodyFactory getBodyFactory() {
        return bodyFactory;
    }
}
