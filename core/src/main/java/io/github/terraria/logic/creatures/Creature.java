package io.github.sandboxGame.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.utils.IntVector2;

import java.util.ArrayList;
import java.util.List;

public class Creature {
    private int id;
    private int creatureType;
    private CreatureBody body;
    private Movement movement;
    private Tool tool;
    private Health health;
    private List<WorldEvent> onDeath = new ArrayList<>();

    public Creature(int id, int creatureType, CreatureBody body, Movement movement, Tool tool, Health health){
        this.id = id;
        this.creatureType = creatureType;
        this.body = body;
        this.movement = movement;
        this.tool = tool;
        this.health = health;
        this.movement.bind(body);
        this.body.bindCreature(this);
        this.health.bindCreature(this);
    }
    public int id(){
        return id;
    }
    public int mobType(){return creatureType;}

    public void move(IntVector2 direction){
        movement.move(direction);
    }
    public void jump(){movement.jump();}
    public boolean liesOn(Vector2 desired){return body.liesOn(desired);}
    public boolean isGrounded(){return body.isGrounded();}
    public boolean obstacleLeft(){return body.obstacleLeft();}
    public boolean obstacleRight(){return body.obstacleRight();}

    public void normalAction(Vector2 actionPosition){
        tool.normalAction(this, actionPosition);
    }
    public void specialAction(Vector2 actionPosition){
        tool.specialAction(this, actionPosition);
    }
    public void setTool(Tool tool){
        this.tool = tool;
    }
    public void takeDamage(Damage amount){
        health.takeDamage(amount);
    }
    public Vector2 getPosition(){
        return body.getPosition().cpy();
    }
    public void applyImpulse(Vector2 impulse){
        body.applyLinearImpulse(impulse);
    }
    public void addDeathEvent(WorldEvent event){
        onDeath.add(event);
    }
    public void destroy(){
        body.destroy();
    }
    public void kill(){
        destroy();
        for(var event : onDeath){
            event.trigger();
        }
    }
}
