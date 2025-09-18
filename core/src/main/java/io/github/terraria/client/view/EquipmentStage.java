package io.github.terraria.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import io.github.terraria.client.state.ClientMainPlayerState;

import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.client.view.textures.texture.TextureBank;

import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerTakeItem;

public class EquipmentStage{
    public static int ITEMS_PER_ROW = 10;
    private TextureBank itemsTexture;
    private Stage inventoryStage;
    private Table currentTable;
    private ClientMainPlayerState playerState;
    private Connection conn;
    private Viewport viewport;
    public EquipmentStage(Connection conn, ClientMainPlayerState playerState, TextureBank itemTexture)
    {
        viewport = new ScreenViewport();
        inventoryStage = new Stage(viewport);
        this.itemsTexture = itemTexture;
        currentTable = new Table();
        Table header = new Table();
        header.setFillParent(true);
        header.top();

        Label title = new Label("Ekwipunek", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        header.add(title).padTop(20).center();

        inventoryStage.addActor(header);

        this.conn = conn;
        this.playerState = playerState;
    }

    public void act(float delta)
    {
        inventoryStage.act(delta);
    }
    public void draw()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(inventoryStage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.9f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(playerState != null) {
            if (playerState.hasDifference()) {
                generateEquipment();
            }
            inventoryStage.draw();
        }
    }
    public Stage getInventoryStage()
    {
        return inventoryStage;
    }
    private void generateEquipment()
    {
        if(currentTable != null)
            currentTable.remove();
        currentTable = new Table();
        currentTable.setFillParent(true);
        inventoryStage.addActor(currentTable);

        Table itemsTable = new Table();
        itemsTable.top().left().pad(20);

        int count = 0;

        for (Integer item : playerState.getEquipment().browse()) {

            TextureRegion region = new TextureRegion(itemsTexture.getTexture(item));

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(region);
            ImageButton itemButton = new ImageButton(style);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Kliknięto item o numerze " + item);
                    PacketPlayerTakeItem take = new PacketPlayerTakeItem();
                    take.itemId = item;
                    take.playerId = playerState.getPlayerId();
                    conn.sendTCP(take);
                }
            });

            itemsTable.add(itemButton).size(40).pad(5);
            count++;

            if (count % ITEMS_PER_ROW == 0) {
                itemsTable.row();
            }
        }

        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        ScrollPane scrollPane = new ScrollPane(itemsTable, scrollStyle);
        scrollPane.setFadeScrollBars(false);

        currentTable.add(scrollPane).growY().align(Align.left);
        currentTable.add().grow();

    }
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
