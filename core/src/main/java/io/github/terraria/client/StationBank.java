package io.github.terraria.client;

import com.badlogic.gdx.graphics.Texture;
import io.github.terraria.client.view.textures.TextureFixture;
import io.github.terraria.client.view.textures.textureQuad.TextureQuad;
import io.github.terraria.client.view.textures.textureQuad.TextureQuadBank;
import io.github.terraria.common.StationType;
import io.github.terraria.loading.RecordLoader;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class StationBank {
    Map<Integer, StationType> stations;
    public StationBank(String jsonName)
    {
        record BlockFixture(int id, StationType station) {}
        var list = RecordLoader.loadList(jsonName, BlockFixture.class);
        stations = list.stream()
            .filter(b -> b.station() != null)
            .collect(Collectors.toMap(BlockFixture::id, BlockFixture::station));
    }
    public boolean isStation(Integer blockId)
    {
        return stations.containsKey(blockId);
    }
    public StationType getStation(Integer blockId)
    {
        if(stations.containsKey(blockId))
            return stations.get(blockId);
        return null;
    }
}
