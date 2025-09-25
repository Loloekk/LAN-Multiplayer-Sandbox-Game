package io.github.terraria.logic.creatures.bots;

import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureRegistry;
import io.github.terraria.utils.IntVector2;

public class WalkingMeleeBot implements Bot{
    private final Creature creature;
    private final CreatureRegistry creatureRegistry;
    private float sightRange;
    private float attackRange;
    private int currentAttackTick = 0;
    private int attackDelay;
    public WalkingMeleeBot(Creature creature, CreatureRegistry creatureRegistry, float sightRange, float attackRange, int attackDelay){
        this.creature = creature;
        this.creatureRegistry = creatureRegistry;
        this.sightRange = sightRange;
        this.attackRange = attackRange;
        this.attackDelay = attackDelay;
    }
    void goLeft(){
        if(creature.obstacleLeft())creature.jump();
        else creature.move(new IntVector2(-1, 0));
    }
    void goRight(){
        if(creature.obstacleRight())creature.jump();
        else creature.move(new IntVector2(1, 0));
    }

    @Override
    public Creature getCreature() {
        return creature;
    }

    @Override
    public void think() {
        Creature nearPlayer = creatureRegistry.getClosestPlayer(creature.getPosition());
        if(nearPlayer != null && nearPlayer.getPosition().epsilonEquals(creature.getPosition(), sightRange)){
            if(nearPlayer.getPosition().epsilonEquals(creature.getPosition(), attackRange)){
                currentAttackTick++;
                if(currentAttackTick == attackDelay){
                    currentAttackTick = 0;
                    creature.normalAction(nearPlayer.getPosition());
                }
            }else{
                if(nearPlayer.getPosition().x < creature.getPosition().x){
                    goLeft();
                }else{
                    goRight();
                }
            }
        }
    }
}
