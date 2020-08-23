package brews.controllers;

import brews.domain.Recipe;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeUploadException;
import brews.handler.BrewsControllerExceptionHandler;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.mapper.domain.RecipeMapper;
import brews.services.ImportRecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UploadRecipeControllerTest {

    @Mock
    ImportRecipeService importRecipeService;
    @Mock
    BeerXMLRecipeMapper beerXMLRecipeMapper;
    @Mock
    RecipeMapper recipeMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        UploadRecipeController uploadRecipeController = new UploadRecipeController(importRecipeService, beerXMLRecipeMapper, recipeMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(uploadRecipeController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void canUploadRecipe() throws Exception {

        // Given
        String user = "joe";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "domain.xml",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipeDtos = new ArrayList<>();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("Recipe");
        recipeDtos.add(recipeDto);

        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");
        recipes.add(recipe);

        when(importRecipeService.importRecipes(anyList(), anyString())).thenReturn(recipes);
        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(recipeDtos);
        doNothing().when(beerXMLRecipeMapper.map(any(ImportedRecipe.class)));

        MockHttpServletRequestBuilder builder =
          MockMvcRequestBuilders.multipart("/api/recipes/upload")
            .file(mockMultipartFile)
            .param("user",user);

        // When
        mockMvc.perform(builder)
               .andExpect(status().isCreated());

        // Then
        verify(importRecipeService, times(1)).importRecipes(anyList(), anyString());
    }


    @Test
    public void expectBadRequestWhenNotBeerXML() throws Exception {

        // Given
        String user = "joe";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "domain.txt",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        MockHttpServletRequestBuilder builder =
          MockMvcRequestBuilders.multipart("/api/recipes/upload?user=joe")
            .file(mockMultipartFile)
            .param("user",user);

        // When
        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(0)).importRecipes(anyList(), anyString());
    }


    @Test
    public void expectBadRequestWhenUploadingEmptyRecipe() throws Exception {

        // Given
        String user = "joe";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "",
                "text/plain", "".getBytes());
        List<RecipeDto> recipeDtos = new ArrayList<>();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("Recipe");
        recipeDtos.add(recipeDto);

        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");
        recipes.add(recipe);


        when(importRecipeService.importRecipes((anyList()), anyString())).thenReturn(recipes);
        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(recipeDtos);
        doNothing().when(beerXMLRecipeMapper.map(any(ImportedRecipe.class)));

        MockHttpServletRequestBuilder builder =
          MockMvcRequestBuilders.multipart("/api/recipes/upload")
            .file(mockMultipartFile)
            .param("user",user);;

        // When
        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(0)).importRecipes(anyList(), anyString());

    }

    @Test
    public void expectBadRequestWhenRecipeCannotBeParsed() throws Exception {

        // Given
        String user = "joe";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "myrecipe.xml",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipeDtos = new ArrayList<>();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);
        recipeDto.setName("Recipe");
        recipeDtos.add(recipeDto);

        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");
        recipes.add(recipe);

        when(importRecipeService.importRecipes((anyList()), anyString())).thenReturn(recipes);
        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(recipeDtos);
        doNothing().when(beerXMLRecipeMapper.map(any(ImportedRecipe.class)));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/recipes/upload")
          .file(mockMultipartFile)
          .param("user",user);

        // When
        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(1)).importRecipes(anyList(), anyString());
    }

}
