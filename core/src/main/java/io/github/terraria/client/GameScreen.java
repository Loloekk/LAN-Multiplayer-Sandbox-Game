package io.github.terraria.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.common.Network;
import io.github.terraria.view.DrawableRectangle;
import io.github.terraria.view.Renderer;
import io.github.terraria.view.Scene;
import io.github.terraria.view.TextureBank;
import com.badlogic.gdx.graphics.Color;

import java.io.IOException;

public class GameScreen implements Screen {
    private Client client;
    private int playerId;

    private final Drop game;
    private final FitViewport viewport = new FitViewport(8, 5);
    private TextureBank textureBank;
    private Renderer renderer;
    private Scene currentScene;
    private final int PLAYER_TEXTURE;
    private final int STONE_TEXTURE;

    public GameScreen(Drop game) {
        this.game = game;
        client = new Client();
        Network.register(client);
        client.start();
        client.addListener(new Listener() {
            @Override
            public void received(com.esotericsoftware.kryonet.Connection c, Object obj) {
                if (obj instanceof Network.PacketJoinAck) {
                    Network.PacketJoinAck ack = (Network.PacketJoinAck)obj;
                    playerId = ack.playerId;
                    Gdx.app.log("GameScreen", "Joined as id=" + playerId + ", name=" + ack.name);
                } else if (obj instanceof Scene scene) {
                    Gdx.app.log("GameScreen", "Scene update");
                    currentScene = scene;
                }
            }
        });
        try {
            client.connect(5000, "localhost", Network.TCP_PORT, Network.UDP_PORT);
            Network.PacketJoin pj = new Network.PacketJoin();
            pj.name = "Gamer";
            client.sendTCP(pj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textureBank = new TextureBank(new Texture("missing.png"));
        PLAYER_TEXTURE = textureBank.registerTexture(new Texture("stefan.png"));
        STONE_TEXTURE = textureBank.registerTexture(new Texture("stone.png"));
        renderer = new Renderer(textureBank);
        currentScene = new Scene();
    }

    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(Color.CYAN);
        renderer.draw(viewport, currentScene);
    }

    private void handleInput() {
        float mx=0, my=0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  mx=-1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) mx=1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  my=-1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    my=1;
        if (mx!=0 || my!=0) {
            Network.PacketInput pi = new Network.PacketInput();
            pi.playerId = playerId;
            pi.moveX = mx;
            pi.moveY = my;
            client.sendUDP(pi);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override public void show()    {}
    @Override public void pause()   {}
    @Override public void resume()  {}
    @Override public void hide()    {}
    @Override
    public void dispose() {
    }
//    @Override public void dispose() {
//        renderer.dispose();
//        client.stop();
//    }
}
