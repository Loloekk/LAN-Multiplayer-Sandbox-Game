package io.github.terraria.logic.crafting;

import io.github.terraria.common.StationType;
import io.github.terraria.loading.RecordLoader;
import io.github.terraria.logic.equipment.ItemRegistry;

import java.util.List;

public class RecipeRepoFactory {
    public static RecipeRepoImpl fromJson(ItemRegistry itemRegistry, String path) {
        record IngredientDto(String name, int amount) {}
        record RecipeDto(int id, List<IngredientDto> ingredients, IngredientDto output, String station) {}
        List<RecipeDto> recipeDtos = RecordLoader.loadList(path, RecipeDto.class);
        List<Recipe> recipes = recipeDtos.stream().map(dto -> {
            List<Ingredient> ingredients = dto.ingredients().stream()
                .map(i -> new Ingredient(itemRegistry.create(i.name()), i.amount()))
                .toList();
            Ingredient output = new Ingredient(itemRegistry.create(dto.output().name()), dto.output().amount());
            return new Recipe(dto.id(), ingredients, output, StationType.valueOf(dto.station()));
        }).toList();
        return new RecipeRepoImpl(recipes);
    }
    public static RecipeRepoImpl fromJson(ItemRegistry itemRegistry) {
        return fromJson(itemRegistry, "recipes.json");
    }
}

