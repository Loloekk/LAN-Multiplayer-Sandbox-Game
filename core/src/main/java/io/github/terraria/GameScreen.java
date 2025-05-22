package io.github.terraria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private static final float RECT_SIZE = 50;
    private static final float MOVE_SPEED = 300;

    private final Drop game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final ShapeRenderer shapeRenderer;
    private final Rectangle player;

    public GameScreen(Drop game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        viewport = new ExtendViewport(1920, 1080, camera);

        shapeRenderer = new ShapeRenderer();
        float startX = (1920 - RECT_SIZE) / 2f;
        float startY = (1080 - RECT_SIZE) / 2f;
        player = new Rectangle(startX, startY, RECT_SIZE, RECT_SIZE);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);


        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  player.x -= MOVE_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.x += MOVE_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    player.y += MOVE_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  player.y -= MOVE_SPEED * delta;


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(player.x, player.y, player.width, player.height);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override public void show()   {}
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
