package io.github.terraria.logic.crafting;

import io.github.terraria.logic.ItemRegistry;
import io.github.terraria.logic.building.BlockFactory;
import io.github.terraria.logic.building.BlockFactoryLoader;
import io.github.terraria.logic.crafting.station.StationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeRepoImplTest {
    private static RecipeRepoImpl recipeRepo;

    @BeforeAll
    static void setup() {
        BlockFactory blockFactory = new BlockFactoryLoader("testBlocks.json").getBlockFactory();
        ItemRegistry itemRegistry = new ItemRegistry(blockFactory);
        recipeRepo = new RecipeRepoImpl(itemRegistry, "/testRecipes.json");
    }

    @Test
    void constructorTest() {
        assertNotNull(recipeRepo);
    }

    @Test
    void getAllRecipeTest() {
        List<Recipe> recipes = recipeRepo.getAll();
        assertFalse(recipes.isEmpty());
        assertEquals("Stone", recipes.get(0).getOutput().getItem().type().name());
    }

    @Test
    void getByIdRecipeTest() {
        Recipe recipe = recipeRepo.getById(1);
        assertEquals("Stone", recipe.getOutput().getItem().type().name());
        assertNull(recipeRepo.getById(999));
    }

    @Test
    void findByStationTest() {
        List<Recipe> recipes = recipeRepo.findByStation(StationType.WORKBENCH);
        assertEquals("Stone", recipes.get(0).getOutput().getItem().type().name());
    }
}
