package io.github.sandboxGame.client.state;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import io.github.sandboxGame.client.Recipes.RecipeAvailabilityListener;
import io.github.sandboxGame.controler.network.PacketServerToClient.PacketCollectItems;
import io.github.sandboxGame.controler.network.PacketServerToClient.PacketRemoveItems;
import io.github.sandboxGame.loading.BlockFactoryLoader;
import io.github.sandboxGame.loading.StationTypeMapLoader;
import io.github.sandboxGame.logic.building.BlockFactory;
import io.github.sandboxGame.logic.crafting.*;
import io.github.sandboxGame.logic.crafting.station.StationTypeMap;
import io.github.sandboxGame.logic.equipment.Item;
import io.github.sandboxGame.logic.equipment.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ClientEquipment {
    private final Multiset<Integer> set = HashMultiset.create();

    private List<RecipeAvailabilityListener> listeners = new ArrayList<>();

    public void insert(Integer item, int count) {
        set.add(item, count);
    }
    public int getCount(Integer item) { return set.count(item); }
    public void remove(Integer item, int count) {
        set.remove(item, count);
    }
    public Multiset<Integer> browse() {
        return Multisets.unmodifiableMultiset(set);
    }
    public void actualize(Object obj)
    {
        if(obj instanceof PacketCollectItems col)
        {
            insert(col.id,col.count);
        }
        else if(obj instanceof PacketRemoveItems rem)
        {
            remove(rem.id, rem.count);
        }
        updateAvailability();
    }

    public void addRecipeAvailListener(RecipeAvailabilityListener listener) {
        listeners.add(listener);
    }
    public void removeRecipeAvailListener(RecipeAvailabilityListener listener) {
        listeners.remove(listener);
    }
    private void notifyAvailChanged(Recipe recipe, boolean available) {
        for (RecipeAvailabilityListener listener : listeners) {
            listener.onChange(recipe, available);
        }
    }
    public void updateAvailability() {
        RecipeRepoImpl repo = RecipeRepoFactory.fromJson(new ItemRegistry());
        for (Recipe recipe : repo.getAll()) {
            boolean avail = canCraft(recipe);
            notifyAvailChanged(recipe, avail);
        }
    }
    boolean canCraft(Recipe recipe) {
        List<Ingredient> ingredients = recipe.ingredients();
        for (Ingredient ing : ingredients) {
            int id = ing.item().type().id();
            if (set.count(id) < ing.amount()) {
                return false;
            }
        }
        return true;
    }
}
