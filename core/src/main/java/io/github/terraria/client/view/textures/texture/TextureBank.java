package io.github.terraria.client.view.textures.texture;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TextureBank {
    private Texture missingTexture;
    private Map<Integer, Texture> idMap;
    private Map<String, Texture> nameMap;

    public TextureBank(Texture missingTexture, Map<Integer, Texture> idMap, Map<String, Texture> nameMap){
        this.missingTexture = missingTexture;
        this.idMap = idMap;
        this.nameMap = nameMap;
    }
    public Texture getTexture(int id){
        if(!idMap.containsKey(id))
            return missingTexture;
        return idMap.get(id);
    }
    public Texture getTexture(String name){
        if(!nameMap.containsKey(name))
            return missingTexture;
        return nameMap.get(name);
    }
    public void dispose()
    {
        missingTexture.dispose();
        Set<Texture> set = new HashSet<>();
        set.addAll(idMap.values());
        set.addAll(nameMap.values());
        for(Texture tex : set)
        {
            tex.dispose();
        }
    }
}
