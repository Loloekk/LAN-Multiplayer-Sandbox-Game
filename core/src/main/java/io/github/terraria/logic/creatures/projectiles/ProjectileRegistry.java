package io.github.terraria.logic.creatures.projectiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectileRegistry {
    private final HashSet<Projectile> projectiles = new HashSet<>();
    private int nextProjectileId = 0;

    public int getProjectileId(){
        return nextProjectileId++;
    }
    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
        projectile.addDestructionEvent(() -> {
            projectiles.remove(projectile);
        });
    }
    public void removeProjectile(Projectile projectile){
        projectiles.remove(projectile);
    }
    public void report(){
        for(var projectile : projectiles){
            System.out.println("Projectile at position " + projectile.getPosition() + " with speed " + projectile.getVelocity());
        }
    }
}
