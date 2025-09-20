package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.utils.IntVector2;

public class Creature {
    private CreatureBody body;
    private Movement movement;
    private Tool tool;
    private Health health;

    public Creature(CreatureBody body, Movement movement, Tool tool, Health health){
        this.body = body;
        this.movement = movement;
        this.tool = tool;
        this.health = health;
        this.movement.bind(body);
        this.body.bindCreature(this);
        this.health.bindCreature(this);
    }

    public void move(IntVector2 direction){
        movement.move(direction);
    }
    public void jump(){movement.jump();}
    public boolean liesOn(Vector2 desired){return body.liesOn(desired);}

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
        return body.getPosition();
    }
    public void destroy(){

    }
}
