package io.github.terraria.client.view.textures;

import java.util.Map;

public class TextureBank {
    private TextureQuad missingTexture;
    private Map<Integer, TextureQuad> map;

    public TextureBank(TextureQuad missingTexture, Map<Integer, TextureQuad> map){
        this.missingTexture = missingTexture;
        this.map = map;
    }
    public TextureQuad getTexture(int id){
        if(!map.containsKey(id))
            return missingTexture;
        return map.get(id);
    }
}
