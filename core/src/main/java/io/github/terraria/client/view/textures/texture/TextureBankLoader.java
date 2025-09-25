package io.github.terraria.client.view.textures.texture;

import com.badlogic.gdx.graphics.Texture;
import io.github.terraria.client.view.textures.TextureFixture;
import io.github.terraria.client.view.textures.textureQuad.TextureQuad;
import io.github.terraria.client.view.textures.textureQuad.TextureQuadBank;
import io.github.terraria.common.Config;
import io.github.terraria.loading.RecordLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TextureBankLoader {
    Texture missing;

    public TextureBankLoader()
    {
        this.missing = new Texture(Config.MISSING_TEXTURE_PATH);
    }

    public TextureBank getTextureBank(String jsonName)
    {
        record TextureFixture(int id, String name, String texture) {}
        var list = RecordLoader.loadList(jsonName, TextureFixture.class);
        Map<Integer, String> idPath = list.stream()
            .collect(Collectors.toMap(TextureFixture::id, TextureFixture::texture));
        Map<String, String> namePath = list.stream()
            .collect(Collectors.toMap(TextureFixture::name, TextureFixture::texture));
        Map<Integer, Texture> idMap = new HashMap<>();
        Map<String, Texture> nameMap = new HashMap<>();
        for(Map.Entry<Integer, String> entry: idPath.entrySet())
        {
            String path = entry.getValue();
            idMap.put(entry.getKey(), new Texture(path));
        }
        for(Map.Entry<String, String> entry: namePath.entrySet())
        {
            String path = entry.getValue();
            nameMap.put(entry.getKey(), new Texture(path));
        }
        return new TextureBank(missing, idMap, nameMap);
    }
}
