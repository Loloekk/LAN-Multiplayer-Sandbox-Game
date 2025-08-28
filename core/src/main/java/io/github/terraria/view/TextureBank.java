package io.github.terraria.view;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class TextureBank {
    private Texture missingTexture;
    private ArrayList<Texture> textures = new ArrayList<>();

    public TextureBank(Texture missingTexture){
        this.missingTexture = missingTexture;
    }

    //returns id of registered texture
    public int registerTexture(Texture texture){
        textures.add(texture);
        return textures.size()-1;
    }

    public Texture getTexture(int id){
        if(id < 0 || id >= textures.size())return missingTexture;
        return textures.get(id);
    }
}
