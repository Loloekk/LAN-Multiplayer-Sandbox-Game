package io.github.sandboxGame.client.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SceneRenderer {
    private SpriteBatch spriteBatch = new SpriteBatch();

    public SceneRenderer(){}

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
