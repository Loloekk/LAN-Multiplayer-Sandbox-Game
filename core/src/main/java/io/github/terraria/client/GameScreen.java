package io.github.terraria.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.controler.Network.Network;
import io.github.terraria.controler.Network.PacketJoin;
import io.github.terraria.controler.Network.PacketJoinAck;
import io.github.terraria.client.view.PlayerData.ViewPlayerData;
import io.github.terraria.client.view.Renderer;
import io.github.terraria.client.view.Scene;
import io.github.terraria.client.view.SceneGenerator;
import io.github.terraria.client.view.TextureBank;
import com.badlogic.gdx.graphics.Color;

import java.io.IOException;
import java.util.ArrayList;

public class GameScreen implements Screen {
    private Client client;
    private int playerId;
    private ViewPlayerData playerData;
    private final Drop game;
//    private final FitViewport viewport = new FitViewport(8, 5);

//    private final ScreenViewport viewport = new ScreenViewport();
//    private final StretchViewport viewport = new StretchViewport(8,5);
    private final ScalingViewport viewport = new ScalingViewport(Scaling.fill, 30, 20);
    private TextureBank textureBank;
    private Renderer renderer;
    private Scene currentScene = new Scene();
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
                if (obj instanceof PacketJoinAck ack ) {
                    playerId = ack.playerId;
                    playerData = new ViewPlayerData(client,playerId);
                    Gdx.app.log("GameScreen", "Joined as id=" + playerId + ", name=" + ack.name);

                }
                else if (obj instanceof ArrayList list)
                {
                    if(playerData == null) {
                        Gdx.app.log("communication error","XD");
                    }
                    for(Object objj : list)
                    {
//                        System.out.println("mamy liste" + objj);
                        playerData.actualize(objj);

                    }
                }
                else {
                    Gdx.app.log("GameScreen", "Scene update");
                    if(playerData == null) {
                        Gdx.app.log("communication error","XD");
                    }
                    playerData.actualize(obj);
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
        textureBank = new TextureBank(new Texture("missing.png"));
        PLAYER_TEXTURE = textureBank.registerTexture(new Texture("stefan.png"));
        STONE_TEXTURE = textureBank.registerTexture(new Texture("stone.png"));
        renderer = new Renderer(textureBank);
    }
    int licz = 0;
    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(Color.CYAN);
        if(playerData == null)
            return;
        SceneGenerator.generate(playerData);
        renderer.draw(viewport, SceneGenerator.generate(playerData));
        licz ++;
        if(licz%120 == 0)
        {
            playerData.throwTrash();
            licz = 0;
        }
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
            System.out.println("Send moving " + playerId);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(mousePos);
            System.out.println("Klik LPM w świecie gry: " + mousePos.x + ", " + mousePos.y);
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
