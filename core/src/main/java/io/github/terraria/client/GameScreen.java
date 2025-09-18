package io.github.terraria.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.client.view.*;
import io.github.terraria.client.view.ganarators.SceneGenerator;
import io.github.terraria.client.view.textures.texture.TextureBank;
import io.github.terraria.client.view.textures.texture.TextureBankLoader;
import io.github.terraria.client.view.textures.textureQuad.TextureQuadBank;
import io.github.terraria.client.view.textures.textureQuad.TextureQuadBankLoader;
import io.github.terraria.controler.network.Network;
import io.github.terraria.controler.network.PacketJoin;
import io.github.terraria.controler.network.PacketJoinAck;
import io.github.terraria.client.state.ClientGameState;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;

import java.io.IOException;
import java.util.ArrayList;

import static io.github.terraria.common.Config.SCENE_HEIGHT;
import static io.github.terraria.common.Config.SCENE_WIDTH;

public class GameScreen implements Screen {
    private Client client;
    private GameIOHandler IOHandler;
    private final Drop game;
    //    private final Viewport viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT);
    private final ScalingViewport viewport = new ScalingViewport(Scaling.fill, SCENE_WIDTH, SCENE_HEIGHT);
    private boolean connectionAccept = false;

    public GameScreen(Drop game) {
        this.game = game;

        client = new Client();
        Network.register(client);
        client.start();
        IOHandler = new GameIOHandler(client, viewport);

        client.addListener(new Listener() {
            @Override
            public void received(com.esotericsoftware.kryonet.Connection c, Object obj) {
                if (obj instanceof PacketJoinAck ack ) {
                    IOHandler.setPlayerId(ack.playerId);
                    connectionAccept = true;
                    Gdx.app.log("GameScreen", "Joined as id=" + ack.playerId + ", name=" + ack.name);

                }
                else if(connectionAccept) {
                    if (obj instanceof ArrayList list)
                    {
                        for (Object objj : list) {
                            IOHandler.actualize(objj);
                        }
                    }
                    else
                    {
                        IOHandler.actualize(obj);
                    }
                }
            }
        });
        try {
            client.connect(5000, "localhost", Network.TCP_PORT, Network.UDP_PORT);
            PacketJoin pj = new PacketJoin();
            pj.name = "Gamer";
            client.sendTCP(pj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);
        if(connectionAccept) {
            IOHandler.handleInput();
            IOHandler.draw(delta);
        }
    }



    @Override
    public void resize(int width, int height) {
        if(connectionAccept)
            IOHandler.resize(width,height);
        viewport.update(width, height, true);
    }
    @Override public void show()    {}
    @Override public void pause()   {}
    @Override public void resume()  {}
    @Override public void hide()    {}
    @Override
    public void dispose() {
        IOHandler.dispose();
    }

}
