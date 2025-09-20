package io.github.terraria.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureRegistry;
import io.github.terraria.logic.creatures.tools.WorldInteractor;
import io.github.terraria.logic.players.PhysicalPlayer;

import java.util.List;

public class PlayerWorldInteractor implements WorldInteractor {
    private final PlayerActionService actionService;
    private final CreatureRegistry creatureRegistry;
    private PhysicalPlayer player = null;

    public PlayerWorldInteractor(PlayerActionService actionService, CreatureRegistry creatureRegistry){
        this.actionService = actionService;
        this.creatureRegistry = creatureRegistry;
    }

    public void bindPlayer(PhysicalPlayer player){
        this.player = player;
    }
    @Override
    public void damageBlockAt(Vector2 loc, int force) {
        if(player != null)actionService.hitAt(player, loc, force);
    }

    @Override
    public void placeHeldBlockAt(Vector2 loc) {
        if(player != null)actionService.placeHeldAt(player, loc);
    }

    @Override
    public Creature getCreatureAt(Vector2 loc, Creature ignored) {
        return creatureRegistry.getCreatureAt(loc, ignored);
    }

    @Override
    public List<Creature> getCreaturesAt(Vector2 loc, Creature ignore) {
        return creatureRegistry.getCreaturesAt(loc, ignore);
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
