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

import java.util.List;

public class MobWorldInteractor implements WorldInteractor {
    private final World world;
    private final List<Body> bodiesToDestroy;
    private final CreatureRegistry creatureRegistry;
    private final ProjectileRegistry projectileRegistry;

    public MobWorldInteractor(World world, List<Body> bodiesToDestroy, CreatureRegistry creatureRegistry, ProjectileRegistry projectileRegistry){
        this.world = world;
        this.bodiesToDestroy = bodiesToDestroy;
        this.creatureRegistry = creatureRegistry;
        this.projectileRegistry = projectileRegistry;
    }
    @Override
    public void damageBlockAt(Vector2 loc, int force) {

    }

    @Override
    public void placeHeldBlockAt(Vector2 loc) {

    }

    @Override
    public Creature getCreatureAt(Vector2 loc, Creature ignored) {
        return creatureRegistry.getCreatureAt(loc, ignored);
    }

    @Override
    public List<Creature> getCreaturesAt(Vector2 loc, Creature ignored) {
        return creatureRegistry.getCreaturesAt(loc, ignored);
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
