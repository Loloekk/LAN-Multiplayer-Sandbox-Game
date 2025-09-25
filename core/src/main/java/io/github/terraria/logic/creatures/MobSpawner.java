package io.github.terraria.logic.creatures;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.creatures.bots.BotRegistry;
import io.github.terraria.logic.creatures.bots.WalkingMeleeBot;

import java.util.Random;

public class MobSpawner {
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final int delayTicks;
    private final int maxMobCnt;
    private int currentTick;
    private final PlaneContainer planeContainer;
    private final CreatureFactory creatureFactory;
    private final CreatureRegistry creatureRegistry;
    private final BotRegistry botRegistry;
    private final Random rand = new Random();
    public MobSpawner(PlaneContainer planeCOntainer, CreatureFactory creatureFactory, CreatureRegistry creatureRegistry, BotRegistry botRegistry, int minX, int maxX, int minY, int maxY, int maxMobCnt, int delayTicks){
        this.planeContainer = planeCOntainer;
        this.creatureFactory = creatureFactory;
        this.creatureRegistry = creatureRegistry;
        this.botRegistry = botRegistry;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.maxMobCnt = maxMobCnt;
        this.delayTicks = delayTicks;
    }

    private boolean spawnAt(int x, int y){
        if(planeContainer.getPhysicalAt(x, y) == null && planeContainer.getPhysicalAt(x, y+1) == null && planeContainer.getPhysicalAt(x, y-1) != null){
            Creature zombie = creatureFactory.createZombieCreature(new Vector2(x, y + 0.5f));
            creatureRegistry.registerMob(zombie);
            botRegistry.addBot(new WalkingMeleeBot(zombie, creatureRegistry, 10, 2.5f, 30));
            return true;
        }
        return false;
    }
    public void trySpawningMob(){
        currentTick++;
        if(currentTick == delayTicks){
            currentTick = 0;
            if(creatureRegistry.mobCnt() >= maxMobCnt)return;
            int x = minX + rand.nextInt(maxX - minX + 1);
            int y = minY + rand.nextInt(maxY - minY + 1);
            spawnAt(x, y);
        }
    }
}
