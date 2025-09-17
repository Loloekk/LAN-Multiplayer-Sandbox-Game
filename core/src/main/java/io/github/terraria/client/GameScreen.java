package io.github.terraria.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import io.github.terraria.client.state.PlayerInteractions;
import io.github.terraria.client.view.*;
import io.github.terraria.controler.network.Network;
import io.github.terraria.controler.network.PacketJoin;
import io.github.terraria.controler.network.PacketJoinAck;
import io.github.terraria.client.state.data.ClientGameState;
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


    private Stage inventoryStage;
    private boolean inventoryVisible = false;
    private final Drop game;
//    private final FitViewport viewport = new FitViewport(8, 5);

//    private final ScreenViewport viewport = new ScreenViewport();
//    private final StretchViewport viewport = new StretchViewport(8,5);
    private final ScalingViewport viewport = new ScalingViewport(Scaling.fill, SCENE_WIDTH, SCENE_HEIGHT);
    private SceneGenerator generator;
    private Renderer renderer;
    private Multiset<Integer> equipment = HashMultiset.create();
    Texture dirtTexture = new Texture("dirt.png");

    public GameScreen(Drop game) {
        this.game = game;
        equipment.add(2);
        equipment.add(2);
        equipment.add(2);
        equipment.add(1);
        equipment.add(1);
        equipment.add(1);
        equipment.add(1);
        generator = new SceneGenerator();

        inventoryStage = new Stage(new ScreenViewport());

        // Prosty UI ekwipunku
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label title = new Label("Ekwipunek", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(title).colspan(5).pad(20);
        table.row();

        int i = 0;
        for (Integer itemId : equipment) {
            Texture tex = dirtTexture;
            if (tex == null) continue; // brak tekstury w mapie

            Drawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
            ImageButton button = new ImageButton(drawable);

            final int finalItemId = itemId;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Kliknięto item o numerze " + finalItemId);
                }
            });

            table.add(button).size(64,64).pad(5);

            i++;
            if (i % 5 == 0) {
                table.row();
            }
        }

        inventoryStage.addActor(table);

        client = new Client();
        Network.register(client);
        client.start();
        client.addListener(new Listener() {
            @Override
            public void received(com.esotericsoftware.kryonet.Connection c, Object obj) {
                if (obj instanceof PacketJoinAck ack ) {
                    playerId = ack.playerId;
                    gameState = new ClientGameState(client, playerId);
                    playerInteractions = new PlayerInteractions(client, gameState, playerId,viewport);
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
        if (inventoryVisible) {
            ScreenUtils.clear(Color.WHITE);
            inventoryStage.act(delta);
            inventoryStage.draw();
        }
        else if(playerInteractions != null) {
            ScreenUtils.clear(Color.CYAN);
            playerInteractions.handleInput();
            renderer.draw(viewport, generator.generate(gameState));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            inventoryVisible = !inventoryVisible;
            if (inventoryVisible) {
                // ustawiamy Stage jako input processor dla UI
                Gdx.input.setInputProcessor(inventoryStage);
            } else {
                // wracamy do sterowania grą
                Gdx.input.setInputProcessor(null);
            }
        }
        // === Rysowanie ekwipunku, jeśli włączony ===

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
        inventoryStage.getViewport().update(width, height, true);
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
