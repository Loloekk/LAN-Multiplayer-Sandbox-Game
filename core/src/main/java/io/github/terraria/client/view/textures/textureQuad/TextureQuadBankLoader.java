package io.github.sandboxGame.client.view.textures.textureQuad;

import com.badlogic.gdx.graphics.Texture;
import io.github.sandboxGame.client.view.textures.TextureFixture;
import io.github.sandboxGame.common.Config;
import io.github.sandboxGame.loading.RecordLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TextureQuadBankLoader {
    TextureQuad missing;

    public TextureQuadBankLoader()
    {
        String missing = Config.MISSING_TEXTURE_PATH;
        this.missing = new TextureQuad(new Texture(missing),1,1,0.5f,0.5f,true);
    }

    public TextureQuadBank getTextureQuadBank(String jsonName)
    {
        record TextureFixtureId(int id, String name, TextureFixture fixture) {}
        var list = RecordLoader.loadList(jsonName, TextureFixtureId.class);
        Map<Integer, TextureFixture> map = list.stream()
            .collect(Collectors.toMap(TextureFixtureId::id, TextureFixtureId::fixture));
        Map<Integer, TextureQuad> textures = new HashMap<>();
        for(Map.Entry<Integer, TextureFixture> entry: map.entrySet())
        {
            TextureFixture tex = entry.getValue();
            textures.put(entry.getKey(), new TextureQuad(new Texture(tex.texture()),
                tex.width(),tex.height(),
                tex.centerX(),tex.centerY(),tex.transparent()));
        }
        return new TextureQuadBank(missing,textures);
    }

}
