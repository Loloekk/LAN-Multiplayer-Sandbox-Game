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
    private static ItemRegistry itemRegistry;

    @BeforeAll
    static void setup() {
        BlockFactory blockFactory = new BlockFactoryLoader("testBlocks.json").getBlockFactory();
        itemRegistry = new ItemRegistry(blockFactory);
    }

    @Test
    void constructorTest() {
        RecipeRepoImpl recipeRepo = new RecipeRepoImpl(itemRegistry);
        assertNotNull(recipeRepo);
    }

    @Test
    void getAllRecipeTest() {
        RecipeRepoImpl recipeRepo = new RecipeRepoImpl(itemRegistry);
        List<Recipe> recipes = recipeRepo.getAll();
        assertFalse(recipes.isEmpty());
        assertEquals("Stone", recipes.get(0).getOutput().getItem().type().name());
    }

    @Test
    void getByIdRecipeTest() {
        RecipeRepoImpl recipeRepo = new RecipeRepoImpl(itemRegistry);
        Recipe recipe = recipeRepo.getById(1);
        assertEquals("Stone", recipe.getOutput().getItem().type().name());
        assertNull(recipeRepo.getById(999));
    }

    @Test
    void findByStationTest() {
        RecipeRepoImpl recipeRepo = new RecipeRepoImpl(itemRegistry);
        List<Recipe> recipes = recipeRepo.findByStation(StationType.WORKBENCH);
        assertEquals("Stone", recipes.get(0).getOutput().getItem().type().name());
    }
}
