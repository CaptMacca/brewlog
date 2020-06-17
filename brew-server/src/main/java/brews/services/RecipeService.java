package brews.services;

import brews.domain.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    List<RecipeDto> getAllRecipes();

    List<RecipeDto> getAllRecipesForUser(String username);

    List<RecipeDto> getTop5RatedRecipesForUser(String username);

    RecipeDto getRecipeById(Long id);

    RecipeDto updateRecipe(Long id, RecipeDto recipeDto);

    RecipeDto updateRating(Long id, Short rating);

    void deleteRecipe(Long id);

}
