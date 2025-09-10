package io.github.terraria.client.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.terraria.client.view.Textures.TextureBank;

public class Renderer {
    private SpriteBatch spriteBatch = new SpriteBatch();

    public Renderer(){}

    public void draw(Viewport viewport, Scene scene){
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        for(DrawableRectangle rectangle : scene.objects){
            rectangle.draw(spriteBatch);
        }
        spriteBatch.end();
    }
}
