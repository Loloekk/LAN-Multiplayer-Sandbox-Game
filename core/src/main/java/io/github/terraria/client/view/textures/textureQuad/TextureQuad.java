package io.github.terraria.client.view.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureQuad {
    private Texture texture;
    private float width;
    private float height;
    private float centerX;
    private float centerY;
    private boolean transparent;

    public TextureQuad(Texture texture, float width, float height, float centerX, float centerY, boolean transparent) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;
        this.transparent = transparent;
    }
    public void draw(SpriteBatch batch, float X, float Y)
    {
         batch.draw(texture,X + centerX - width/2, Y + centerY - height/2, width,height);
    }
    public boolean isTransparent()
    {
        return transparent;
    }
    public void dispose()
    {
        texture.dispose();
    }
}
