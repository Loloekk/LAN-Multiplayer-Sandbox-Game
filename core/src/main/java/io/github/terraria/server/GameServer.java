package io.github.terraria.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.common.Network;
import java.util.concurrent.*;
import java.util.*;
import java.io.IOException;

public class GameServer {
    private Server server;
    private final Map<Connection, Queue<Network.PacketInput>> inputQueues = new ConcurrentHashMap<>();
    private final Map<Integer, Network.PlayerState> playerStates = new ConcurrentHashMap<>();
    private final Map<Connection, Integer> connectionIds = new ConcurrentHashMap<>();
    private volatile int nextPlayerId = 1;
    private static final float VIEW_RADIUS = 300f;

    public void start() throws IOException, InterruptedException {
        server = new Server();
        Network.register(server);
        server.bind(Network.TCP_PORT, Network.UDP_PORT);
        server.start();

        server.addListener(new Listener() {
            @Override public void connected(Connection connection) {}
            @Override public void disconnected(Connection connection) {
                Integer id = connectionIds.remove(connection);
                inputQueues.remove(connection);
                if (id != null) playerStates.remove(id);
            }
            @Override public void received(Connection connection, Object obj) {
                if (obj instanceof Network.PacketJoin) {
                    Network.PacketJoin join = (Network.PacketJoin) obj;
                    int id = nextPlayerId++;
                    connectionIds.put(connection, id);
                    inputQueues.put(connection, new ConcurrentLinkedQueue<>());
                    Network.PlayerState ps = new Network.PlayerState();
                    ps.id = id; ps.x = 0; ps.y = 0; ps.name = join.name;
                    playerStates.put(id, ps);
                    Network.PacketJoinAck ack = new Network.PacketJoinAck();
                    ack.playerId = id; ack.name = join.name;
                    connection.sendTCP(ack);
                } else if (obj instanceof Network.PacketInput) {
                    inputQueues.getOrDefault(connection, new ConcurrentLinkedQueue<>()).add((Network.PacketInput) obj);
                }
            }
        });

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            updateLogic();
            broadcastFilteredStates();
        }, 0, 50, TimeUnit.MILLISECONDS);

        System.out.println("Server started on TCP=" + Network.TCP_PORT + ", UDP=" + Network.UDP_PORT);

        new CountDownLatch(1).await();
    }


    private void updateLogic() {
        for (Queue<Network.PacketInput> queue : inputQueues.values()) {
            Network.PacketInput in;
            while ((in = queue.poll()) != null) {
                Network.PlayerState ps = playerStates.get(in.playerId);
                if (ps != null) {
                    ps.x += in.moveX * 1f;
                    ps.y += in.moveY * 1f;
                }
            }
        }
    }

    private void broadcastFilteredStates() {
        long now = System.currentTimeMillis();
        for (Map.Entry<Connection, Integer> entry : connectionIds.entrySet()) {
            Connection conn = entry.getKey();
            Network.PlayerState self = playerStates.get(entry.getValue());
            if (self == null) continue;
            List<Network.PlayerState> nearby = new ArrayList<>();
            for (Network.PlayerState p : playerStates.values()) {
                float dx = p.x - self.x, dy = p.y - self.y;
                if (dx*dx + dy*dy <= VIEW_RADIUS*VIEW_RADIUS) nearby.add(p);
            }
            Network.PacketState state = new Network.PacketState();
            state.players = nearby;
            state.timestamp = now;
            conn.sendUDP(state);
        }
    }

    public static void main(String[] args) {
        try {
            new GameServer().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
