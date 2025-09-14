package io.github.terraria.common;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();
    public static final int VIEW_CHUNK_DEFAULT_WIDTH;
    public static final int VIEW_CHUNK_DEFAULT_HEIGHT;
    public static final int VIEW_CHUNK_DEFAULT_LAYERS;
    public static final int CHUNK_DEFAULT_WIDTH;
    public static final int CHUNK_DEFAULT_HEIGHT;
    public static final int CHUNK_DEFAULT_LAYERS;
    public static final int PLAYER_CHUNK_WIDTH_RADIUS;
    public static final int PLAYER_CHUNK_HEIGHT_RADIUS;
    public static final long LEFT_CLICK_DELAY;
    public static final String MISSING_TEXTURE_PATH;
    public static final int SCENE_WIDTH;
    public static final int SCENE_HEIGHT;
    public static final int SCENE_LAYERS;
    public static final int TCP_PORT;
    public static final int UDP_PORT;
    public static final int PLAYER_DATA_CHUNK_WIDTH_RADIUS;
    public static final int PLAYER_DATA_CHUNK_HEIGHT_RADIUS;
    public static final float MOVE_IMPULSE_X;
    public static final float MAX_PLAYER_VELOCITY_X;
    public static final float PLAYER_JUMP_STRENGTH;
    public static final int STATIC_PLANE_CONTAINER_DEFAULT_WIDTH;
    public static final int STATIC_PLANE_CONTAINER_DEFAULT_HEIGHT;
    public static final int STATIC_PLANE_CONTAINER_LAYERS;
    public static final float BLOCK_DEFAULT_FRICTION;
    public static final float BLOCK_DEFAULT_RESTITUTION;
    public static final float PLAYER_HEIGHT;
    public static final float PLAYER_WIDTH;
    public static final float PLAYER_DENSITY;
    public static final float PLAYER_FRICTION;
    public static final float PLAYER_RESTITUTION;
    public static final int PLAYER_DEFAULT_EQUIPMENT_CAPACITY;
    public static final float PLAYER_ACTIVATOR_MAX_PLAYERS_RADIUS;
    public static final float PHYSICS_TIME_STEP;
    public static final int PHYSICS_VELOCITY_ITERATIONS;
    public static final int PHYSICS_POSITION_ITERATIONS;

    static {
        try {
            InputStream input = Config.class.getClassLoader().getResourceAsStream("default.properties");
            properties.load(input);
            if(input != null)input.close();
        }
        catch (Throwable e) {
            System.err.println(e);
        }
        VIEW_CHUNK_DEFAULT_WIDTH = Integer.parseInt(properties.getProperty("viewChunk.defaultWidth", "5"));
        VIEW_CHUNK_DEFAULT_HEIGHT = Integer.parseInt(properties.getProperty("viewChunk.defaultHeight", "5"));
        VIEW_CHUNK_DEFAULT_LAYERS = Integer.parseInt(properties.getProperty("viewChunk.defaultLayers", "2"));

        CHUNK_DEFAULT_WIDTH = Integer.parseInt(properties.getProperty("chunk.defaultWidth", "5"));
        CHUNK_DEFAULT_HEIGHT = Integer.parseInt(properties.getProperty("chunk.defaultHeight", "5"));
        CHUNK_DEFAULT_LAYERS = Integer.parseInt(properties.getProperty("chunk.defaultLayers", "2"));

        PLAYER_CHUNK_WIDTH_RADIUS = Integer.parseInt(properties.getProperty("playerChunk.widthRadius", "4"));
        PLAYER_CHUNK_HEIGHT_RADIUS = Integer.parseInt(properties.getProperty("playerChunk.heightRadius", "3"));

        LEFT_CLICK_DELAY = Long.parseLong(properties.getProperty("leftClickDelay", "200"));
        MISSING_TEXTURE_PATH = properties.getProperty("missingTexturePath", "missing.png");

        SCENE_WIDTH = Integer.parseInt(properties.getProperty("scene.width", "30"));
        SCENE_HEIGHT = Integer.parseInt(properties.getProperty("scene.height", "20"));
        SCENE_LAYERS = Integer.parseInt(properties.getProperty("scene.layers", "2"));

        TCP_PORT = Integer.parseInt(properties.getProperty("TCPPort", "54555"));
        UDP_PORT = Integer.parseInt(properties.getProperty("UDPPort", "54777"));

        PLAYER_DATA_CHUNK_WIDTH_RADIUS = Integer.parseInt(properties.getProperty("playerData.chunkWidthRadius", "4"));
        PLAYER_DATA_CHUNK_HEIGHT_RADIUS = Integer.parseInt(properties.getProperty("playerData.chunkHeightRadius", "3"));

        MOVE_IMPULSE_X = Float.parseFloat(properties.getProperty("moveImpulseX", "1f"));
        MAX_PLAYER_VELOCITY_X = Float.parseFloat(properties.getProperty("maxPlayerVelocityX", "5f"));
        PLAYER_JUMP_STRENGTH = Float.parseFloat(properties.getProperty("playerJumpStrength", "20f"));

        STATIC_PLANE_CONTAINER_DEFAULT_WIDTH = Integer.parseInt(properties.getProperty("staticPlaneContainer.defaultWidth", "100"));
        STATIC_PLANE_CONTAINER_DEFAULT_HEIGHT = Integer.parseInt(properties.getProperty("staticPlaneContainer.defaultHeight", "100"));
        STATIC_PLANE_CONTAINER_LAYERS = Integer.parseInt(properties.getProperty("staticPlaneContainer.layers", "2"));

        BLOCK_DEFAULT_FRICTION = Float.parseFloat(properties.getProperty("block.defaultFriction", "0.9f"));
        BLOCK_DEFAULT_RESTITUTION = Float.parseFloat(properties.getProperty("block.defaultRestitution", "0.1f"));

        PLAYER_WIDTH = Float.parseFloat(properties.getProperty("player.width", "0.8f"));
        PLAYER_HEIGHT = Float.parseFloat(properties.getProperty("player.height", "1.8f"));
        PLAYER_DENSITY = Float.parseFloat(properties.getProperty("player.density", "2f"));
        PLAYER_FRICTION = Float.parseFloat(properties.getProperty("player.friction", "1.3f"));
        PLAYER_RESTITUTION = Float.parseFloat(properties.getProperty("player.restitution", "0.1f"));
        PLAYER_DEFAULT_EQUIPMENT_CAPACITY = Integer.parseInt(properties.getProperty("player.defaultEquipmentCapacity", "50"));

        PLAYER_ACTIVATOR_MAX_PLAYERS_RADIUS = Float.parseFloat(properties.getProperty("playerActivator.maxPlayersRadius", "0.99f"));

        PHYSICS_TIME_STEP = Float.parseFloat(properties.getProperty("physics.timeStep", "0.02f"));
        PHYSICS_VELOCITY_ITERATIONS = Integer.parseInt(properties.getProperty("physics.velocityIterations", "8"));
        PHYSICS_POSITION_ITERATIONS = Integer.parseInt(properties.getProperty("physics.positionIterations", "4"));
    }
}
