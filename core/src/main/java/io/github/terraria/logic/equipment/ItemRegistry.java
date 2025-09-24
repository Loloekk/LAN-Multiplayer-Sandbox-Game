package io.github.terraria.logic.equipment;

import io.github.terraria.loading.BlockFactoryLoader;
import io.github.terraria.logic.building.BlockFactory;

import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    List<ItemFactory> itemFactories;

    public ItemRegistry(List<ItemFactory> itemFactories) {
        this.itemFactories = itemFactories;
    }

    public ItemRegistry(){
        itemFactories = new ArrayList<>();
        itemFactories.add(new BlockFactoryLoader().getBlockFactory());
        itemFactories.add(new GenericItemFactory<MeleeWeaponItem, MeleeWeaponItem.Type>(
            "meleeWeapons.json", MeleeWeaponItem::new, MeleeWeaponItem.Type.class
        ));
        itemFactories.add(new GenericItemFactory<MiningToolItem, MiningToolItem.Type>(
            "miningTools.json", MiningToolItem::new, MiningToolItem.Type.class
        ));
        itemFactories.add(new GenericItemFactory<RangeWeaponItem, RangeWeaponItem.Type>(
            "rangeWeapons.json", RangeWeaponItem::new, RangeWeaponItem.Type.class
        ));
    }

    public Item create(String name) {
        return itemFactories.stream().filter(x -> x.contains(name))
            .findFirst().get().create(name);
    }

    public Item create(int id) {
        return itemFactories.stream().filter(x -> x.contains(id))
            .findFirst().get().create(id);
    }
}
