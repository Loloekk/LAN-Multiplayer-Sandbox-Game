package io.github.terraria.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
    private int playerId;
    private ClientGameState gameState;
    private PlayerInteractions playerInteractions;

    private boolean inventoryVisible = false;
    private final Drop game;
    //    private final Viewport viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT);
    private final ScalingViewport viewport = new ScalingViewport(Scaling.fill, SCENE_WIDTH, SCENE_HEIGHT);
    private SceneGenerator generator;
    private SceneRenderer renderer;
    private EquipmentStage equipmentStage;
    private boolean connectionAccept = false;

    TextureBank itemsTexture;
    TextureQuadBank blocksTexture;
    TextureQuadBank playerTexture;

    public GameScreen(Drop game) {
        this.game = game;


        TextureBankLoader loader = new TextureBankLoader("missing.png");
        itemsTexture = loader.getTextureBank("textureItems.json");
        TextureQuadBankLoader loaderQuad = new TextureQuadBankLoader("missing.png");
        blocksTexture = loaderQuad.getTextureQuadBank("textureBlocks.json");
        playerTexture = loaderQuad.getTextureQuadBank("texturePlayer.json");

        generator = new SceneGenerator(blocksTexture, playerTexture, itemsTexture);
        client = new Client();
        Network.register(client);
        client.start();
        gameState = new ClientGameState(client);
        playerInteractions = new PlayerInteractions(client, gameState, viewport);
        equipmentStage = new EquipmentStage(client,gameState.getMainPlayerState(), itemsTexture);
        client.addListener(new Listener() {
            @Override
            public void received(com.esotericsoftware.kryonet.Connection c, Object obj) {
                if (obj instanceof PacketJoinAck ack ) {
                    playerId = ack.playerId;
                    gameState.setPlayerId(playerId);
                    connectionAccept = true;
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
        renderer = new SceneRenderer();
    }
    int licz = 0;
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);
        if(connectionAccept) {
            if(playerInteractions != null )
                playerInteractions.handleInput();
            renderer.draw(viewport, generator.generate(gameState));
            if (inventoryVisible) {
                equipmentStage.act(delta);
                equipmentStage.draw();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                inventoryVisible = !inventoryVisible;
                if (inventoryVisible) {
                    Gdx.input.setInputProcessor(equipmentStage.getInventoryStage());
                } else {
                    Gdx.input.setInputProcessor(null);
                }
            }
        }

        licz ++;
        if(licz%120 == 0)
        {
            gameState.throwTrash();
            licz = 0;
        }
    }



    @Override
    public void resize(int width, int height) {
        if(equipmentStage != null) {
            equipmentStage.resize(width, height);
        }
        viewport.update(width, height, true);
    }
    @Override public void show()    {}
    @Override public void pause()   {}
    @Override public void resume()  {}
    @Override public void hide()    {}
    @Override
    public void dispose() {
        generator.dispose();
    }

}
