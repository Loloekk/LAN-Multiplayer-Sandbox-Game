package io.github.terraria.controler.server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.controler.Network.Network;
import io.github.terraria.controler.Network.PacketJoin;
import io.github.terraria.controler.Network.PacketJoinAck;
import io.github.terraria.controler.PlayerNetworkData.PlayerData;
import io.github.terraria.logic.GameState;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.building.StaticPlaneContainerBuilder;
import io.github.terraria.logic.physics.*;
import io.github.terraria.logic.players.*;

import java.util.concurrent.*;
import java.util.*;
import java.io.IOException;

public class GameServer {
    private Server server;
    private ServerRenderer renderer;
    private final Map<Connection, Queue<Network.PacketInput>> inputQueues = new ConcurrentHashMap<>();
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
        renderer = new ServerRenderer();

        // initialize model
        world = new Box2DWorld(new Vector2(0, -10), true);
        StaticPlaneContainerBuilder builder = new StaticPlaneContainerBuilder();
        builder.world(world);
        builder.width(27).height(20).zeroX(0).zeroY(10);
        PlaneContainer planeContainer = builder.build();
        gameState = new GameState(planeContainer, new ActivePlayersMap(new HashMap<>()));
//        System.out.println("Plane container " + planeContainer);
//        System.out.println("Gamestate grid = " + gameState.grid());


        playerRegistry = new PlayerRegistryList(new ArrayList<>(), new Vector2(3f, 3f));
        playerActivator = new DefaultPlayerActivator(playerRegistry, world, gameState.activePlayers());
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
                    int id = pla.getId();
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

//        for(int j = 4; j >=-5; j --)
//        {
//            for(int i = 0; i < 20 ;i ++)
//                System.out.print(gameState.grid().getFrontBlockAt(i,j)+ " ");
//            System.out.println();
//        }
//        ArrayList<ArrayList<ArrayList>> Wejscie = GameState.grid.
        new CountDownLatch(1).await();
            System.out.println("Server started on TCP=" + Network.TCP_PORT + ", UDP=" + Network.UDP_PORT);
    }

    int licz = 0;
    private void handleInput() {
        for (Queue<Network.PacketInput> queue : inputQueues.values()) {
            Network.PacketInput in;
            while ((in = queue.poll()) != null) {
                //System.out.println("Get moving " + in.moveX+ " "+ in.playerId);
                if(in.moveX > 0)
                    MoveService.movePlayer(gameState.activePlayers().get(in.playerId),MoveService.Direction.right);
                else if (in.moveX < 0)
                    MoveService.movePlayer(gameState.activePlayers().get(in.playerId),MoveService.Direction.left);

                if(in.moveY > 0)MoveService.jumpPlayer(gameState.activePlayers().get(in.playerId));
                licz++;
                if(licz%100 == 0)
                {
                    System.out.println("liczy " + licz);
                }
                if(licz%200 == 0)
                {
                    IntVector2 Intloc = new IntVector2(1,-1);
                    Vector2 loc = new Vector2(1,-1);
                    actionService.hitAt(gameState.activePlayers().get(in.playerId),loc);
//                    gameState.grid().removeFrontBlockAt(Intloc);
                }
            }
        }
    }

    public void handlePhysics() {
        float timeStep = 0.02f;
        int velocityIterations = 8;
        int positionIterations = 4;
        world.step(timeStep,velocityIterations,positionIterations);
    }
    private void broadcastScenes() {
        try {
            for (Map.Entry<Connection, PlayerData> entry : connectionIds.entrySet()) {
                PlayerData playerState = entry.getValue();
//                System.out.println("Sending scene to player " + id);
//                System.out.println(gameState.grid() + " why?");
//                LocalPlaneContainer plane = gameState.grid().getLocal(new RectangleNeighbourhood(new Vector2(-10f, -10f), new Vector2(10f, 10f)));
//                Scene scene = renderer.renderScene(plane);
//                conn.sendUDP(scene);
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
