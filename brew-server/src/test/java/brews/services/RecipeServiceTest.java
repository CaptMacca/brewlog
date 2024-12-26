package brews.services;

import brews.app.presentation.dto.recipe.RecipeDto;
import brews.domain.Brew;
import brews.domain.Recipe;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.services.exceptions.RecipeEntityNotFoundException;
import brews.services.impl.RecipeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    BrewsRepository brewsRepository;

    RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, brewsRepository);
    }

    @Test
    public void givenRecipesGetAllRecipesSucceeds() {
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
    public void givenRecipesAndValidUserGetAllRecipesForUserSucceeds() {
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
    public void givenRecipesGetTop5RecipesSucceeds() {
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
        verify(recipeRepository).findTop5RatedByUserUsernameOrderByNameDesc(anyString());
    }

    @Test
    public void givenRecipeGetRecipeByIdSucceeds() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("a recipe");

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        // When
        Recipe test = recipeService.getRecipeById(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(recipeRepository).findById(anyLong());
    }

    @Test()
    public void givenUnknownRecipeGetRecipeThrowsRecipeNotFoundException() {
        Assertions.assertThrows(RecipeEntityNotFoundException.class, () -> {
            // Given
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setId(1L);
            recipeDto.setName("a recipe");

            Recipe recipe = new Recipe();
            recipe.setId(recipeDto.getId());
            recipe.setName(recipeDto.getName());

            when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

            // When
            recipeService.getRecipeById(1L);

            // Then
            verify(recipeRepository).findById(anyLong());
        });

    }

    @Test
    public void givenRecipeGetNotesSucceeds() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1l);
        recipe.setName("recipe");
        recipe.setNotes("recipe notes");

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        // When
        String recipeNotes = recipeService.getNotesForRecipe(anyLong());

        // Then
        verify(recipeRepository).findById(anyLong());
        assertThat(recipeNotes).isEqualTo("recipe notes");
    }

    @Test
    public void givenRecipeUpdateRecipeSucceeds() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("a recipe");

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.saveAndFlush(any())).thenReturn(recipe);

        // When
        Recipe test = recipeService.updateRecipe(1L, recipe);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).saveAndFlush(any(Recipe.class));
    }

    @Test()
    public void givenUnknownRecipeGetRecipeByIdThrowsRecipeNotFoundException() {

        Assertions.assertThrows(RecipeEntityNotFoundException.class, () -> {
            // Given
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setId(1L);
            recipeDto.setName("a recipe");

            Recipe recipe = new Recipe();
            recipe.setId(recipeDto.getId());
            recipe.setName(recipeDto.getName());

            when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

            // When
            recipeService.updateRecipe(1L, recipe);

            // Then
            verify(recipeRepository, times(1)).findById(anyLong());
            verify(recipeRepository, times(0)).saveAndFlush(any(Recipe.class));
        });
    }

    @Test
    public void givenRecipeDeleteRecipeSucceeds() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        // When
        recipeService.deleteRecipe(1L);

        // Then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).delete(any(Recipe.class));

    }

    @Test()
    public void givenRecipeWithNoBrewsDeleteSucceeds() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(brewsRepository.findBrewsByRecipe(any(Recipe.class))).thenReturn(new ArrayList<Brew>());

        // When
        recipeService.deleteRecipe(1L);

        // Then
        verify(recipeRepository).findById(anyLong());
        verify(brewsRepository).findBrewsByRecipe(any(Recipe.class));
        verify(recipeRepository).delete(any(Recipe.class));
        verify(brewsRepository, times(0)).deleteAll(anyList());
    }

    @Test()
    public void givenRecipeWithBrewsDeleteSucceedsAndAllBrewsDeleted() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setRecipe(recipe);

        Set<Brew> brews = new HashSet<>();
        brews.add(brew);

        recipe.setBrews(brews);

    }

    @Test()
    public void givenRecipeUpdateRatingSucceeds() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("a recipe");
        recipe.setRating((short) 2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.saveAndFlush(any())).thenReturn(recipe);

        // When
        Recipe test = recipeService.updateRating(1L, (short) 5.0);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        assertThat(test.getRating()).isEqualTo((short) 5.0);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).saveAndFlush(any(Recipe.class));
    }

}