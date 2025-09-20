package io.github.terraria.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureRegistry;
import io.github.terraria.logic.creatures.tools.WorldInteractor;

import java.util.List;

public class MobWorldInteractor implements WorldInteractor {
    private final CreatureRegistry creatureRegistry;

    public MobWorldInteractor(CreatureRegistry creatureRegistry){
        this.creatureRegistry = creatureRegistry;
    }
    @Override
    public void damageBlockAt(Vector2 loc, int force) {

    }

    @Override
    public void placeHeldBlockAt(Vector2 loc) {

    }

    @Override
    public Creature getCreatureAt(Vector2 loc, Creature ignored) {
        return creatureRegistry.getCreatureAt(loc, ignored);
    }

    @Override
    public List<Creature> getCreaturesAt(Vector2 loc, Creature ignored) {
        return creatureRegistry.getCreaturesAt(loc, ignored);
    }

    @Override
    public Creature getPlayerAt(Vector2 loc) {
        return creatureRegistry.getPlayerAt(loc);
    }

    @Override
    public List<Creature> getPlayersAt(Vector2 loc) {
        return creatureRegistry.getPlayersAt(loc);
    }
}
