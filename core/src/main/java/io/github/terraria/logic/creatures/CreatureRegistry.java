package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.common.Config;
import io.github.terraria.logic.creatures.movements.WalkingMovement;
import io.github.terraria.logic.creatures.tools.PlayerTool;

import java.util.HashSet;

public class CreatureRegistry {
    private final com.badlogic.gdx.physics.box2d.World world;
    private final HashSet<Creature> mobs = new HashSet<>();
    private final HashSet<Creature> players = new HashSet<>();

    public CreatureRegistry(com.badlogic.gdx.physics.box2d.World world){
        this.world = world;
    }

    public Creature spawnPlayerCreature(Vector2 position){
        BasicCreatureBody playerBody = new BasicCreatureBody(world, position, Config.PLAYER_WIDTH,
            Config.PLAYER_HEIGHT, Config.PLAYER_DENSITY, Config.PLAYER_FRICTION, Config.PLAYER_RESTITUTION);
        WalkingMovement movement = new WalkingMovement(Config.MAX_PLAYER_VELOCITY_X, Config.MOVE_IMPULSE_X, Config.PLAYER_JUMP_STRENGTH);
        PlayerTool tool = new PlayerTool();
        BasicHealth health = new BasicHealth();
        Creature player = new Creature(playerBody, movement, tool, health);
        players.add(player);
        return player;
    }
}
