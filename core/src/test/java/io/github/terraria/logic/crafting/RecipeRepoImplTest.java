package io.github.terraria.logic.crafting;

import io.github.terraria.logic.equipment.ItemRegistry;
import io.github.terraria.logic.building.BlockFactory;
import io.github.terraria.loading.BlockFactoryLoader;
import io.github.terraria.common.StationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeRepoImplTest {
    private static RecipeRepoImpl recipeRepo;
    private static ItemRegistry itemRegistry;

    @BeforeAll
    static void setup() {
        itemRegistry = new ItemRegistry();
    }

    @BeforeEach
    void setupTest() {
        recipeRepo = RecipeRepoFactory.fromJson(itemRegistry, "testRecipes.json");
    }

    @Test
    void constructorTest() {
        assertNotNull(recipeRepo);
    }

    @Test
    void getAllRecipeTest() {
        List<Recipe> recipes = recipeRepo.getAll();
        assertFalse(recipes.isEmpty());
        assertEquals("Stone", recipes.get(0).output().getItem().type().name());
    }

    @Test
    void getByIdRecipeTest() {
        Recipe recipe = recipeRepo.getById(1);
        assertEquals("Stone", recipe.output().getItem().type().name());
        assertNull(recipeRepo.getById(999));
    }

    @Test
    void findByStationTest() {
        List<Recipe> recipes = recipeRepo.findByStation(StationType.WORKBENCH);
        assertEquals("Stone", recipes.get(0).output().getItem().type().name());
    }
}
