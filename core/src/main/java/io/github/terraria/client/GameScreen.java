package io.github.terraria.client;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.common.Network;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class GameScreen implements Screen {
    private Client client;

    private final Drop game;
    private int playerId;
    private Map<Integer, Network.PlayerState> players = new HashMap<>();
    private ShapeRenderer renderer = new ShapeRenderer();

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
                } else if (obj instanceof Network.PacketState) {
                    Network.PacketState st = (Network.PacketState)obj;
                    players.clear();
                    for (Network.PlayerState ps : st.players) players.put(ps.id, ps);
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
    }

    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Network.PlayerState p : players.values()) {
            renderer.circle(p.x, p.y, 10);
        }
        renderer.end();
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
