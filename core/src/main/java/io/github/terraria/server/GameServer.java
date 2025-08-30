package io.github.terraria.server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.common.Network;
import io.github.terraria.logic.GameState;
import io.github.terraria.logic.RectangleNeighbourhood;
import io.github.terraria.logic.building.LocalPlaneContainer;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.building.StaticPlaneContainerBuilder;
import io.github.terraria.logic.physics.*;
import io.github.terraria.logic.players.*;
import io.github.terraria.view.Scene;

import java.util.concurrent.*;
import java.util.*;
import java.io.IOException;

public class GameServer {
    private Server server;
    private ServerRenderer renderer;
    private int xd = 0;
    private final Map<Connection, Queue<Network.PacketInput>> inputQueues = new ConcurrentHashMap<>();
    private final Map<Integer, Network.PlayerState> playerStates = new ConcurrentHashMap<>();
    private final Map<Connection, Integer> connectionIds = new ConcurrentHashMap<>();
    private volatile int nextPlayerId = 1;
    private static final float VIEW_RADIUS = 300f;

    private GameState gameState;
    private World world;
    private SpawnRegistry spawnRegistry;
    private PlayerRegistry playerRegistry;
    private PlayerActivator playerActivator;
    private PlayerActionService actionService;

    public void start() throws IOException, InterruptedException {
        server = new Server();
        Network.register(server);
        server.bind(Network.TCP_PORT, Network.UDP_PORT);
        server.start();
        renderer = new ServerRenderer();

        // initialize model
        world = new Box2DWorld(new Vector2(0, -10), true);
        StaticPlaneContainerBuilder builder = new StaticPlaneContainerBuilder();
        builder.world(world);
        builder.width(1000).height(80).zeroX(500).zeroY(40);
        PlaneContainer planeContainer = builder.build();
        gameState = new GameState(planeContainer, new ActivePlayersMap(new HashMap<>()));
        System.out.println("Plane container " + planeContainer);
        System.out.println("Gamestate grid = " + gameState.grid());


        spawnRegistry = new SpawnRegistryMap(new HashMap<>());
        playerRegistry = new PlayerRegistryList(spawnRegistry, new ArrayList<>(), new Vector2(0f, 0f));
        playerActivator = new DefaultPlayerActivator(playerRegistry, world, gameState.activePlayers());
        actionService = new PlayerActionServiceImpl(gameState);

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
                    Player pla = playerRegistry.registerPlayer();
                    int id = pla.getId();
                    playerActivator.loginPlayer(id);
                    connectionIds.put(connection, id);
                    inputQueues.put(connection, new ConcurrentLinkedQueue<>());
//                    Network.PlayerState ps = new Network.PlayerState();
//                    ps.id = id; ps.x = 0; ps.y = 0; ps.name = join.name;
//                    playerStates.put(id, ps);
                    Network.PacketJoinAck ack = new Network.PacketJoinAck();
                    ack.playerId = id; ack.name = join.name;
                    System.out.println("Player " + id + " dodany");
                    connection.sendTCP(ack);
                } else if (obj instanceof Network.PacketInput) {
                    inputQueues.getOrDefault(connection, new ConcurrentLinkedQueue<>()).add((Network.PacketInput) obj);
                }
            }
        });

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            handleInput();
            handlePhysics();
            broadcastScenes();
        }, 0, 20, TimeUnit.MILLISECONDS);

        System.out.println("Server started on TCP=" + Network.TCP_PORT + ", UDP=" + Network.UDP_PORT);

        new CountDownLatch(1).await();
    }


    private void handleInput() {
        for (Queue<Network.PacketInput> queue : inputQueues.values()) {
            Network.PacketInput in;
            while ((in = queue.poll()) != null) {
                System.out.println("Get moving " + in.moveX+ " "+ in.playerId);
                if(in.moveX > 0)
                    MoveService.movePlayer(gameState.activePlayers().get(in.playerId),MoveService.Direction.right);
                else if (in.moveX < 0)
                    MoveService.movePlayer(gameState.activePlayers().get(in.playerId),MoveService.Direction.left);

            }
        }
    }

    public void handlePhysics(){

    }

    private void broadcastScenes() {
        try {
            for (Map.Entry<Connection, Integer> entry : connectionIds.entrySet()) {
                Connection conn = entry.getKey();
                int id = entry.getValue();
//                System.out.println("Sending scene to player " + id);
//                System.out.println(gameState.grid() + " why?");
//                LocalPlaneContainer plane = gameState.grid().getLocal(new RectangleNeighbourhood(new Vector2(-10f, -10f), new Vector2(10f, 10f)));
//                Scene scene = renderer.renderScene(plane);
//                conn.sendUDP(scene);
                Network.PlayerState pla = new Network.PlayerState();
                pla.id = id;
                pla.x = gameState.activePlayers().get(id).getIntegerPosition().x();
                pla.y = gameState.activePlayers().get(id).getIntegerPosition().y();
//                for(players : gameState.activePlayers()
                conn.sendUDP(pla);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        xd++;
    }

    public static void main(String[] args) {
        try {
            new GameServer().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
