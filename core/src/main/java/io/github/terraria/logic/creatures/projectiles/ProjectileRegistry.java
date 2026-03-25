package io.github.sandboxGame.logic.creatures.projectiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectileRegistry {
    private final HashSet<Projectile> projectiles = new HashSet<>();
    private final HashSet<Integer> activeProjectiles = new HashSet<>();
    private int nextProjectileId = 0;

    public int getProjectileId(){
        return nextProjectileId++;
    }
    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
        activeProjectiles.add(projectile.getId());
        projectile.addDestructionEvent(() -> {
            projectiles.remove(projectile);
            activeProjectiles.remove(projectile.getId());
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
    public boolean isProjectileActive(int projectileId){
        return activeProjectiles.contains(projectileId);
    }
    public List<Projectile> activeProjectiles(){
        return new ArrayList<>(projectiles);
    }
}
