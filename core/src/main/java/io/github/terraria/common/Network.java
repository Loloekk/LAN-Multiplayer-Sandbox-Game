package io.github.terraria.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import io.github.terraria.view.DrawableRectangle;
import io.github.terraria.view.Scene;

import java.util.ArrayList;
import java.util.List;

public class Network {
    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;

    public static void register(EndPoint endpoint) {
        Kryo kryo = endpoint.getKryo();
        kryo.register(PacketJoin.class);
        kryo.register(PacketJoinAck.class);
        kryo.register(PacketInput.class);
        kryo.register(PacketState.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(PlayerState.class);
        kryo.register(Scene.class);
        kryo.register(DrawableRectangle.class);
        kryo.register(ArrayList.class);
    }

    // Client → Server: join request with player name
    public static class PacketJoin {
        public String name;
    }
    // Server → Client: join acknowledgement with assigned ID
    public static class PacketJoinAck {
        public int playerId;
        public String name;
    }
    // Client → Server: input/movement
    public static class PacketInput {
        public int playerId;
        public float moveX, moveY;
    }
    // Server → Client: state update
    public static class PacketState {
        public List<PlayerState> players;
        public long timestamp;
    }
    // Individual player state
    public static class PlayerState {
        public int id;
        public float x, y;
        public String name;
    }
}
