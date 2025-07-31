package io.github.terraria.logic;

import java.util.List;

public interface PlayersContainer {
    public void addPlayer(Player player);
    public Player getPlayer(int id);
    public List<Player> getList();
    // Potrzebna też jakaś osobno możliwość, żeby dostać gracza/moba w danym kordynacie.
}
