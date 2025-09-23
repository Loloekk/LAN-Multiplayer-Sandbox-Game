package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.common.Config;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.equipment.MultisetItemHolder;

// The player part exported to PlayerRegistry (in between logging)
// should be a record for easy serialization.
// It may (and probably should as fields of active players may not be final)
// be exported through a custom method.
public record PlayerRecord(int id, String name, ItemHolder equipment, Vector2 spawn, Vector2 lastPos) {
    private static final int defaultEquipmentCap = Config.PLAYER_DEFAULT_EQUIPMENT_CAPACITY;
    public PlayerRecord(int id, String name, Vector2 spawn, Vector2 lastPos) {
        this(id, name, new MultisetItemHolder(defaultEquipmentCap), spawn, lastPos);
    }
}
