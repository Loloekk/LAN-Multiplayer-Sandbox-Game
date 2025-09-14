package io.github.terraria.controler.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import io.github.terraria.common.BlockState;
import io.github.terraria.common.Config;
import io.github.terraria.common.PlayerState;
import io.github.terraria.client.view.DrawableRectangle;
import io.github.terraria.client.view.Scene;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayer;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayerHit;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayerMove;

import java.util.ArrayList;

public class Network {
    public static final int TCP_PORT = Config.TCP_PORT;
    public static final int UDP_PORT = Config.UDP_PORT;

    public static void register(EndPoint endpoint) {
        Kryo kryo = endpoint.getKryo();
        kryo.register(PacketJoin.class);
        kryo.register(PacketJoinAck.class);
        kryo.register(PacketPlayer.class);
        kryo.register(PacketPlayerMove.class);
        kryo.register(PacketPlayerHit.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(PlayerState.class);
        kryo.register(Scene.class);
        kryo.register(DrawableRectangle.class);
        kryo.register(ArrayList.class);
        kryo.register(BlockState.class);
        kryo.register(PacketPlayerDisappear.class);
    }
}
