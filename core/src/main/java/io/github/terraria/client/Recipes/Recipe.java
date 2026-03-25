package io.github.sandboxGame.client.Recipes;


import io.github.sandboxGame.common.StationType;

import java.util.List;

record Recipe(int id, List<Product> ingredients, Product output, StationType station) {}
