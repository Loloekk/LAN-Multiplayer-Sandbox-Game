package io.github.terraria.logic.creatures.projectiles;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.WorldEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Projectile {
    private final ProjectileBody body;
    private boolean alive = true;
    private int id;
    private int typeId;
    private List<WorldEvent> afterDestruction = new ArrayList<>();
    public Projectile(int id, int typeId, ProjectileBody body){
        this.id = id;
        this.typeId = typeId;
        this.body = body;
        body.bindProjectile(this);
    }
    public abstract void hitCreature(Creature creature);
    public abstract void hitObstacle();
    protected void destroy(){
        alive = false;
        body.destroy();
        for(var event : afterDestruction){
            event.trigger();
        }
    }
    public void addDestructionEvent(WorldEvent event){
        afterDestruction.add(event);
    }
    public boolean isAlive(){
        return alive;
    }
    public int getId(){
        return id;
    }
    public int getTypeId(){
        return typeId;
    }
    public Vector2 getPosition(){
        return body.getPosition();
    }
    public Vector2 getVelocity() {return body.getVelocity();}
}
