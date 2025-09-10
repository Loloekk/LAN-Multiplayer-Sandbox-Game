package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PlayerActivator;
import io.github.terraria.logic.players.PlayerRegistry;
import io.github.terraria.utils.MathUtils;

public class DefaultPlayerActivator extends PlayerActivator {
    private final PlaneContainer planeContainer;
    public DefaultPlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers, PlaneContainer planeContainer) {
        super(registry, world, activePlayers);
        this.planeContainer = planeContainer;
    }

    private static final PlayerFixture playerFixture = new PlayerFixture(0.8f, 1.8f,
        2f, 1.3f, 0.1f, new Vector2());

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
    protected Body getNewPlayerBody(Vector2 spawnPosition) {
        Body body = world.createDynamicBody(shiftToFree(IntVector2.toInt(spawnPosition),
            MathUtils.ceil(PlayerActivator.MAX_PLAYERS_RADIUS * 2)));
        body.addPlayerFixture(playerFixture);
        return body;
    }
}
