package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.common.Config;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.creatures.movements.FloatingMovement;
import io.github.terraria.logic.creatures.movements.FlyingMovement;
import io.github.terraria.logic.creatures.movements.WalkingMovement;
import io.github.terraria.logic.creatures.tools.NoTool;
import io.github.terraria.logic.creatures.tools.PlayerTool;
import io.github.terraria.logic.players.PhysicalPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CreatureRegistry {
    private final com.badlogic.gdx.physics.box2d.World world;
    private final HashSet<Creature> mobs = new HashSet<>();
    private final HashSet<Creature> players = new HashSet<>();

    public CreatureRegistry(com.badlogic.gdx.physics.box2d.World world){
        this.world = world;
    }

    public List<Creature> getCreaturesAt(Vector2 loc, Creature ignored){
        List<Creature> res = new ArrayList<>();
        for(Creature creature : mobs){
            if(creature == ignored)continue;
            if(creature.liesOn(loc))res.add(creature);
        }
        for(Creature creature : players){
            if(creature == ignored)continue;
            if(creature.liesOn(loc))res.add(creature);
        }
        return res;
    }
    public Creature getCreatureAt(Vector2 loc, Creature ignored){
        List<Creature> creatures = getCreaturesAt(loc, ignored);
        Creature res = null;
        float minDist = Float.POSITIVE_INFINITY;
        for(Creature creature : creatures){
            float dist = creature.getPosition().dst2(loc);
            if(dist < minDist){
                res = creature;
                minDist = dist;
            }
        }
        return res;
    }
    public List<Creature> getPlayersAt(Vector2 loc){
        List<Creature> res = new ArrayList<>();
        for(Creature creature : players){
            if(creature.liesOn(loc))res.add(creature);
        }
        return res;
    }
    public Creature getPlayerAt(Vector2 loc){
        List<Creature> creatures = getPlayersAt(loc);
        Creature res = null;
        float minDist = Float.POSITIVE_INFINITY;
        for(Creature creature : creatures){
            float dist = creature.getPosition().dst2(loc);
            if(dist < minDist){
                res = creature;
                minDist = dist;
            }
        }
        return res;
    }

    public Creature spawnPlayerCreature(Vector2 position, PlayerWorldInteractor interactor){
        BasicCreatureBody playerBody = new BasicCreatureBody(world, position, Config.PLAYER_WIDTH,
            Config.PLAYER_HEIGHT, Config.PLAYER_DENSITY, Config.PLAYER_FRICTION, Config.PLAYER_RESTITUTION);
        WalkingMovement movement = new WalkingMovement(Config.MAX_PLAYER_VELOCITY_X, Config.MOVE_IMPULSE_X, Config.PLAYER_JUMP_STRENGTH);
        PlayerTool tool = new PlayerTool(interactor, new NoTool());
        BasicHealth health = new BasicHealth();
        Creature playerCreature = new Creature(playerBody, movement, tool, health);
        players.add(playerCreature);
        return playerCreature;
    }
}
