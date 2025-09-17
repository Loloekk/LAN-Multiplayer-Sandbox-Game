package io.github.terraria.client.view.textures.texture;

import com.badlogic.gdx.graphics.Texture;

import java.util.Map;

public class TextureBank {
    private Texture missingTexture;
    private Map<Integer, Texture> map;

    public TextureBank(Texture missingTexture, Map<Integer, Texture> map){
        this.missingTexture = missingTexture;
        this.map = map;
    }
    public Texture getTexture(int id){
        if(!map.containsKey(id))
            return missingTexture;
        return map.get(id);
    }
    public void dispose()
    {
        missingTexture.dispose();
        for(Texture tex : map.values())
        {
            tex.dispose();
        }
    }
}
