package io.github.terraria.client.view.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureQuad {
    private Texture texture;
    private float width;
    private float height;
    private float centerX;
    private float centerY;

    public TextureQuad(Texture texture, float width, float height, float centerX, float centerY) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;
    }
    public void draw(SpriteBatch batch, float X, float Y)
    {
         batch.draw(texture,X + centerX - width/2, Y + centerY - height/2, width,height);
    }
    public String info()
    {
        return texture + " "+ width + " "+ height+ " "+ centerX+ " "+ centerY;
    }
}
