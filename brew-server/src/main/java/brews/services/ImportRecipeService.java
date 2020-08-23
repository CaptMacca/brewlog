package brews.services;

import brews.domain.Recipe;

import java.util.List;

public interface ImportRecipeService {
    List<Recipe> importRecipes(List<Recipe> recipes, String username);
}

