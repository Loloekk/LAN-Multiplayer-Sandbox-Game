package io.github.terraria.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.client.view.*;
import io.github.terraria.client.state.ClientInteractions;
import io.github.terraria.controler.network.Network;
import io.github.terraria.controler.network.PacketJoin;
import io.github.terraria.controler.network.PacketJoinAck;
import io.github.terraria.client.state.data.ClientGameState;
import com.badlogic.gdx.graphics.Color;

import java.io.IOException;
import java.util.ArrayList;

public class GameScreen implements Screen {
    private Client client;
    private int playerId;
    private ClientGameState gameState;
    private ClientInteractions playerInteractions;
    private final Drop game;
//    private final FitViewport viewport = new FitViewport(8, 5);

//    private final ScreenViewport viewport = new ScreenViewport();
//    private final StretchViewport viewport = new StretchViewport(8,5);
    private final ScalingViewport viewport = new ScalingViewport(Scaling.fill, 30, 20);
    private SceneGenerator generator;
    private Renderer renderer;

    public GameScreen(Drop game) {
        this.game = game;
        generator = new SceneGenerator();
        client = new Client();
        Network.register(client);
        client.start();
        client.addListener(new Listener() {
            @Override
            public void received(com.esotericsoftware.kryonet.Connection c, Object obj) {
                if (obj instanceof PacketJoinAck ack ) {
                    playerId = ack.playerId;
                    gameState = new ClientGameState(client, playerId);
                    playerInteractions = new ClientInteractions(client, gameState, playerId,viewport);
                    Gdx.app.log("GameScreen", "Joined as id=" + playerId + ", name=" + ack.name);

                }
                else if (obj instanceof ArrayList list)
                {
                    if(gameState == null) {
                        Gdx.app.log("communication error","XD");
                    }
                    for(Object objj : list)
                    {
//                        System.out.println("mamy liste" + objj);
                        gameState.actualize(objj);

                    }
                }
                else {
                    Gdx.app.log("GameScreen", "Scene update");
                    if(gameState == null) {
                        Gdx.app.log("communication error","XD");
                    }
                    gameState.actualize(obj);
                    System.out.println(obj);
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
//        textureBank = new TextureBank(new Texture("missing.png"));
//        PLAYER_TEXTURE = textureBank.registerTexture(new Texture("stefan.png"));
//        STONE_TEXTURE = textureBank.registerTexture(new Texture("stone.png"));
        renderer = new Renderer();
    }
    int licz = 0;
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);
        if(playerInteractions == null)
            return;
        playerInteractions.handleInput();
        renderer.draw(viewport, generator.generate(gameState));
        licz ++;
        if(licz%120 == 0)
        {
            gameState.throwTrash();
            licz = 0;
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
