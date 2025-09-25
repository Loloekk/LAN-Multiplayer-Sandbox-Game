package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.common.Config;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.creatures.*;
import io.github.terraria.logic.players.PhysicalPlayer;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PlayerActivator;
import io.github.terraria.logic.players.PlayerRegistry;
import io.github.terraria.utils.MathUtils;

public class DefaultPlayerActivator extends PlayerActivator {
    private final PlaneContainer planeContainer;
    private final CreatureFactory creatureFactory;

    public DefaultPlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers, PlaneContainer planeContainer, CreatureRegistry creatureRegistry, CreatureFactory creatureFactory) {
        super(registry, world, activePlayers, creatureRegistry);
        this.planeContainer = planeContainer;
        this.creatureFactory = creatureFactory;
    }

    private static final PlayerFixture playerFixture = new PlayerFixture(Config.PLAYER_WIDTH, Config.PLAYER_HEIGHT,
        Config.PLAYER_DENSITY, Config.PLAYER_FRICTION, Config.PLAYER_RESTITUTION, new Vector2());

    Vector2 shiftToFree(IntVector2 position, int requiredHeight) {
        int y = position.y() - requiredHeight / 2;
        final int topBit = 1 << (requiredHeight - 1);
        final int desiredHash = 2 * topBit - 1;
        int hash = 0;
        while(position.y() <= planeContainer.getTopY()) {
            hash >>= 1;
            if(planeContainer.getPhysicalAt(position.x(), y) == null)
                hash += topBit;
            if(hash == desiredHash)
                return new Vector2(position.x(), (y + 1 + (y + 1 - requiredHeight)) / 2f);
            y++;
        }
        return new Vector2(position.x(), planeContainer.getTopY() + 1 + requiredHeight / 2f);
    }

    @Override
    protected Creature getNewPlayerCreature(Vector2 spawnPosition, PlayerWorldInteractor interactor) {
        spawnPosition = shiftToFree(IntVector2.toInt(spawnPosition), 2);
        Creature playerCreature = creatureFactory.createPlayerCreature(spawnPosition, interactor);
        creatureRegistry.registerPlayer(playerCreature);
        return playerCreature;
    }
}
