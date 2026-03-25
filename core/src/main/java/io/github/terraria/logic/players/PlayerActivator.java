package io.github.sandboxGame.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.common.Config;
import io.github.sandboxGame.logic.actions.PlayerWorldInteractor;
import io.github.sandboxGame.logic.creatures.Creature;
import io.github.sandboxGame.logic.creatures.CreatureRegistry;
import io.github.sandboxGame.logic.equipment.Item;
import io.github.sandboxGame.logic.physics.World;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerActivator {
    public static final float MAX_PLAYERS_RADIUS = Config.PLAYER_ACTIVATOR_MAX_PLAYERS_RADIUS; // Z jakimś zapasem dla float błędów.
    private final PlayerRegistry registry;
    protected final World world;
    private final ActivePlayers activePlayers;
    protected final CreatureRegistry creatureRegistry;
    private final List<PhysicalPlayer> respawnQueue = new ArrayList<>();
    public PlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers, CreatureRegistry creatureRegistry) {
        this.registry = registry;
        this.world = world;
        this.activePlayers = activePlayers;
        this.creatureRegistry = creatureRegistry;
    }

    protected abstract Creature getNewPlayerCreature(Vector2 spawnPosition, PlayerWorldInteractor interactor);

    public boolean isActive(int playerId){
        return activePlayers.isActive(playerId);
    }
    private void respawnPlayer(PhysicalPlayer player){
        int playerId = player.id();
        PlayerRecord playerRecord = registry.getPlayer(playerId);
        player.setCreature(getNewPlayerCreature(playerRecord.spawn(), player.getInteractor()));
        player.setId(playerId);
        List<Item> toRemove = new ArrayList<>(player.equipment().browse());
        for(var item : toRemove){
            player.equipment().remove(item);
        }
        player.setHeldItem(null);
        player.creature().addDeathEvent(() -> respawnQueue.add(player));
    }
    // Sprawdzanie haseł poza modelem.
    public void loginPlayer(PhysicalPlayer player, int playerId) {
        PlayerRecord playerRecord = registry.getPlayer(playerId);
        player.setCreature(getNewPlayerCreature(playerRecord.lastPos(), player.getInteractor()));
        player.setId(playerId);
        for(Item item : playerRecord.equipment().browse())
        {
            player.collectItem(item);
        }
        activePlayers.add(player);
        player.creature().addDeathEvent(() -> respawnQueue.add(player));
    }
    public void logoutPlayer(PlayerRecord player) {
        PhysicalPlayer physicalPlayer = activePlayers.remove(player.id());
        PlayerRecord playerRecord = new PlayerRecord(player.id(), player.name(), physicalPlayer.equipment(), player.spawn(), physicalPlayer.getPosition());
        physicalPlayer.creature().destroy(); // ?
        creatureRegistry.removePlayer(physicalPlayer.creature());
        registry.updateRecord(playerRecord.id(), playerRecord);
    }
    public void respawnPlayers(){
        for(var player : respawnQueue)respawnPlayer(player);
        respawnQueue.clear();
    }
}
