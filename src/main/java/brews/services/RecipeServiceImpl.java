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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoMapper recipeDtoMapper;
    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeDtoMapper recipeDtoMapper, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoMapper = recipeDtoMapper;
        this.recipeMapper = recipeMapper;
    }

    @Override
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
    public RecipeDto getRecipeById(Long id) {

        log.debug("Retrieve recipe with id:" + id);
        RecipeDto recipeDto;

        Optional<Recipe> recipe = Optional.of(recipeRepository.findOne(id));

        if (recipe.isPresent()) {
            recipeDto = recipeDtoMapper.map(recipe.get());
        } else {
            String response = "Recipe with id:" + id + "could not be found";
            log.error(response);
            throw new RecipeServiceException(response);
        }

        return recipeDto;
    }

    @Override
    public RecipeDto saveRecipe(RecipeDto recipeDto) {

        log.debug("Saving recipe: " + recipeDto.toString());
        Recipe detachedRecipe = recipeMapper.map(recipeDto);
        Recipe existingRecipe = recipeRepository.findOne(detachedRecipe.getId());

        if (existingRecipe == null) {
            String response = "Recipe with id:" + recipeDto.getId() + "could not be found";
            log.error(response);
            throw new RecipeServiceException(response);
        }

        BeanUtils.copyProperties(detachedRecipe, existingRecipe);
        existingRecipe = recipeRepository.saveAndFlush(existingRecipe);
        return recipeDtoMapper.map(existingRecipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        log.debug("Deleting recipe with id:" + id);
        Recipe recipe = recipeRepository.findOne(id);

        if (recipe == null) {
            String response = "Recipe with id:" + id + "could not be found";
            log.error(response);
            throw new RecipeServiceException(response);
        }

        recipeRepository.delete(recipe);
    }
}
