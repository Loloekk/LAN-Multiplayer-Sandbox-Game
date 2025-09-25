package io.github.terraria.logic.creatures.bots;

import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureRegistry;
import io.github.terraria.utils.IntVector2;

public class WalkingMeleeBot implements Bot{
    private final Creature creature;
    private final CreatureRegistry creatureRegistry;
    private float sightRange;
    public WalkingMeleeBot(Creature creature, CreatureRegistry creatureRegistry, float sightRange){
        this.creature = creature;
        this.creatureRegistry = creatureRegistry;
        this.sightRange = sightRange;
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
            if(nearPlayer.getPosition().x < creature.getPosition().x){
                goLeft();
            }else{
                goRight();
            }
        }
    }
}
