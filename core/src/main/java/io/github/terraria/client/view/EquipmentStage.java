package io.github.terraria.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;
import io.github.terraria.client.Recipes.RecipeLoader;
import io.github.terraria.client.state.ClientMainPlayerState;

import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.client.view.textures.texture.TextureBank;

import io.github.terraria.common.StationType;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerTakeItem;

public class EquipmentStage{
    public static int ITEMS_PER_ROW = 10;
    private TextureBank itemsTextures;
    private Stage inventoryStage;
    ShapeRenderer shapeRenderer;
    private Table currentInventoryTable;
    private ClientMainPlayerState playerState;
    private Connection conn;
    private Viewport viewport;
    private RecipeLoader recipes;
    private StationType currentStation;
    private boolean differences;
    public EquipmentStage(Connection conn, ClientMainPlayerState playerState, TextureBank itemTextures)
    {
        this.conn = conn;
        this.playerState = playerState;
        this.itemsTextures = itemTextures;

        viewport = new ScreenViewport();
        inventoryStage = new Stage(viewport);
        shapeRenderer = new ShapeRenderer();

        recipes = new RecipeLoader(itemsTextures,conn, inventoryStage,playerState);
        currentInventoryTable = new Table();
        Table header = new Table();
        header.setFillParent(true);
        header.top();

        Label title = new Label("Ekwipunek", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        header.add(title).padTop(20).center();

        inventoryStage.addActor(header);
        currentStation = StationType.INVENTORY;
        differences = true;
    }

    public void act(float delta)
    {
        inventoryStage.act(delta);
    }
    public void draw()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(inventoryStage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.9f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth()*2, Gdx.graphics.getHeight()*2);//TODo debug why *2
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(playerState != null) {
            if (playerState.hasDifference() || differences) {
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
        differences = false;
        if(currentInventoryTable != null)
            currentInventoryTable.remove();
        currentInventoryTable = new Table();
        currentInventoryTable.setFillParent(true);
        inventoryStage.addActor(currentInventoryTable);

        Table itemsTable = new Table();
        itemsTable.top().left().pad(20);

        int count = 0;

        for (Integer item : playerState.getEquipment().browse()) {

            TextureRegion region = new TextureRegion(itemsTextures.getTexture(item));

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

        Table centerTable = new Table();
        centerTable.top().pad(20);

        if(playerState.getHeldItem() != null) {
            TextureRegion centerRegion = new TextureRegion(itemsTextures.getTexture(playerState.getHeldItem()));
            Image centerItem = new Image(centerRegion);
            centerTable.add(centerItem).size(80);
        }

        Table rightTable = new Table();
        rightTable.top().pad(20);

        currentInventoryTable.add(scrollPane)
            .width(55 * ITEMS_PER_ROW + 20)
            .growY()
            .left()
            .expandY();

        currentInventoryTable.add(centerTable)
            .pad(20)
            .expandX()
            .left();

        currentInventoryTable.add(recipes.getStationCraftings(currentStation))
            .width(600)
            .height(600)
            .pad(10)
            .padRight(30)
            .right()
            .expandX();
    }
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    public void setCurrentStation(StationType station)
    {
        if(currentStation == station)
            return;
        currentStation = station;
        differences = true;
    }
}
