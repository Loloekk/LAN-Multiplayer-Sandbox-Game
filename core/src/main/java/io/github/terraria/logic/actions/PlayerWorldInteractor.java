package io.github.terraria.logic.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureRegistry;
import io.github.terraria.logic.creatures.projectiles.Projectile;
import io.github.terraria.logic.creatures.projectiles.ProjectileRegistry;
import io.github.terraria.logic.creatures.projectiles.ProjectileType;
import io.github.terraria.logic.creatures.tools.WorldInteractor;
import io.github.terraria.logic.players.PhysicalPlayer;

import java.util.List;

public class PlayerWorldInteractor implements WorldInteractor {
    private final World world;
    private final List<Body> bodiesToDestroy;
    private final PlayerActionService actionService;
    private final CreatureRegistry creatureRegistry;
    private final ProjectileRegistry projectileRegistry;
    private PhysicalPlayer player = null;

    public PlayerWorldInteractor(World world, List<Body> bodiesToDestroy, PlayerActionService actionService, CreatureRegistry creatureRegistry, ProjectileRegistry projectileRegistry){
        this.world = world;
        this.bodiesToDestroy = bodiesToDestroy;
        this.actionService = actionService;
        this.creatureRegistry = creatureRegistry;
        this.projectileRegistry = projectileRegistry;
    }

    public void bindPlayer(PhysicalPlayer player){
        this.player = player;
    }
    @Override
    public void damageBlockAt(Vector2 loc, int force) {
        if(player != null)actionService.hitAt(player, loc, force);
    }

    @Override
    public void placeHeldBlockAt(Vector2 loc) {
        if(player != null)actionService.placeHeldAt(player, loc);
    }

    @Override
    public Creature getCreatureAt(Vector2 loc, Creature ignored) {
        return creatureRegistry.getCreatureAt(loc, ignored);
    }

    @Override
    public List<Creature> getCreaturesAt(Vector2 loc, Creature ignore) {
        return creatureRegistry.getCreaturesAt(loc, ignore);
    }

    @Override
    public Creature getPlayerAt(Vector2 loc) {
        return creatureRegistry.getPlayerAt(loc);
    }

    @Override
    public List<Creature> getPlayersAt(Vector2 loc) {
        return creatureRegistry.getPlayersAt(loc);
    }

    @Override
    public void fireProjectile(ProjectileType type, Vector2 pos, Vector2 dir) {
        Projectile projectile = type.fire(projectileRegistry.getProjectileId(), world, bodiesToDestroy, pos, dir);
        projectileRegistry.addProjectile(projectile);
    }
}
