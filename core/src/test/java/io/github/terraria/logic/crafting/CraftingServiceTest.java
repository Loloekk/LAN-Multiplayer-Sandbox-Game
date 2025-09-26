package io.github.terraria.logic.crafting;

import io.github.terraria.logic.crafting.station.CraftingStation;
import io.github.terraria.logic.crafting.station.CraftingStationRegistry;
import io.github.terraria.logic.crafting.station.InventoryStation;
import io.github.terraria.logic.crafting.station.WorkBenchStation;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.equipment.ItemRegistry;
import io.github.terraria.logic.equipment.MultisetItemHolder;
import io.github.terraria.logic.building.BlockFactory;
import io.github.terraria.loading.BlockFactoryLoader;
import io.github.terraria.common.StationType;
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
