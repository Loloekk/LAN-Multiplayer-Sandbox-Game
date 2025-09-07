package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.GameState;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.building.Block;

public class PlayerActionServiceImpl extends PlayerActionService {
    public PlayerActionServiceImpl(GameState gameState) {
        super(gameState);
    }

    @Override
    public void hitAt(PhysicalPlayer physicalPlayer, Vector2 loc) {
        if(!loc.epsilonEquals(physicalPlayer.getPosition(), (float) rangeRadius))
            return;

        {
            Block block = gameState.grid().removeFrontBlockAt(IntVector2.toInt(loc));
            if (block != null) {
                // Dodaj do ekwipunku (albo ustaw na mapie do zebrania).
                return;
            }
        }
        {
            PhysicalPlayer player = gameState.activePlayers().getAt(loc);
            if (player != null) {
                // Rozważ uderzenia gracza.
                return;
            }
        }
    }

    @Override
    public void specialAt(PhysicalPlayer physicalPlayer, Block block, Vector2 loc) {
        if(!loc.epsilonEquals(physicalPlayer.getPosition(), (float) rangeRadius))
            return;

        if(gameState.grid().placeBlockAt(IntVector2.toInt(loc), block)) {
            // Zdejmij block z ekwipunku gracza.
        }
    }
}
