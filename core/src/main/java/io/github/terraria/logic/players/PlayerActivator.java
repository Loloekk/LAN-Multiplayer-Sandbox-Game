package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.common.Config;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureBody;
import io.github.terraria.logic.equipment.Item;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.World;

public abstract class PlayerActivator {
    public static final float MAX_PLAYERS_RADIUS = Config.PLAYER_ACTIVATOR_MAX_PLAYERS_RADIUS; // Z jakimś zapasem dla float błędów.
    private final PlayerRegistry registry;
    protected final World world;
    private final ActivePlayers activePlayers;
    public PlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers) {
        this.registry = registry;
        this.world = world;
        this.activePlayers = activePlayers;
    }

    protected abstract Creature getNewPlayerCreature(Vector2 spawnPosition, PlayerWorldInteractor interactor);

    // Sprawdzanie haseł poza modelem.
    public void loginPlayer(PhysicalPlayer player, int playerId) {
        PlayerRecord playerRecord = registry.getPlayer(playerId);
        player.setCreature(getNewPlayerCreature(playerRecord.spawn(), player.getInteractor()));
        player.setId(playerId);
        for(Item item : playerRecord.equipment().browse())
        {
            player.collectItem(item);
        }
        activePlayers.add(player);
    }
    public void logoutPlayer(int playersId) {
        PhysicalPlayer physicalPlayer = activePlayers.remove(playersId);
        PlayerRecord playerRecord = new PlayerRecord(physicalPlayer.id(), physicalPlayer.equipment(), physicalPlayer.getPosition());
        physicalPlayer.creature().destroy(); // ?
        registry.updateRecord(playerRecord.id(), playerRecord);
    }
}
