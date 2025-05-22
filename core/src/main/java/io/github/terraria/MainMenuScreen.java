package io.github.terraria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    private static final float BUTTON_WIDTH  = 300;
    private static final float BUTTON_HEIGHT = 100;

    private final Drop game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Rectangle playButton;
    private final Vector3 touchPoint;

    public MainMenuScreen(Drop game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        viewport = new ExtendViewport(1920, 1080, camera);


        float x = (1920 - BUTTON_WIDTH)  / 2f;
        float y = (1080 - BUTTON_HEIGHT) / 2f;
        playButton = new Rectangle(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);

        touchPoint = new Vector3();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 1, 0, 1);


        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPoint);

        game.batch.begin();


        game.batch.draw(
            game.buttonTexture,
            playButton.x, playButton.y,
            playButton.width, playButton.height
        );

        game.batch.end();


        if (Gdx.input.justTouched() && playButton.contains(touchPoint.x, touchPoint.y)) {
            game.setScreen(new GameScreen(game));
            Gdx.app.log("MainMenu", "PLAY BUTTON PRESSED!");
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override public void show()    {}
    @Override public void pause()   {}
    @Override public void resume()  {}
    @Override public void hide()    {}
    @Override
    public void dispose() {
    }
}
