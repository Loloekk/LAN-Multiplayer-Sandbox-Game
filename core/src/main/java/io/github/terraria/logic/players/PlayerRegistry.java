package io.github.terraria.logic.players;

import java.util.List;

public interface PlayerRegistry {
    public void registerPlayer(int id);// Imię trzymane na wyższym poziomie?
    public Player getPlayer(int id);
    public List<Player> getList();
}
