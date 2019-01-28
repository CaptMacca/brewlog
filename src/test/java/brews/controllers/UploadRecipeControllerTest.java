package brews.controllers;

import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeUploadException;
import brews.handler.BrewsControllerExceptionHandler;
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

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        UploadRecipeController uploadRecipeController = new UploadRecipeController(importRecipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(uploadRecipeController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void canUploadRecipe() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "domain.xml",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        when(importRecipeService.importBeerXml(any(InputStream.class), anyString())).thenReturn(recipes);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/recipes/upload?user=joe")
                .file(mockMultipartFile);

        // When
        mockMvc.perform(builder)
               .andExpect(status().isCreated());

        // Then
        verify(importRecipeService, times(1)).importBeerXml(any(InputStream.class), anyString());
    }


    @Test
    public void expectBadRequestWhenNotBeerXML() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "domain.txt",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/recipes/upload?user=joe")
                .file(mockMultipartFile);

        // When
        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(0)).importBeerXml(any(InputStream.class), anyString());
    }


    @Test
    public void expectBadRequestWhenUploadingEmptyRecipe() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "",
                "text/plain", "".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        when(importRecipeService.importBeerXml(any(InputStream.class), anyString())).thenReturn(recipes);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/recipes/upload?user=joe")
                .file(mockMultipartFile);

        // When
        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(0)).importBeerXml(any(InputStream.class), anyString());

    }

    @Test
    public void expectBadRequestWhenRecipeCannotBeParsed() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "myrecipe.xml",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        when(importRecipeService.importBeerXml(any(InputStream.class), anyString())).thenThrow(new ImportedRecipeUploadException());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/recipes/upload?user=joe")
                .file(mockMultipartFile);

        // When
        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(1)).importBeerXml(any(InputStream.class), anyString());
    }

}
