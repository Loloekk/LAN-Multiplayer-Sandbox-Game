package io.github.terraria.client.view.textures;

import com.badlogic.gdx.graphics.Texture;
import io.github.terraria.loading.RecordLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TextureBankLoader {
    TextureQuad missing;

    public TextureBankLoader(String missing)
    {
        this.missing = new TextureQuad(new Texture(missing),1,1,0.5f,0.5f,true);
    }

    public TextureBank getTextureBank(String jsonName)
    {
        record TextureFixtureId(int id, String name, TextureFixture fixture) {}
        var list = RecordLoader.loadList(jsonName, TextureFixtureId.class);
        Map<Integer, TextureFixture> map = list.stream()
            .collect(Collectors.toMap(TextureFixtureId::id, TextureFixtureId::fixture));
        Map<Integer, TextureQuad> textures = new HashMap<>();
        for(Map.Entry<Integer, TextureFixture> entry: map.entrySet())
        {
            TextureFixture tex = entry.getValue();
            System.out.println(tex.texture());
            textures.put(entry.getKey(), new TextureQuad(new Texture(tex.texture()),
                tex.width(),tex.height(),
                tex.centerX(),tex.centerY(),tex.transparent()));
            System.out.println(
                tex.width()+ " " +tex.height()+ " " +
                tex.centerX()+ " " +tex.centerY());
        }
        return new TextureBank(missing,textures);
    }

}
