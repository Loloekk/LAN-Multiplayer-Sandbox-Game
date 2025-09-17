package io.github.terraria.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import io.github.terraria.client.state.ClientPlayerState;

import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.client.view.textures.texture.TextureBank;
import io.github.terraria.client.view.textures.texture.TextureBankLoader;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerHeldItem;

import javax.xml.transform.stream.StreamResult;

public class EquipmentStage{
    private TextureBank itemsTexture;
    private Stage inventoryStage;
    private Table currentTable;
    private ClientPlayerState playerState;
    private Connection conn;
    private Viewport viewport;
    public EquipmentStage(Connection conn, ClientPlayerState playerState)
    {
        viewport = new ScreenViewport();
        inventoryStage = new Stage(viewport);
        TextureBankLoader loader = new TextureBankLoader("missing.png");
        itemsTexture = loader.getTextureBank("textureItems.json");
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
        currentTable.top().left();
        currentTable.padTop(50);
        currentTable.padLeft(20);

        int i = 0;
        for (Integer itemId : playerState.getEquipment().browse()) {
            Texture tex = itemsTexture.getTexture(itemId);
            if (tex == null) continue;

            Drawable drawable = new TextureRegionDrawable(new TextureRegion(tex));

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = drawable;

            ImageButton button = new ImageButton(style);
            button.getImage().setScaling(Scaling.fit);
            button.getImage().setFillParent(true);

            final int finalItemId = itemId;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Kliknięto item o numerze " + finalItemId);
                    PacketPlayerHeldItem held = new PacketPlayerHeldItem();
                    held.itemId = finalItemId;
                    held.playerId = playerState.getPlayerId();
                    conn.sendTCP(held);
                }
            });

            currentTable.add(button).size(64, 64).pad(2);

            i++;
            if (i % 10 == 0) {
                currentTable.row();
            }
        }

        inventoryStage.addActor(currentTable);
    }
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
