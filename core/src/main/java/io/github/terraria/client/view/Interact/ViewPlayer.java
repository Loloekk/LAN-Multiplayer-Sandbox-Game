package io.github.terraria.client.view.Interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Connection;
import io.github.terraria.client.view.Interact.Data.ViewPlayerData;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayerHit;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayerMove;

public class ViewPlayer {
    Connection conn;
    private int playerId;
    ViewPlayerData playerData;
    Viewport viewport;


    private long lastLeftClickTime = 0;
    private static final long LEFT_CLICK_DELAY = 200;
    public ViewPlayer(Connection conn, int playerId, Viewport viewport)
    {
        this.conn = conn;
        this.playerId = playerId;
        this.viewport = viewport;
        playerData = new ViewPlayerData(conn, playerId);
    }
    public ViewPlayerData getData()
    {
        return playerData;
    }
    public void actualize(Object obj)
    {
        playerData.actualize(obj);
    }
    public void throwTrash()
    {
        playerData.throwTrash();
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
            move.playerId = playerId;
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
                mousePos = getGamePosition(mousePos);
//                System.out.println("Klik LPM w świecie gry: " + mousePos.x + ", " + mousePos.y);
                PacketPlayerHit hit = new PacketPlayerHit();
                hit.playerId = playerId;
                hit.x = mousePos.x;
                hit.y = mousePos.y;
                conn.sendUDP(hit);
//                System.out.println("Send hiting " + playerId);
            }
        }
    }
    private Vector2 getGamePosition(Vector2 screenPos)
    {
        return new Vector2(playerData.getX() + (screenPos.x - 15), playerData.getY() + (screenPos.y - 10));
    }
}
