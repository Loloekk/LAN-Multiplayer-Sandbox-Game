package io.github.terraria;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    static final float BUTTON_WIDTH = 100;
    static final float BUTTON_HEIGHT = 200;
    Drop game;
    OrthographicCamera camera;
    Viewport viewport;
    Rectangle playButton;
    Rectangle settingsButton;
    Rectangle exitButton;
    Vector3 touchPoint;
    public MainMenuScreen(Drop game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        viewport = new ExtendViewport(1920, 1080, camera);
        playButton = new Rectangle(960 - BUTTON_WIDTH/2, 500 - BUTTON_HEIGHT/2, BUTTON_WIDTH, BUTTON_HEIGHT);
        touchPoint = new Vector3();
    }
    @Override
    public void render(float delta) {
       
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) { viewport.update(width, height); }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }
}
