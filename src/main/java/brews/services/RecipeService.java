package brews.services;

import brews.domain.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    List<RecipeDto> getAllRecipes();

    RecipeDto getRecipeById(Long id);

    RecipeDto saveRecipe(Long id, RecipeDto recipeDto);

    void deleteRecipe(Long id);
}
