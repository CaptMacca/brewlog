package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.RecipeMapper;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
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
    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, BrewsRepository brewsRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.brewsRepository = brewsRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    @Transactional
    public List<RecipeDto> getAllRecipes() {
        log.debug("Retrieving all recipes from the database");
        List<Recipe> recipes = recipeRepository.findAll();

        return recipeMapper.toRecipeDtos(recipes);
    }

    @Override
    @Transactional
    public RecipeDto getRecipeById(Long id) {
        log.debug("Retrieve recipe with id:" + id);
        Recipe recipe = recipeRepository.findOne(id);

        if (recipe!=null) {
           return recipeMapper.toRecipeDto(recipe);
        } else {
            throw new BrewsEntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {

        log.debug(String.format("Saving recipe: %s", recipeDto.toString()));
        Recipe detachedRecipe = recipeMapper.toRecipe(recipeDto);
        Recipe existingRecipe = recipeRepository.findOne(id);

        if (existingRecipe!=null) {
            log.debug("Updating the recipe in the repository");
            BeanUtils.copyProperties(detachedRecipe, existingRecipe);
            Recipe updatedRecipe = recipeRepository.saveAndFlush(existingRecipe);
            return recipeMapper.toRecipeDto(updatedRecipe);
        } else {
            throw new BrewsEntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        log.debug(String.format("Deleting recipe with id: %d",id));
        Recipe recipe = recipeRepository.findOne(id);

        if (recipe == null) {
            throw new BrewsEntityNotFoundException();
        }

        // Find any associated brews and delete them
        List<Brew> brews = brewsRepository.findBrewsByRecipeId(recipe.getId());
        for(Brew brew : brews) {
            brewsRepository.delete(brew.getId());
        }

        recipeRepository.delete(recipe);
    }
}
