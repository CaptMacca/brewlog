package brews.controllers;

import brews.domain.dto.RecipeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.handler.BrewsControllerExceptionHandler;
import brews.services.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Steve on 3/07/2017.
 */
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        // Given
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("mock");

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        // When
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    public void testGetRecipe() throws Exception {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("mock");

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipeDto);

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
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(id);
        recipeDto.setName("mock");

        ObjectMapper objectMapper = new ObjectMapper();

        when(recipeService.saveRecipe(anyLong(), anyObject())).thenReturn(recipeDto);

        // When
        mockMvc.perform(put("/api/recipes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).saveRecipe(anyLong(), anyObject());
    }

    @Test
    public void testUpdateNotFoundRecipe() throws Exception {
        // Given
        Long id = 1L;
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(id);
        recipeDto.setName("mock");

        ObjectMapper objectMapper = new ObjectMapper();

        when(recipeService.saveRecipe(anyLong(), anyObject())).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(put("/api/recipes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isNotFound());

        //Then
        verify(recipeService, times(1)).saveRecipe(anyLong(), anyObject());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        // Given
        Long id = 1L;

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
