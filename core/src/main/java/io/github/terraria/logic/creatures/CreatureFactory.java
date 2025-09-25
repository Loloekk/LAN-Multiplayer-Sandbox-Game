package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.terraria.common.Config;
import io.github.terraria.logic.actions.MobWorldInteractor;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.creatures.movements.WalkingMovement;
import io.github.terraria.logic.creatures.tools.MeleeWeapon;
import io.github.terraria.logic.creatures.tools.NoTool;
import io.github.terraria.logic.creatures.tools.PlayerTool;

import java.util.List;

public class CreatureFactory {
    private final World world;
    private final List<Body> bodiesToDestroy;
    private final MobWorldInteractor mobWorldInteractor;
    private int nextCreatureId = 0;
    public CreatureFactory(World world, List<Body> bodiesToDestroy, MobWorldInteractor mobWorldInteractor){
        this.world = world;
        this.bodiesToDestroy = bodiesToDestroy;
        this.mobWorldInteractor = mobWorldInteractor;
    }
    public Creature createZombieCreature(Vector2 position){
        BasicCreatureBody zombieBody = new BasicCreatureBody(world, bodiesToDestroy, position, 0.8f, 1.6f, 2.0f, 1.3f, 0.1f);
        WalkingMovement movement = new WalkingMovement(Config.MAX_PLAYER_VELOCITY_X, Config.MOVE_IMPULSE_X, Config.PLAYER_JUMP_STRENGTH);
        MeleeWeapon weapon = new MeleeWeapon(mobWorldInteractor, new Damage(10.0f), 3.0f);
        BasicHealth health = new BasicHealth(100.0f);
        return new Creature(nextCreatureId++,1, zombieBody, movement, weapon, health);
    }

    public Creature createPlayerCreature(Vector2 position, PlayerWorldInteractor playerWorldInteractor){
        BasicCreatureBody playerBody = new BasicCreatureBody(world, bodiesToDestroy, position, Config.PLAYER_WIDTH,
            Config.PLAYER_HEIGHT, Config.PLAYER_DENSITY, Config.PLAYER_FRICTION, Config.PLAYER_RESTITUTION);
        WalkingMovement movement = new WalkingMovement(Config.MAX_PLAYER_VELOCITY_X, Config.MOVE_IMPULSE_X, Config.PLAYER_JUMP_STRENGTH);
        PlayerTool tool = new PlayerTool(playerWorldInteractor, new NoTool());
        BasicHealth health = new BasicHealth(100.0f);
        return new Creature(nextCreatureId++,0, playerBody, movement, tool, health);
    }
}
