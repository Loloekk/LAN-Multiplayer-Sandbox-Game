package io.github.terraria.controler.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import io.github.terraria.common.BlockState;
import io.github.terraria.common.Config;
import io.github.terraria.controler.network.PacketServerToClient.*;
import io.github.terraria.client.view.DrawableRectangle;
import io.github.terraria.client.view.Scene;
import io.github.terraria.controler.network.PacketClientToServer.*;
import io.github.terraria.utils.IntVector2;

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
        kryo.register(PacketPlayerState.class);
        kryo.register(Scene.class);
        kryo.register(DrawableRectangle.class);
        kryo.register(ArrayList.class);
        kryo.register(BlockState.class);
        kryo.register(PacketDisappearPlayer.class);
        kryo.register(PacketCollectItems.class);
        kryo.register(PacketRemoveItems.class);
        kryo.register(PacketPlayerTakeItem.class);
        kryo.register(PacketPlayerTouch.class);
        kryo.register(PacketPlayerHeldItem.class);
        kryo.register(PacketCraftItems.class);
        kryo.register(IntVector2.class);
        kryo.register(PacketDisappearMob.class);
        kryo.register(PacketMobState.class);
    }
}
