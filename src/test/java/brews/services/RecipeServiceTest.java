package brews.services;

import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.RecipeMapper;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
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

    @Mock
    RecipeMapper recipeMapper;

    RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, brewsRepository, recipeMapper);
    }

    @Test
    public void testGetAllRecipes() {
        // Given
        List<RecipeDto> recipeDtos = new ArrayList<>();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("a recipe");
        recipeDtos.add(recipeDto);

        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(recipeDtos);

        // When
        List<RecipeDto> test = recipeService.getAllRecipes();

        // Then
        assertThat(test).isNotEmpty();
        verify(recipeMapper, times(1)).toRecipeDtos(anyList());
        verify(recipeRepository, times(1)).findAll();
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

        when(recipeMapper.toRecipeDto(any(Recipe.class))).thenReturn(recipeDto);
        when(recipeRepository.getOne(anyLong())).thenReturn(recipe);

        // When
        RecipeDto test = recipeService.getRecipeById(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeMapper, times(1)).toRecipeDto(any(Recipe.class));
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

        when(recipeMapper.toRecipeDto(any(Recipe.class))).thenReturn(recipeDto);
        when(recipeRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        recipeService.getRecipeById(1L);

        // Then
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeMapper, times(1)).toRecipeDto(any(Recipe.class));
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

        when(recipeMapper.toRecipeDto(any(Recipe.class))).thenReturn(recipeDto);
        when(recipeMapper.toRecipe(any(RecipeDto.class))).thenReturn(recipe);
        when(recipeRepository.getOne(anyLong())).thenReturn(recipe);
        when(recipeRepository.saveAndFlush(any())).thenReturn(recipe);

        // When
        RecipeDto test = recipeService.updateRecipe(1L, recipeDto);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(recipeMapper, times(1)).updateFromRecipeDto(any(RecipeDto.class),any(Recipe.class));
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeRepository, times(1)).saveAndFlush(any(Recipe.class));
        verify(recipeMapper, times(1)).toRecipeDto(any(Recipe.class));

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

        when(recipeMapper.toRecipeDto(any(Recipe.class))).thenReturn(recipeDto);
        when(recipeMapper.toRecipe(any(RecipeDto.class))).thenReturn(recipe);
        when(recipeRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        recipeService.updateRecipe(1L, recipeDto);

        // Then
        verify(recipeMapper, times(1)).toRecipe(any(RecipeDto.class));
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(recipeRepository, times(0)).saveAndFlush(any(Recipe.class));
        verify(recipeMapper, times(0)).toRecipeDto(any(Recipe.class));

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