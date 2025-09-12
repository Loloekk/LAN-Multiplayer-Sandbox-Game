package io.github.terraria.logic.crafting;

import io.github.terraria.logic.equipment.ItemRegistry;
import io.github.terraria.loading.RecordLoader;
import io.github.terraria.logic.crafting.station.StationType;

import java.util.List;

public class RecipeRepoImpl implements RecipeRepo {
    final private List<Recipe> all;

    public RecipeRepoImpl(ItemRegistry itemRegistry, String path) {
        // using data transfer objects, json takes items by name
        record IngredientDto(String name, int amount) {}
        record RecipeDto(int id, List<IngredientDto> ingredients, IngredientDto output, String station) {}
        List<RecipeDto> recipeDtos = RecordLoader.loadList(path, RecipeDto.class);
        all = recipeDtos.stream().map(dto -> {
            List<Ingredient> ingredients = dto.ingredients().stream()
                .map(i -> new Ingredient(itemRegistry.create(i.name()), i.amount()))
                .toList();
            Ingredient output = new Ingredient(itemRegistry.create(dto.output().name()), dto.output().amount());
            return new Recipe(dto.id(), ingredients, output, StationType.valueOf(dto.station()));
        }).toList();
    }

    public RecipeRepoImpl(ItemRegistry itemRegistry) {
        this(itemRegistry, "recipes.json");
    }
    @Override
    public List<Recipe> getAll() {
        return all;
    }

    @Override
    public Recipe getById(int id) {
        return all.stream().filter(r -> r.recipeId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Recipe> findByStation(StationType station) {
        return all.stream().filter(r -> r.station() == station).toList();
    }
}
