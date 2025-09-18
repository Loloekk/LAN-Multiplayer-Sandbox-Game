package io.github.terraria.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.client.state.ClientGameState;
import io.github.terraria.common.Config;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerHit;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerMove;
import io.github.terraria.controler.network.PacketClientToServer.PacketPlayerTouch;

public class PlayerInteractions {
    Connection conn;
    Viewport viewport;
    ClientGameState gameState;

    private long lastLeftClickTime = 0;
    private long lastRightClickTime = 0;
    private static final long LEFT_CLICK_DELAY = Config.LEFT_CLICK_DELAY;
    public PlayerInteractions(Connection conn, ClientGameState gameState, Viewport viewport)
    {
        this.conn = conn;
        this.gameState = gameState;
        this.viewport = viewport;
    }
    public void handleInput() {
        short mx=0;
        boolean jump = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  mx+=-1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) mx+=1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    jump=true;
        if (Gdx.input.isKeyPressed(Input.Keys.A))  mx+=-1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) mx+=1;
        if (Gdx.input.isKeyPressed(Input.Keys.W))    jump=true;
        if (mx!=0 || jump!=false) {
            PacketPlayerMove move = new PacketPlayerMove();
            move.playerId = gameState.getMainPlayerState().getPlayerId();
            move.moveX = mx;
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
            if (now - lastRightClickTime >= LEFT_CLICK_DELAY) {
                lastRightClickTime = now;
                Vector3 mousePos3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                viewport.unproject(mousePos3);
                Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
                mousePos = gameState.getGamePosition(mousePos);
//                System.out.println("Klik LPM w świecie gry: " + mousePos.x + ", " + mousePos.y);
                PacketPlayerTouch touch = new PacketPlayerTouch();
                touch.playerId = gameState.getMainPlayerState().getPlayerId();
                touch.x = mousePos.x;
                touch.y = mousePos.y;
                conn.sendTCP(touch);
//                System.out.println("Send hiting " + playerId);
            }
        }
    }
}
