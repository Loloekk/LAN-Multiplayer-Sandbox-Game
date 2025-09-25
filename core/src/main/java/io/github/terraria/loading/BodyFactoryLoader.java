package io.github.terraria.loading;

import io.github.terraria.logic.physics.BlockFixture;
import io.github.terraria.logic.physics.BodyFactory;

import java.util.Map;
import java.util.stream.Collectors;

public class BodyFactoryLoader {
    final Map<Integer, BlockFixture> map;
    private final BodyFactory bodyFactory;
    public BodyFactoryLoader(String jsonName) {
        record BlockFixtureId(int id, boolean isPhysical, BlockFixture fixture) {
            BlockFixtureId {
                if(isPhysical && fixture == null)
                    fixture = new BlockFixture();
            }
        }
        var list = RecordLoader.loadList(jsonName, BlockFixtureId.class);
        map = list.stream()
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
