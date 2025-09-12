package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.MultisetItemHolder;

// The player part exported to PlayerRegistry (in between logging)
// should be a record for easy serialization.
// It may (and probably should as fields of active players may not be final)
// be exported through a custom method.
public record PlayerRecord(int id, ItemHolder equipment, Vector2 spawn) {
    private static final int defaultEquipmentCap = 50;
    public PlayerRecord(int id, Vector2 spawn) {
        this(id, new MultisetItemHolder(defaultEquipmentCap), spawn);
    }
}
