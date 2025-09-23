package io.github.terraria.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.controler.network.*;

import java.io.IOException;

public class MenuScreen implements Screen {
    private final Drop game;
    private final Stage stage;
    private final Skin skin;
    private Label label;
    private Label message;
    private TextField nameField;
    private TextButton joinButton;
    private Client client;
    private boolean connected = false;
    private int id;
    private String name;
    private final Listener connectListener = new Listener(){
        @Override
        public void received(Connection connection, Object object) {
            if(object instanceof PacketRegisterAck ack){
                id = ack.id;
                connected = true;
            }else if(object instanceof  PacketNameTaken){
                message.setText("Player with this name is logged in!");
                joinButton.setDisabled(false);
                connection.close();
            }else{
                message.setText("Unknown problem during login");
                joinButton.setDisabled(false);
                connection.close();
            }
        }
    };
    private void tryConnecting(){
        joinButton.setDisabled(true);
        name = nameField.getText();
        client = new Client();
        Network.register(client);
        client.start();

        client.addListener(connectListener);
        try{
            client.connect(5000, "localhost", Network.TCP_PORT, Network.UDP_PORT);
            PacketRegister pj = new PacketRegister();
            pj.name = nameField.getText();
            client.sendTCP(pj);
        }catch (IOException e){
            message.setText("Connection timed out");
            joinButton.setDisabled(false);
            e.printStackTrace();
        }
    }

    public MenuScreen(Drop game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        label = new Label("Enter player name:", skin);
        message = new Label("", skin);
        message.setColor(Color.RED);
        nameField = new TextField("", skin);
        joinButton = new TextButton("Join", skin);
        joinButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               tryConnecting();
           }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);
        table.add(label).row();
        table.add(nameField).width(400).row();
        table.add(joinButton).width(400).row();
        table.add(message);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
        if(connected){
            client.removeListener(connectListener);
            game.setScreen(new GameScreen(game, client, name, id));
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
