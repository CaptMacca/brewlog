package brews.services.impl;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.RecipeMapper;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final BrewsRepository brewsRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, BrewsRepository brewsRepository) {
        this.recipeRepository = recipeRepository;
        this.brewsRepository = brewsRepository;
    }

    @Override
    @Transactional
    public List<Recipe> getAllRecipes() {
        log.debug("Retrieving all recipes from the database");
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes;
    }

    @Override
    @Transactional
    public List<Recipe> getAllRecipesForUser(String username) {
        log.debug("Retrieve recipes for user: {}", username);
        List<Recipe> recipes = recipeRepository.findRecipesByUserUsername(username);
        return recipes;
    }

    @Override
    @Transactional
    public List<Recipe> getTop5RatedRecipesForUser(String username) {
        log.debug("Retrieve top 5 rated recipes for user: {}", username);
        List<Recipe> recipes = recipeRepository.findTop5RatedByUserUsernameOrderByNameDesc(username);
        return recipes;
    }

    @Override
    @Transactional
    public Recipe getRecipeById(Long id) {
        log.debug("Retrieve recipe with id: {}", id);
        Recipe recipe = recipeRepository.getOne(id);

        if (recipe!=null) {
           return recipe;
        } else {
            throw new BrewsEntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public Recipe updateRecipe(Long id, Recipe recipe) {

        log.debug(String.format("Saving recipe: %s", recipe.toString()));
        Recipe existingRecipe = recipeRepository.getOne(id);

        if (existingRecipe!=null) {
            log.debug("Updating the recipe in the repository");
            BeanUtils.copyProperties(recipe,existingRecipe);
            Recipe updatedRecipe = recipeRepository.saveAndFlush(existingRecipe);
            return updatedRecipe;
        } else {
            throw new BrewsEntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        log.debug(String.format("Deleting recipe with id: %d",id));
        Recipe recipe = recipeRepository.getOne(id);

        if (recipe == null) {
            throw new BrewsEntityNotFoundException();
        }

        // Find any associated brews and delete them
        List<Brew> brews = brewsRepository.findBrewsByRecipe(recipe);
        for(Brew brew : brews) {
            brewsRepository.delete(brew);
        }

        recipeRepository.delete(recipe);
    }

    @Override
    @Transactional
    public Recipe updateRating(Long id, Short rating) {
        log.debug(String.format("Updating recipe id: %d with rating: %d", id, rating));
        Recipe recipe = recipeRepository.getOne(id);

        if (recipe == null) {
            throw new BrewsEntityNotFoundException();
        }
        recipe.setRating(rating);
        Recipe updatedRecipe = recipeRepository.save(recipe);

        return updatedRecipe;
    }
}
