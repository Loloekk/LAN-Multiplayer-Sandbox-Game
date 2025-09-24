package io.github.terraria.logic.creatures.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.terraria.logic.creatures.Damage;

import java.util.List;

public class BasicProjectileType implements ProjectileType{
    private Damage damage;
    private float speed;
    private float radius;
    private float gravity;
    public BasicProjectileType(Damage damage, float speed, float radius, float gravity){
        this.damage = damage;
        this.speed = speed;
        this.radius = radius;
        this.gravity = gravity;
    }
    @Override
    public Projectile fire(int id, World world, List<Body> bodiesToDestroy, Vector2 position, Vector2 direction) {
        System.out.println("Projectile fire " + id + " " + position + " " + direction);
        ProjectileBody body = new ProjectileBody(world, bodiesToDestroy, position, direction.scl(speed), radius, gravity);
        return new BasicProjectile(id, 1, body, damage);
    }
}
