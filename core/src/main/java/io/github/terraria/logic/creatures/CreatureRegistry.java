package io.github.sandboxGame.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.sandboxGame.common.Config;
import io.github.sandboxGame.logic.actions.MobWorldInteractor;
import io.github.sandboxGame.logic.actions.PlayerWorldInteractor;
import io.github.sandboxGame.logic.creatures.movements.WalkingMovement;
import io.github.sandboxGame.logic.creatures.projectiles.BasicProjectileType;
import io.github.sandboxGame.logic.creatures.projectiles.ProjectileRegistry;
import io.github.sandboxGame.logic.creatures.projectiles.ProjectileType;
import io.github.sandboxGame.logic.creatures.tools.NoTool;
import io.github.sandboxGame.logic.creatures.tools.PlayerTool;
import io.github.sandboxGame.logic.creatures.tools.RangeWeapon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CreatureRegistry {
    private final com.badlogic.gdx.physics.box2d.World world;
    private final List<Body> bodiesToDestroy;
    private final HashSet<Creature> mobs = new HashSet<>();
    private final HashSet<Integer> aliveMobs = new HashSet<>();
    private final HashSet<Creature> players = new HashSet<>();
    private final MobWorldInteractor mobWorldInteractor;
    private int nextId = 0;

    public CreatureRegistry(com.badlogic.gdx.physics.box2d.World world, List<Body> bodiesToDestroy, ProjectileRegistry projectileRegistry){
        this.world = world;
        this.bodiesToDestroy = bodiesToDestroy;
        mobWorldInteractor = new MobWorldInteractor(world, bodiesToDestroy, this, projectileRegistry);
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
    public Creature getClosestPlayer(Vector2 loc){
        Creature res = null;
        float minDist = Float.POSITIVE_INFINITY;
        for(var player : players){
            float dist = player.getPosition().dst2(loc);
            if(dist < minDist){
                dist = minDist;
                res = player;
            }
        }
        return res;
    }

    public List<Creature> aliveMobs(){
        return new ArrayList<>(mobs);
    }
    public void registerPlayer(Creature player){
        players.add(player);
        player.addDeathEvent(() -> players.remove(player));
    }
    public void registerMob(Creature mob){
        mobs.add(mob);
        aliveMobs.add(mob.id());
        mob.addDeathEvent(() ->{
            mobs.remove(mob);
            aliveMobs.remove(mob.id());
        });
    }
    public void removePlayer(Creature player){
        players.remove(player);
    }
    public void removeMob(Creature mob){
        mobs.remove(mob);
    }

    public boolean isMobAlive(int id) {
        return aliveMobs.contains(id);
    }
    public int mobCnt(){ return aliveMobs.size();}
}
