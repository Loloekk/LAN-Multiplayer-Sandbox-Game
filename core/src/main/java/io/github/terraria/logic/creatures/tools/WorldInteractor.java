package io.github.terraria.logic.creatures.tools;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.Creature;

import java.util.List;

public interface WorldInteractor {

    void damageBlockAt(Vector2 loc, int force);
    void placeHeldBlockAt(Vector2 loc);
    Creature getCreatureAt(Vector2 loc, Creature ignored);
    List<Creature> getCreaturesAt(Vector2 loc, Creature ignored);
    Creature getPlayerAt(Vector2 loc);
    List<Creature> getPlayersAt(Vector2 loc);
}
