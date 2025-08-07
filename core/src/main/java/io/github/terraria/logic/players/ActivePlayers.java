package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.IntRectangle;

import java.util.List;

public interface ActivePlayers {
    void add(PhysicalPlayer player);
    PhysicalPlayer remove(int playersId);
    PhysicalPlayer get(int playersId);
    List<PhysicalPlayer> getList();
    PhysicalPlayer getAt(Vector2 desired);
    // Docelowo to pewnie może być coś uboższego niż ActivePlayers (pod względem metod i lista zamiast mapy).
    ActivePlayers getLocal(IntRectangle rectangle);
}
