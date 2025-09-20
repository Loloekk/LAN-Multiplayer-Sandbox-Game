package io.github.terraria.client.Recipes;


import io.github.terraria.common.StationType;

import java.util.List;

record Recipe(int id, List<Product> ingredients, Product output, StationType station) {}
