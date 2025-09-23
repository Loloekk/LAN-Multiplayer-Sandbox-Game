package io.github.terraria.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import io.github.terraria.client.GameScreen;

///** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Drop extends Game {
    SpriteBatch batch;
    private Texture image;
    public Texture buttonTexture;


    @Override
    public void create() {
        batch = new SpriteBatch();
        buttonTexture = new Texture(Gdx.files.internal("libgdx.png"));
        image = new Texture("libgdx.png");
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
