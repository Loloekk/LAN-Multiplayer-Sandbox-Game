package io.github.sandboxGame.logic.equipment;

import io.github.sandboxGame.logic.creatures.Damage;
import io.github.sandboxGame.logic.creatures.Tool;
import io.github.sandboxGame.logic.creatures.tools.MeleeWeapon;
import io.github.sandboxGame.logic.creatures.tools.WorldInteractor;

public record MeleeWeaponItem(Type type) implements Item, ToolItem{
    public record Type(int id, String name, float damage, float range) implements ItemType{}

    @Override
    public Tool getTool(WorldInteractor interactor) {
        return new MeleeWeapon(interactor, new Damage(type.damage), type.range);
    }
}
