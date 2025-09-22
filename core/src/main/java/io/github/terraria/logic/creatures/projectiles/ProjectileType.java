package io.github.terraria.logic.creatures.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

public interface ProjectileType {
    Projectile fire(int id, World world, List<Body> bodiesToDestroy, Vector2 position, Vector2 direction);//direction should be normalized
}
