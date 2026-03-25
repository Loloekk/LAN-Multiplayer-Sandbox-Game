package io.github.sandboxGame.logic.crafting;

import io.github.sandboxGame.logic.crafting.station.CraftingStation;
import io.github.sandboxGame.logic.crafting.station.CraftingStationRegistry;
import io.github.sandboxGame.logic.crafting.station.InventoryStation;
import io.github.sandboxGame.logic.crafting.station.WorkBenchStation;
import io.github.sandboxGame.logic.equipment.ItemHolder;
import io.github.sandboxGame.logic.equipment.ItemRegistry;
import io.github.sandboxGame.logic.equipment.MultisetItemHolder;
import io.github.sandboxGame.logic.building.BlockFactory;
import io.github.sandboxGame.loading.BlockFactoryLoader;
import io.github.sandboxGame.common.StationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CraftingServiceTest {
    private static final int cap = 10;
    private static class DummyItemHolder extends MultisetItemHolder {
        public DummyItemHolder() {
            super(cap);
        }
    }

    private static ItemRegistry itemRegistry;
    private static RecipeRepoImpl recipeRepo;
    private ItemHolder itemHolder;
    private CraftingService service;

    @BeforeAll
    static void setup() {
        itemRegistry = new ItemRegistry();
        recipeRepo = RecipeRepoFactory.fromJson(itemRegistry, "testRecipes.json");
    }

    @BeforeEach
    void setupTest() {
        itemHolder = new DummyItemHolder();
        service = new CraftingService(recipeRepo, null, new CraftingStationRegistry(List.of(new WorkBenchStation(), new InventoryStation())));
    }

    @Test
    void canCraftWhenAvailable() {
        Recipe recipe = recipeRepo.getById(1);
        itemHolder.insert(itemRegistry.create("Stone"), 1);
        assertTrue(service.canCraft(recipe, itemHolder));
    }

    @Test
    void cannotCraftWhenNotAvailable() {
        Recipe recipe = recipeRepo.getById(1);
        assertFalse(service.canCraft(recipe, itemHolder));
    }

    @Test
    void getAvailableRecipes() {
        itemHolder.insert(itemRegistry.create("Stone"), 1);
        List<Recipe> workbenchRecipes = service.getAvailableRecipes(StationType.WORKBENCH, itemHolder);
        assertFalse(workbenchRecipes.isEmpty());
        assertEquals(StationType.WORKBENCH, workbenchRecipes.get(0).station());
    }

    @Test
    void successfulCraft() {
        Recipe recipe = recipeRepo.getById(1);
        itemHolder.insert(itemRegistry.create("Stone"), 1);
        boolean crafted = service.craft(recipe, itemHolder);
        assertTrue(crafted);
        assertEquals(4, itemHolder.browse().count(itemRegistry.create("Stone")));
    }

    @Test
    void failedCraft() {
        Recipe recipe = recipeRepo.getById(1);
        boolean crafted = service.craft(recipe, itemHolder);
        assertFalse(crafted);
        assertEquals(0, itemHolder.browse().count(itemRegistry.create("Stone")));
    }

    @Test
    void craftTwoInput() {
        Recipe recipe = recipeRepo.getById(2);
        itemHolder.insert(itemRegistry.create("Stone"), 1);
        assertFalse(service.canCraft(recipe, itemHolder));
        itemHolder.insert(itemRegistry.create("Dirt"), 1);
        assertTrue(service.canCraft(recipe, itemHolder));
    }

    @Test
    void stationCraft() {
        Recipe recipe = recipeRepo.getById(3);
        itemHolder.insert(itemRegistry.create("Stone"), 1);
        boolean crafted = service.craft(recipe, itemHolder);
        assertTrue(crafted);
        assertEquals(1, itemHolder.browse().count(itemRegistry.create("Anvil")));
    }
}
