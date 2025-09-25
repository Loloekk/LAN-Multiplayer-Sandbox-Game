package io.github.terraria.controler.server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.terraria.common.Config;
import io.github.terraria.controler.network.*;
import io.github.terraria.controler.network.PacketClientToServer.*;
import io.github.terraria.controler.playerNetworkData.PlayerData;
import io.github.terraria.loading.BlockFactoryLoader;
import io.github.terraria.loading.StationTypeMapLoader;
import io.github.terraria.logic.actions.*;
import io.github.terraria.logic.building.BlockFactory;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.building.StaticPlaneContainerBuilder;
import io.github.terraria.logic.crafting.CraftingService;
import io.github.terraria.logic.crafting.Recipe;
import io.github.terraria.logic.crafting.RecipeRepo;
import io.github.terraria.logic.crafting.RecipeRepoImpl;
import io.github.terraria.logic.crafting.station.StationTypeMap;
import io.github.terraria.logic.building.StaticPlaneContainerBuilder;
import io.github.terraria.logic.creatures.*;
import io.github.terraria.logic.creatures.bots.BotRegistry;
import io.github.terraria.logic.creatures.projectiles.ProjectileRegistry;
import io.github.terraria.logic.equipment.ItemRegistry;
import io.github.terraria.logic.equipment.ObservableMultisetItemHolder;
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
    private MobWorldInteractor mobWorldInteractor;
    private CreatureFactory creatureFactory;
    private CreatureRegistry creatureRegistry;
    private ProjectileRegistry projectileRegistry;
    private BotRegistry botRegistry = new BotRegistry();
    private ItemRegistry itemRegistry;
    private List<com.badlogic.gdx.physics.box2d.Body> bodiesToDestroy = new ArrayList<>();
    private Creature depressedZombie;

    private CraftingService craftingService;
    private CraftingActionService craftingActionService;

    public void start() throws IOException, InterruptedException {
        server = new Server();
        Network.register(server);
        server.bind(Network.TCP_PORT, Network.UDP_PORT);
        server.start();

        // initialize model
        com.badlogic.gdx.physics.box2d.World boxWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -10), true);
        boxWorld.setContactListener(new CollisionHandler());
        world = new Box2DWorld(boxWorld);
        BlockFactory blockFactory = new BlockFactoryLoader().getBlockFactory();
        StaticPlaneContainerBuilder builder = new StaticPlaneContainerBuilder().blockFactory(blockFactory);
        itemRegistry = new ItemRegistry();
        builder.world(world);
        builder.width(100).height(40).zeroX(50).zeroY(20);
        PlaneContainer planeContainer = builder.build();
//        System.out.println("Plane container " + planeContainer);
//        System.out.println("Gamestate grid = " + gameState.grid());
        projectileRegistry = new ProjectileRegistry();
        creatureRegistry = new CreatureRegistry(boxWorld, bodiesToDestroy, projectileRegistry);
        mobWorldInteractor = new MobWorldInteractor(boxWorld, bodiesToDestroy, creatureRegistry, projectileRegistry);
        creatureFactory = new CreatureFactory(boxWorld, bodiesToDestroy, mobWorldInteractor);
        playerRegistry = new PlayerRegistryList(new ArrayList<>(), new Vector2(0f, 0f));
        gameState = new GameState(planeContainer, new ActivePlayersMap(new HashMap<>()), creatureRegistry, projectileRegistry);
        playerActivator = new DefaultPlayerActivator(playerRegistry, world, gameState.activePlayers(), planeContainer, creatureRegistry, creatureFactory);
        actionService = new PlayerActionServiceImpl(gameState);
        depressedZombie = creatureFactory.createZombieCreature(new Vector2(0f, 10f));
        creatureRegistry.registerMob(depressedZombie);

        craftingService = new CraftingService(new RecipeRepoImpl(itemRegistry), new StationTypeMapLoader().getFactory()); // should be this but blocks.json is not filled yet
        //craftingService = new CraftingService(new RecipeRepoImpl(new ItemRegistry(new BlockFactoryLoader("testBlocks.json").getBlockFactory()), "testRecipes.json"), new StationTypeMapLoader().getFactory());
        craftingActionService = new CraftingActionService(gameState, craftingService);

        server.addListener(new Listener() {
            @Override public void connected(Connection connection) {}
            @Override public void disconnected(Connection connection) {
                PlayerData playerData = connectionIds.get(connection);
                if(playerData == null)return;
                Integer id = playerData.getPlayerId();
                connectionIds.remove(connection);
                inputQueues.remove(connection);
                playerActivator.logoutPlayer(playerRegistry.getPlayer(id));
                System.out.println("Player " + id + " dissconected");
            }
            @Override public void received(Connection connection, Object obj) {
                if (obj instanceof PacketRegister register){
                    PlayerRecord pla;
                    if(playerRegistry.hasPlayer(register.name)){
                        pla = playerRegistry.getPlayer(playerRegistry.getId(register.name));
                    }else{
                        pla = playerRegistry.registerPlayer(register.name);
                    }
                    if(playerActivator.isActive(pla.id())){
                        System.out.println("Trying to log as active user!");
                        connection.sendTCP(new PacketNameTaken());
                        connection.close();
                    }else{
                        System.out.println("Registering player name");
                        PacketRegisterAck ack = new PacketRegisterAck();
                        ack.id = pla.id();
                        connection.sendTCP(ack);
                    }
                } else if (obj instanceof PacketJoin join) {
                    if(playerRegistry.getId(join.name) != join.id){
                        //invalid join packet
                        connection.close();
                    }
                    PlayerRecord pla = playerRegistry.getPlayer(join.id);
                    int id = pla.id();
                    System.out.println("Player " + id + " dodany " + join.name);

                    PlayerData playerState = new PlayerData(connection,gameState,id);
                    ObservableMultisetItemHolder observableMultisetItemHolder = new ObservableMultisetItemHolder(Config.PLAYER_DEFAULT_EQUIPMENT_CAPACITY);
                    observableMultisetItemHolder.addObserver(playerState.getItemHolderObserver());
                    ObservablePhysicalPlayer player = new ObservablePhysicalPlayer(observableMultisetItemHolder, new PlayerWorldInteractor(boxWorld, bodiesToDestroy, actionService, creatureRegistry, projectileRegistry));
                    playerActivator.loginPlayer(player,id);
                    connectionIds.put(connection, playerState);
                    inputQueues.put(connection, new ConcurrentLinkedQueue<>());
                } else if (obj instanceof PacketPlayer) {
                    inputQueues.getOrDefault(connection, new ConcurrentLinkedQueue<>()).add((PacketPlayer) obj);
                }
            }
        });

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            handleInput();
            botRegistry.update();
            handlePhysics();
            for(var body : bodiesToDestroy){
                boxWorld.destroyBody(body);
            }
            bodiesToDestroy.clear();
            playerActivator.respawnPlayers();
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
//                System.out.println(in);
                if(in.getPlayerId() != playerId) {
                    continue;
                }
                Creature playerCreature = gameState.activePlayers().get(playerId).creature();
                if(in instanceof PacketPlayerMove move) {
                    playerCreature.move(move.direction);
                    if (move.jump) playerCreature.jump();
                }
                if(in instanceof PacketPlayerHit hit)
                {
                    Vector2 pos = new Vector2(hit.x,hit.y);
                    playerCreature.normalAction(pos);
//                    System.out.println("Player " + playerId + " hit at " + pos.x + " "+ pos.y);
//                    actionService.hitAt(gameState.activePlayers().get(playerId),pos);
                }
                if(in instanceof PacketPlayerTouch touch)
                {
                    Vector2 pos = new Vector2(touch.x,touch.y);
                    playerCreature.specialAction(pos);
//                    System.out.println("Player " + playerId + " hit at " + pos.x + " "+ pos.y);
//                    actionService.placeHeldAt(gameState.activePlayers().get(playerId),pos);
                }
                if(in instanceof PacketPlayerTakeItem take)
                {
                    PhysicalPlayer player = gameState.activePlayers().get(take.playerId);
                    if(player != null)
                        player.setHeldItem(itemRegistry.create(take.itemId));
                }
                if(in instanceof PacketCraftItems craft)
                {
                    //System.out.println(craft.playerId);
                    PhysicalPlayer player = gameState.activePlayers().get(craft.playerId);
                    //System.out.println(player.id());
                    if (player != null) {
                        Recipe recipe = craftingService.getById(craft.craftingId);
                        //System.out.println(recipe.recipeId());
                        if (recipe != null && craftingActionService.craft(player, recipe)) {
                            System.out.println("Player " + craft.playerId + " crafting " + craft.craftingId);
                        } else {
                            System.out.println("Player " + craft.playerId + " failed to craft " + craft.craftingId);
                        }
                        player.actualizeHeldItem();
                    }
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
