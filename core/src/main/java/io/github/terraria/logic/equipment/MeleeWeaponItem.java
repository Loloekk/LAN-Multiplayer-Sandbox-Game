package io.github.terraria.logic.equipment;

import io.github.terraria.logic.creatures.Damage;
import io.github.terraria.logic.creatures.Tool;
import io.github.terraria.logic.creatures.tools.MeleeWeapon;
import io.github.terraria.logic.creatures.tools.WorldInteractor;

public record MeleeWeaponItem(Type type) implements Item, ToolItem{
    public record Type(int id, String name, float damage, float range) implements ItemType{}

    @Override
    public Tool getTool(WorldInteractor interactor) {
        return new MeleeWeapon(interactor, new Damage(type.damage), type.range);
    }
}
