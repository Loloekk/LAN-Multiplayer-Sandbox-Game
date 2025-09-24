package io.github.terraria.logic.creatures.tools;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.projectiles.ProjectileType;

import java.util.List;

public interface WorldInteractor {

    boolean damageBlockAt(Vector2 loc, int force);
    void placeHeldBlockAt(Vector2 loc);
    Creature getCreatureAt(Vector2 loc, Creature ignored);
    List<Creature> getCreaturesAt(Vector2 loc, Creature ignored);
    Creature getPlayerAt(Vector2 loc);
    List<Creature> getPlayersAt(Vector2 loc);
    //dir should be normalised
    void fireProjectile(ProjectileType type, Vector2 pos, Vector2 dir);
}
