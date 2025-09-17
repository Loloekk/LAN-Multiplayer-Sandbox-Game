package io.github.terraria.client.view.textures.texture;

import com.badlogic.gdx.graphics.Texture;
import io.github.terraria.client.view.textures.TextureFixture;
import io.github.terraria.client.view.textures.textureQuad.TextureQuad;
import io.github.terraria.client.view.textures.textureQuad.TextureQuadBank;
import io.github.terraria.loading.RecordLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TextureBankLoader {
    Texture missing;

    public TextureBankLoader(String missing)
    {
        this.missing = new Texture(missing);
    }

    public TextureBank getTextureBank(String jsonName)
    {
        record TextureFixture(int id, String name, String texture) {}
        var list = RecordLoader.loadList(jsonName, TextureFixture.class);
        Map<Integer, String> map = list.stream()
            .collect(Collectors.toMap(TextureFixture::id, TextureFixture::texture));
        Map<Integer, Texture> textures = new HashMap<>();
        for(Map.Entry<Integer, String> entry: map.entrySet())
        {
            String path = entry.getValue();
            textures.put(entry.getKey(), new Texture(path));
        }
        return new TextureBank(missing,textures);
    }
}
