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

    public RecipeRepoImpl(ItemRegistry itemRegistry, String path) {
        all = new ArrayList<>();
        JsonLoader.loadJson(path, obj -> {
            int id = obj.get("id").getAsInt();

            List<Ingredient> ingredients = new ArrayList<>();
            for (JsonElement el : obj.getAsJsonArray("ingredients")) {
                JsonObject ingrObj = el.getAsJsonObject();
                String itemName = ingrObj.get("name").getAsString();
                int amount = ingrObj.get("amount").getAsInt();
                Item item = itemRegistry.create(itemName);
                ingredients.add(new Ingredient(item, amount));
            }

            JsonObject outObj = obj.getAsJsonObject("output");
            String outName = outObj.get("name").getAsString();
            int outAmount = outObj.get("amount").getAsInt();
            Ingredient output = new Ingredient(itemRegistry.create(outName), outAmount);

            StationType station = StationType.valueOf(obj.get("station").getAsString());

            all.add(new Recipe(id, ingredients, output, station));
        });
    }

    public RecipeRepoImpl(ItemRegistry itemRegistry) {
        this(itemRegistry, "/recipes.json");
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
