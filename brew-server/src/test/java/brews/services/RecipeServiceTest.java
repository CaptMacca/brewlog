package brews.services;

import brews.domain.Recipe;
import brews.app.presentation.dto.recipe.RecipeDto;
import brews.domain.exceptions.BrewsEntityNotFoundException;
import brews.infrastructure.data.jpa.repository.BrewsRepository;
import brews.infrastructure.data.jpa.repository.RecipeRepository;
import brews.services.impl.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    BrewsRepository brewsRepository;

    RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, brewsRepository);
    }

    @Test
    public void testGetAllRecipes() {
        // Given
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");
        recipes.add(recipe);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        // When
        List<Recipe> test = recipeService.getAllRecipes();

        // Then
        assertThat(test).isNotEmpty();
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllRecipesForUser() {
        // Given
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");
        recipes.add(recipe);

        String username = "mockuser";

        when(recipeService.getAllRecipesForUser(anyString())).thenReturn(recipes);

        // When
        List<Recipe> test = recipeService.getAllRecipesForUser(username);

        // Then
        assertThat(test).isNotEmpty();
        verify(recipeRepository, times(1)).findRecipesByUserUsername(anyString());
    }

    @Test
    public void testTop5Recipes() {
        // Given
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");
        recipes.add(recipe);

        when(recipeService.getTop5RatedRecipesForUser(anyString())).thenReturn(recipes);

        String username = "mockuser";

        // When
        List<Recipe> test = recipeService.getTop5RatedRecipesForUser(username);

        // Then
        assertThat(test).isNotEmpty();
        verify(recipeRepository, times(1)).findTop5RatedByUserUsernameOrderByNameDesc(anyString());
    }

    @Test
    public void testGetRecipeById() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("a recipe");

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());

        when(recipeRepository.getOne(anyLong())).thenReturn(recipe);

        // When
        Recipe test = recipeService.getRecipeById(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(recipeRepository, times(1)).getOne(anyLong());
    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testGetUnknownRecipeById() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("a recipe");

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());

        when(recipeRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        recipeService.getRecipeById(1L);

        // Then
        verify(recipeRepository, times(1)).getOne(anyLong());
    }

    @Test
    public void testUpdateRecipe() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("a recipe");

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());

        when(recipeRepository.getOne(anyLong())).thenReturn(recipe);
        when(recipeRepository.saveAndFlush(any())).thenReturn(recipe);

        // When
        Recipe test = recipeService.updateRecipe(1L, recipe);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeRepository, times(1)).saveAndFlush(any(Recipe.class));

    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testUpdateUnknownRecipe() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("a recipe");

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());

        when(recipeRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        recipeService.updateRecipe(1L, recipe);

        // Then
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeRepository, times(0)).saveAndFlush(any(Recipe.class));

    }

    @Test
    public void testDeleteRecipe() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");

        when(recipeRepository.getOne(anyLong())).thenReturn(recipe);

        // When
        recipeService.deleteRecipe(1L);

        // Then
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeRepository, times(1)).delete(any(Recipe.class));

    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testDeleteUnknownRecipe() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");

        when(recipeRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        recipeService.deleteRecipe(1L);

        // Then
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeRepository, times(0)).delete(any(Recipe.class));

    }
}