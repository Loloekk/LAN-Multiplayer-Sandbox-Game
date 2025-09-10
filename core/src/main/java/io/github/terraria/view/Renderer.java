package io.github.terraria.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Renderer {
    private float scale = 1f;
    private SpriteBatch spriteBatch = new SpriteBatch();
    private TextureBank textureBank;

    public Renderer(TextureBank textureBank){
        this.textureBank = textureBank;
    }

    public void draw(Viewport viewport, Scene scene){
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        for(DrawableRectangle rectangle : scene.objects){
            spriteBatch.draw(textureBank.getTexture(rectangle.textureId),
                rectangle.centerX*scale - scale * rectangle.width/2,
                rectangle.centerY*scale - scale * rectangle.height/2,
                rectangle.width*scale,
                rectangle.height*scale);
        }
        spriteBatch.end();
    }
}
