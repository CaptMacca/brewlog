package brews.services;

import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    List<Recipe> getAllRecipesForUser(String username);

    List<Recipe> getTop5RatedRecipesForUser(String username);

    Recipe getRecipeById(Long id);

    Recipe updateRecipe(Long id, Recipe recipe);

    Recipe updateRating(Long id, Short rating);

    void deleteRecipe(Long id);

}
