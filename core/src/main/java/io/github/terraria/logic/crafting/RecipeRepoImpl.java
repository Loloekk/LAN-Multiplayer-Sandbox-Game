package io.github.terraria.logic.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.terraria.logic.Item;
import io.github.terraria.logic.ItemRegistry;
import io.github.terraria.logic.JsonLoader;
import io.github.terraria.logic.crafting.station.StationType;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepoImpl implements RecipeRepo {
    final private List<Recipe> all;
    public RecipeRepoImpl() {
        all = new ArrayList<>();
        ItemRegistry itemRegistry = new ItemRegistry(); // fix
        JsonLoader.loadJson("/recipes.json", obj -> {
            int id = obj.get("id").getAsInt();

            List<Ingredient> ingredients = new ArrayList<>();
            for (JsonElement el : obj.getAsJsonArray("ingredients")) {
                JsonObject ingrObj = el.getAsJsonObject();
                int itemId = ingrObj.get("id").getAsInt();
                int amount = ingrObj.get("amount").getAsInt();
                Item item = itemRegistry.get(itemId);
                ingredients.add(new Ingredient(item, amount));
            }

            JsonObject outObj = obj.getAsJsonObject("output");
            int outId = outObj.get("item").getAsInt();
            int outAmount = outObj.get("amount").getAsInt();
            Ingredient output = new Ingredient(itemRegistry.get(outId), outAmount);

            StationType station = StationType.valueOf(outObj.get("station").getAsString());

            all.add(new Recipe(id, ingredients, output, station));
        });
    }
    @Override
    public List<Recipe> getAll() {
        return all;
    }

    @Override
    public Recipe getById(int id) {
        return all.stream().filter(r -> r.getRecipeId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Recipe> findByStation(StationType station) {
        return all.stream().filter(r -> r.getStation() == station).toList();
    }
}
