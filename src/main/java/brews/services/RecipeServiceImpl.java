package brews.services;

import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;
import brews.exceptions.RecipeServiceException;
import brews.mapper.RecipeDtoMapper;
import brews.mapper.RecipeMapper;
import brews.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoMapper recipeDtoMapper;
    private final RecipeMapper recipeMapper;

    private static final String NO_RECIPE_FOUND_MSG = "Recipe with id: %d could not be found";

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeDtoMapper recipeDtoMapper, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoMapper = recipeDtoMapper;
        this.recipeMapper = recipeMapper;
    }

    @Override
    @Transactional
    public List<RecipeDto> getAllRecipes() {
        log.debug("Retrieving all recipes from the database");
        List<RecipeDto> recipeDtos;
        Optional<List<Recipe>> recipes = Optional.of(recipeRepository.findAll());

        if (recipes.isPresent()) {
            recipeDtos = recipeDtoMapper.map(recipes.get());
        } else {
            log.info("No recipes found in the database");
            return new ArrayList<>();
        }

        return recipeDtos;
    }

    @Override
    @Transactional
    public RecipeDto getRecipeById(Long id) {

        log.debug("Retrieve recipe with id:" + id);
        RecipeDto recipeDto;

        Optional<Recipe> recipe = Optional.of(recipeRepository.findOne(id));

        if (recipe.isPresent()) {
            recipeDto = recipeDtoMapper.map(recipe.get());
        } else {
            String response = String.format(NO_RECIPE_FOUND_MSG, id);
            log.error(response);
            throw new RecipeServiceException(response);
        }

        return recipeDto;
    }

    @Override
    @Transactional
    public RecipeDto saveRecipe(RecipeDto recipeDto) {

        log.debug(String.format("Saving recipe: %s", recipeDto.toString()));
        Recipe detachedRecipe = recipeMapper.map(recipeDto);
        Optional<Recipe> existingRecipe = Optional.of(recipeRepository.findOne(detachedRecipe.getId()));

        if (existingRecipe.isPresent()) {
            log.debug("Updating the recipe in the repository");
            Recipe recipeResult = existingRecipe.get();
            BeanUtils.copyProperties(detachedRecipe, recipeResult);
            Recipe updatedRecipe = recipeRepository.saveAndFlush(recipeResult);
            return recipeDtoMapper.map(updatedRecipe);
        } else {
            String response = String.format(NO_RECIPE_FOUND_MSG, + recipeDto.getId());
            log.error(response);
            throw new RecipeServiceException(response);
        }
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        log.debug(String.format("Deleting recipe with id: %d",id));
        Recipe recipe = recipeRepository.findOne(id);

        if (recipe == null) {
            String response = String.format(NO_RECIPE_FOUND_MSG, id);
            log.error(response);
            throw new RecipeServiceException(response);
        }

        recipeRepository.delete(recipe);
    }
}
