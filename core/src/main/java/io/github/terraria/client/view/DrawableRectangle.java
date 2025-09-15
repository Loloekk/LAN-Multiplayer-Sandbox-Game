package io.github.terraria.client.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.terraria.client.view.textures.TextureQuad;

public class DrawableRectangle {
    public float X;
    public float Y;
    public TextureQuad texture;
    public DrawableRectangle(float X, float Y, TextureQuad texture){
        this.X = X;
        this.Y = Y;
        this.texture = texture;
    }
    public void draw(SpriteBatch batch)
    {
        texture.draw(batch, X,Y);
    }

}
