package io.github.terraria.logic.equipment;

import io.github.terraria.logic.creatures.Damage;
import io.github.terraria.logic.creatures.Tool;
import io.github.terraria.logic.creatures.projectiles.BasicProjectileType;
import io.github.terraria.logic.creatures.tools.RangeWeapon;
import io.github.terraria.logic.creatures.tools.WorldInteractor;

public record RangeWeaponItem(Type type) implements Item, ToolItem {
    public record Type(int id, String name, int projectileId, float damage, float speed, float radius, float gravity, float separation) implements ItemType {}
    @Override
    public Tool getTool(WorldInteractor interactor) {
        BasicProjectileType projectileType = new BasicProjectileType(new Damage(type.damage),
            type.speed, type.radius, type.gravity);
        return new RangeWeapon(interactor, projectileType, type.separation);
    }

}
