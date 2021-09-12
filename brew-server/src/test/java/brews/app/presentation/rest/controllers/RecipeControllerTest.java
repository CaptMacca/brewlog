package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.recipe.RecipeDto;
import brews.app.presentation.rest.exceptionhandler.BrewsControllerExceptionHandler;
import brews.domain.Recipe;
import brews.domain.exceptions.BrewsEntityNotFoundException;
import brews.domain.mapper.RecipeMapper;
import brews.services.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Steve on 3/07/2017.
 */
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    RecipeMapper recipeMapper;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        RecipeController recipeController = new RecipeController(recipeService,recipeMapper);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        // Given
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("mock");
        recipes.add(recipe);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        // When
        mockMvc.perform(get("/api/recipes/all"))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    public void testGetAllRecipesFatal() throws Exception {
        // Given
        when(recipeService.getAllRecipes()).thenThrow(new NullPointerException());

        // When
        mockMvc.perform(get("/api/recipes/all"))
                .andExpect(status().is5xxServerError());

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    public void testGetAllRecipesForUser() throws Exception {
        // Given
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("mock");
        recipes.add(recipe);

        List<RecipeDto> recipeDtos = new ArrayList<>();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("mock");
        recipeDtos.add(recipeDto);

        when(recipeService.getAllRecipesForUser(anyString())).thenReturn(recipes);
        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(recipeDtos);

        // When
        mockMvc.perform(get("/api/recipes")
                .param("username","anyuser"))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).getAllRecipesForUser(anyString());

    }

    @Test
    public void testGetRecipe() throws Exception {

        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("mock");


        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("mock");

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        when(recipeMapper.toRecipeDto(any(Recipe.class))).thenReturn(recipeDto);

        // When
        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).getRecipeById(anyLong());
    }

    @Test
    public void testNotFoundGetRecipe() throws Exception {
        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("mock");

        when(recipeService.getRecipeById(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isNotFound());

        verify(recipeService, times(1)).getRecipeById(anyLong());
    }


    @Test
    public void testUpdateRecipe() throws Exception {
        // Given
        Long id = 1L;
        String recipeName = "mock";

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(id);
        recipeDto.setName(recipeName);

        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(recipeName);

        when(recipeService.updateRecipe(anyLong(), any(Recipe.class))).thenReturn(recipe);
        when(recipeMapper.toRecipeDto(any(Recipe.class))).thenReturn(recipeDto);
        when(recipeMapper.toRecipe(any(RecipeDto.class))).thenReturn(recipe);

        // When
        mockMvc.perform(put("/api/recipes/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("mock")));

        verify(recipeService, times(1)).updateRecipe(anyLong(), any(Recipe.class));
    }

    @Test
    public void testUpdateNotFoundRecipe() throws Exception {
        // Given
        Long id = 1L;
        String name = "mock";

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(id);
        recipeDto.setName(name);

        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);

        ObjectMapper objectMapper = new ObjectMapper();

        when(recipeService.updateRecipe(anyLong(), any(Recipe.class))).thenThrow(new BrewsEntityNotFoundException());
        when(recipeMapper.toRecipeDto(any(Recipe.class))).thenReturn(recipeDto);
        when(recipeMapper.toRecipe(any(RecipeDto.class))).thenReturn(recipe);

        // When
        mockMvc.perform(put("/api/recipes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isNotFound());

        //Then
        verify(recipeService, times(1)).updateRecipe(anyLong(), any(Recipe.class));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        // Given

        //When
        mockMvc.perform(delete("/api/recipes/1")).andExpect(status().isNoContent());

        //Then
        verify(recipeService, times(1)).deleteRecipe(anyLong());
    }

    @Test
    public void testDeleteNotFoundRecipe() throws Exception {
        // Given
        doThrow(new BrewsEntityNotFoundException()).when(recipeService).deleteRecipe(anyLong());

        //When
        mockMvc.perform(delete("/api/recipes/1")).andExpect(status().isNotFound());

        //Then
        verify(recipeService, times(1)).deleteRecipe(anyLong());
    }

}
