package io.github.terraria.controler.server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.common.Config;
import io.github.terraria.controler.Network.Network;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayer;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayerHit;
import io.github.terraria.controler.Network.PacketPlayer.PacketPlayerMove;
import io.github.terraria.controler.Network.PacketJoin;
import io.github.terraria.controler.Network.PacketJoinAck;
import io.github.terraria.controler.PlayerNetworkData.PlayerData;
import io.github.terraria.logic.actions.GameState;
import io.github.terraria.logic.actions.MoveService;
import io.github.terraria.logic.actions.PlayerActionService;
import io.github.terraria.logic.actions.PlayerActionServiceImpl;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.building.StaticPlaneContainerBuilder;
import io.github.terraria.logic.physics.*;
import io.github.terraria.logic.players.*;

import java.util.concurrent.*;
import java.util.*;
import java.io.IOException;

public class GameServer {
    private Server server;
    private final Map<Connection, Queue<PacketPlayer>> inputQueues = new ConcurrentHashMap<>();
    private final Map<Connection, PlayerData> connectionIds = new ConcurrentHashMap<>();

    private GameState gameState;
    private World world;
    private PlayerRegistry playerRegistry;
    private PlayerActivator playerActivator;
    private PlayerActionService actionService;

    public void start() throws IOException, InterruptedException {
        server = new Server();
        Network.register(server);
        server.bind(Network.TCP_PORT, Network.UDP_PORT);
        server.start();

        // initialize model
        world = new Box2DWorld(new Vector2(0, -10), true);
        StaticPlaneContainerBuilder builder = new StaticPlaneContainerBuilder();
        builder.world(world);
        builder.width(100).height(40).zeroX(50).zeroY(10);
        PlaneContainer planeContainer = builder.build();
        gameState = new GameState(planeContainer, new ActivePlayersMap(new HashMap<>()));
//        System.out.println("Plane container " + planeContainer);
//        System.out.println("Gamestate grid = " + gameState.grid());


        playerRegistry = new PlayerRegistryList(new ArrayList<>(), new Vector2(0f, 0f));
        playerActivator = new DefaultPlayerActivator(playerRegistry, world, gameState.activePlayers(), planeContainer);
        actionService = new PlayerActionServiceImpl(gameState);

        server.addListener(new Listener() {
            @Override public void connected(Connection connection) {}
            @Override public void disconnected(Connection connection) {
                Integer id = connectionIds.get(connection).getPlayerId();
                connectionIds.remove(connection);
                inputQueues.remove(connection);
                playerActivator.logoutPlayer(id);
                System.out.println("Player " + id + " dissconected");
            }
            @Override public void received(Connection connection, Object obj) {
                if (obj instanceof PacketJoin join) {
                    PlayerRecord pla = playerRegistry.registerPlayer();
                    int id = pla.id();
                    PacketJoinAck ack = new PacketJoinAck();
                    ack.playerId = id; ack.name = join.name;
                    System.out.println("Player " + id + " dodany");
                    connection.sendTCP(ack);

                    PlayerData playerState = new PlayerData(connection,gameState,id);
                    playerActivator.loginPlayer(id);
                    connectionIds.put(connection, playerState);
                    inputQueues.put(connection, new ConcurrentLinkedQueue<>());
//                    Network.PlayerState ps = new Network.PlayerState();
//                    ps.id = id; ps.x = 0; ps.y = 0; ps.name = join.name;
//                    playerStates.put(id, ps);
                } else if (obj instanceof PacketPlayer) {
                    inputQueues.getOrDefault(connection, new ConcurrentLinkedQueue<>()).add((PacketPlayer) obj);
                }
            }
        });

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            handleInput();
            handlePhysics();
            broadcastScenes();
        }, 0, 20, TimeUnit.MILLISECONDS);

        new CountDownLatch(1).await();
            System.out.println("Server started on TCP=" + Network.TCP_PORT + ", UDP=" + Network.UDP_PORT);
    }

    int licz = 0;
    private void handleInput() {
        for (Map.Entry<Connection ,Queue<PacketPlayer>> entry : inputQueues.entrySet()) {
            PacketPlayer in;
            int playerId = connectionIds.get(entry.getKey()).getPlayerId();
            Queue<PacketPlayer> queue = entry.getValue();
            while ((in = queue.poll()) != null) {
                System.out.println(in);
                if(in.getPlayerId() != playerId) {
                    continue;
                }
                if(in instanceof PacketPlayerMove move) {
                    if (move.moveX > 0)
                        MoveService.movePlayer(gameState.activePlayers().get(playerId), MoveService.Direction.right);
                    else if (move.moveX < 0)
                        MoveService.movePlayer(gameState.activePlayers().get(playerId), MoveService.Direction.left);

                    if (move.jump == true) MoveService.jumpPlayer(gameState.activePlayers().get(playerId));
                }
                if(in instanceof PacketPlayerHit hit)
                {
                    Vector2 pos = new Vector2(hit.x,hit.y);
//                    System.out.println("Player " + playerId + " hit at " + pos.x + " "+ pos.y);
                    actionService.hitAt(gameState.activePlayers().get(playerId),pos);
                }
            }
        }
    }

    public void handlePhysics() {
        world.step(Config.PHYSICS_TIME_STEP, Config.PHYSICS_VELOCITY_ITERATIONS, Config.PHYSICS_POSITION_ITERATIONS);
    }
    private void broadcastScenes() {
        try {
            for (Map.Entry<Connection, PlayerData> entry : connectionIds.entrySet()) {
                PlayerData playerState = entry.getValue();
                playerState.actualize();
            }
        }catch (Exception e){
            e.printStackTrace();
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
