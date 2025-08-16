package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

public interface Player {
    int getId();
    void setSpawnPosition(Vector2 spawnPosition);
    Vector2 getSpawnPosition();
    // getEquipment();
    // Czy zarejestrowany gracz powinien mieć ekwipunek?
    // Co, jeśli mielibyśmy kilka map?
    // Wtedy raczej kilka instancji modeli i tak.
    // Klasa PlayerRegister z modelu ma sens
    // dla powrotu do tej samej mapy.

    // getHeldItem();
    // Przyjmujemy, że zachowuje się pomiędzy logowaniami.
    // Przy śmierci ręcznie zerowany ekwipunek oraz trzymany przedmiot.
}
