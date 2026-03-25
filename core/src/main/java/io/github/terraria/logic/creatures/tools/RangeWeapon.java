package io.github.sandboxGame.logic.creatures.tools;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.creatures.Creature;
import io.github.sandboxGame.logic.creatures.Tool;
import io.github.sandboxGame.logic.creatures.projectiles.ProjectileType;

public class RangeWeapon implements Tool {
    private final WorldInteractor interactor;
    private final ProjectileType projectileType;
    private final float separation;

    public RangeWeapon(WorldInteractor interactor, ProjectileType projectileType, float separation){
        this.interactor = interactor;
        this.projectileType = projectileType;
        this.separation = separation;
    }
    @Override
    public boolean normalAction(Creature user, Vector2 actionPosition) {
        return false;
    }

    @Override
    public boolean specialAction(Creature user, Vector2 actionPosition) {
        System.out.println("Special action!");
        Vector2 dir = actionPosition.sub(user.getPosition()).nor();
        Vector2 pos = user.getPosition().add(dir.cpy().scl(separation));
        interactor.fireProjectile(projectileType, pos, dir);
        return true;
    }
}
