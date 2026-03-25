package io.github.sandboxGame.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import io.github.sandboxGame.client.view.*;
import io.github.sandboxGame.client.view.ganarators.SceneGenerator;
import io.github.sandboxGame.client.view.textures.texture.TextureBank;
import io.github.sandboxGame.client.view.textures.texture.TextureBankLoader;
import io.github.sandboxGame.client.view.textures.textureQuad.TextureQuadBank;
import io.github.sandboxGame.client.view.textures.textureQuad.TextureQuadBankLoader;
import io.github.sandboxGame.controler.network.Network;
import io.github.sandboxGame.controler.network.PacketJoin;
import io.github.sandboxGame.controler.network.PacketJoinAck;
import io.github.sandboxGame.client.state.ClientGameState;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;

import java.io.IOException;
import java.util.ArrayList;

import static io.github.sandboxGame.common.Config.SCENE_HEIGHT;
import static io.github.sandboxGame.common.Config.SCENE_WIDTH;

public class GameScreen implements Screen {
    private Client client;
    private GameIOHandler IOHandler;
    private final Drop game;
    //    private final Viewport viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT);
    private final ScalingViewport viewport = new ScalingViewport(Scaling.fill, SCENE_WIDTH, SCENE_HEIGHT);

    private String playerName;

    public GameScreen(Drop game, Client client, String playerName, int playerId) {
        this.game = game;
        this.client = client;
        this.playerName = playerName;
        IOHandler = new GameIOHandler(client, viewport);
        IOHandler.setPlayerId(playerId);
        Gdx.app.log("GameScreen", "Joined as id=" + playerId + ", name=" + playerName);

        client.addListener(new Listener() {
            @Override
            public void received(com.esotericsoftware.kryonet.Connection c, Object obj) {
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
        });
        PacketJoin pj = new PacketJoin();
        pj.name = playerName;
        pj.id = playerId;
        client.sendTCP(pj);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);
        IOHandler.handleInput();
        IOHandler.draw(delta);
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        IOHandler.resize(width,height);
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
