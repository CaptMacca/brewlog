package brews.services.impl;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.exceptions.BrewsEntityNotFoundException;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final BrewsRepository brewsRepository;

    @Override
    @Transactional
    public List<Recipe> getAllRecipes() {
        log.debug("Retrieving all recipes from the database");
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public List<Recipe> getAllRecipesForUser(String username) {
        log.debug("Retrieve recipes for user: {}", username);
        return recipeRepository.findRecipesByUserUsername(username);
    }

    @Override
    @Transactional
    public List<Recipe> getTop5RatedRecipesForUser(String username) {
        log.debug("Retrieve top 5 rated recipes for user: {}", username);
        return recipeRepository.findTop5RatedByUserUsernameOrderByNameDesc(username);
    }

    @Override
    @Transactional
    public Recipe getRecipeById(Long id) {
        log.debug("Retrieve recipe with id: {}", id);
        return
          recipeRepository.findById(id).orElseThrow(
            BrewsEntityNotFoundException::new
          );
    }

    @Override
    @Transactional
    public String getNotesForRecipe(Long id) {
        return getRecipeById(id).getNotes();
    }

    @Override
    @Transactional
    public Recipe updateRecipe(Long id, Recipe recipe) {
        log.debug(String.format("Saving recipe: %s", recipe.toString()));
        Recipe existingRecipe = getRecipeById(id);
        BeanUtils.copyProperties(recipe,existingRecipe);
        return recipeRepository.saveAndFlush(existingRecipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        log.debug(String.format("Deleting recipe with id: %d",id));
        Recipe recipe = getRecipeById(id);

        // Find any associated brews and delete them
        List<Brew> brews = brewsRepository.findBrewsByRecipe(recipe);
        brewsRepository.deleteAll(brews);
        recipeRepository.delete(recipe);
    }

    @Override
    @Transactional
    public Recipe updateRating(Long id, Short rating) {
        log.debug(String.format("Updating recipe id: %d with rating: %d", id, rating));
        Recipe recipe = getRecipeById(id);
        recipe.setRating(rating);
        return recipeRepository.save(recipe);
    }
}
