package io.github.terraria.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.client.Recipes.RecipeLoader;
import io.github.terraria.client.state.ClientGameState;
import io.github.terraria.client.view.EquipmentStage;
import io.github.terraria.client.view.SceneRenderer;
import io.github.terraria.client.view.ganarators.SceneGenerator;
import io.github.terraria.client.view.textures.texture.TextureBank;
import io.github.terraria.client.view.textures.texture.TextureBankLoader;
import io.github.terraria.client.view.textures.textureQuad.TextureQuadBank;
import io.github.terraria.client.view.textures.textureQuad.TextureQuadBankLoader;
import io.github.terraria.common.Config;
import io.github.terraria.common.StationType;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerHit;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerMove;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerTouch;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.utils.MathUtils;

public class GameIOHandler {
    private Connection conn;
    private Viewport viewport;

    private ClientGameState gameState;
    private StationBank stationBank;

    private SceneGenerator generator;
    private SceneRenderer renderer;
    private EquipmentStage equipmentStage;


    private TextureBank itemsTexture;
    private TextureQuadBank blocksTexture;
    private TextureQuadBank playerTexture;
    private TextureQuadBank mobsTexture;
    private TextureQuadBank projectilesTexture;
    private StationType currentStation;

    private boolean inventoryVisible = false;

    private long lastLeftClickTime = 0;
    private long lastRightClickTime = 0;
    private long frames = 0;
    private static final long LEFT_CLICK_DELAY = Config.LEFT_CLICK_DELAY;
    private static final long RIGHT_CLICK_DELAY = Config.LEFT_CLICK_DELAY;
    public static int SCENE_LAYERS = Config.SCENE_LAYERS;
    public GameIOHandler(Connection conn, Viewport viewport)
    {
        this.conn = conn;
        this.viewport = viewport;
        gameState = new ClientGameState(conn);
        stationBank = new StationBank("blocks.json");


        TextureBankLoader loader = new TextureBankLoader("missing.png");
        itemsTexture = loader.getTextureBank("textureItems.json");
        TextureQuadBankLoader loaderQuad = new TextureQuadBankLoader("missing.png");
        blocksTexture = loaderQuad.getTextureQuadBank("textureBlocks.json");
        playerTexture = loaderQuad.getTextureQuadBank("texturePlayer.json");
        mobsTexture = loaderQuad.getTextureQuadBank("textureMobs.json");
        projectilesTexture = loaderQuad.getTextureQuadBank("textureProjectiles.json");


        generator = new SceneGenerator(blocksTexture, playerTexture, itemsTexture, mobsTexture, projectilesTexture);
        equipmentStage = new EquipmentStage(conn,gameState.getMainPlayerState(), itemsTexture);
        renderer = new SceneRenderer();

        currentStation = StationType.INVENTORY;
    }

    public void handleInput() {
        int mx = 0;
        int my = 0;
        boolean jump = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  mx += -1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) mx += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) my += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) my += -1;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) jump=true;
        if (Gdx.input.isKeyPressed(Input.Keys.A))  mx += -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) mx += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) my += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) my += -1;
        {
            PacketPlayerMove move = new PacketPlayerMove();
            move.playerId = gameState.getMainPlayerState().getPlayerId();
            move.direction = new IntVector2(mx, my);
            move.jump = jump;
            conn.sendUDP(move);
//            System.out.println("Send moving " + playerId);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            long now = TimeUtils.millis();
            if (now - lastLeftClickTime >= LEFT_CLICK_DELAY) {
                lastLeftClickTime = now;
                Vector3 mousePos3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                viewport.unproject(mousePos3);
                Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
                mousePos = gameState.getGamePosition(mousePos);
//                System.out.println("Klik LPM w świecie gry: " + mousePos.x + ", " + mousePos.y);
                PacketPlayerHit hit = new PacketPlayerHit();
                hit.playerId = gameState.getMainPlayerState().getPlayerId();
                hit.x = mousePos.x;
                hit.y = mousePos.y;
                conn.sendTCP(hit);
//                System.out.println("Send hiting " + playerId);
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            long now = TimeUtils.millis();
            if (now - lastRightClickTime >= RIGHT_CLICK_DELAY) {
                lastRightClickTime = now;
                Vector3 mousePos3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                viewport.unproject(mousePos3);
                Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
                mousePos = gameState.getGamePosition(mousePos);

                Integer itemId = null;
                for(int z = 0; z < SCENE_LAYERS; z ++)
                {
                    itemId = gameState.getBlockId(MathUtils.floor(mousePos.x),MathUtils.floor(mousePos.y), z);
                    if(itemId != null)
                        break;
                }
                if(stationBank.isStation(itemId))
                {
                    equipmentStage.setCurrentStation(stationBank.getStation(itemId));
                    inventoryVisible = true;
                    Gdx.input.setInputProcessor(equipmentStage.getInventoryStage());
                }
                else{
                    PacketPlayerTouch touch = new PacketPlayerTouch();
                    touch.playerId = gameState.getMainPlayerState().getPlayerId();
                    touch.x = mousePos.x;
                    touch.y = mousePos.y;
                    conn.sendTCP(touch);
                }

//                System.out.println("Send hiting " + playerId);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            equipmentStage.setCurrentStation(StationType.INVENTORY);
            inventoryVisible = !inventoryVisible;
            if (inventoryVisible) {
                Gdx.input.setInputProcessor(equipmentStage.getInventoryStage());
            } else {
                Gdx.input.setInputProcessor(null);
            }
        }
    }
    public void actualize(Object obj)
    {
        gameState.actualize(obj);
    }
    public void setPlayerId(int id)
    {
        gameState.setPlayerId(id);
    }
    public void draw(float delta)
    {
        renderer.draw(viewport, generator.generate(gameState));
        if (inventoryVisible) {
            equipmentStage.act(delta);
            equipmentStage.draw();
        }
        frames ++;
        if(frames%120 == 0)
        {
            gameState.throwTrash();
            frames = 0;
        }
    }
    public void resize(int width, int height)
    {
        equipmentStage.resize(width, height);
    }
    public void dispose()
    {
        itemsTexture.dispose();
        blocksTexture.dispose();
        playerTexture.dispose();
    }
}
