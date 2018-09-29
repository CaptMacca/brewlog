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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UploadRecipeControllerTest {

    @Mock
    ImportRecipeService importRecipeService;

    UploadRecipeController uploadRecipeController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        uploadRecipeController = new UploadRecipeController(importRecipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(uploadRecipeController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testUploadRecipe() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "domain.xml",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        when(importRecipeService.importBeerXml(anyObject())).thenReturn(recipes);

        // When
        mockMvc.perform(fileUpload("/api/recipes/upload")
                .file(mockMultipartFile))
                .andExpect(status().isCreated());

        // Then
        verify(importRecipeService, times(1)).importBeerXml(anyObject());
    }


    @Test
    public void testUploadRecipeNotXML() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "domain.txt",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        // When
        mockMvc.perform(fileUpload("/api/recipes/upload")
                .file(mockMultipartFile))
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(0)).importBeerXml(anyObject());
    }


    @Test
    public void testUploadRecipeNotFile() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "",
                "text/plain", "".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        when(importRecipeService.importBeerXml(any(InputStream.class))).thenReturn(recipes);

        // When
        mockMvc.perform(fileUpload("/api/recipes/upload")
                .file(mockMultipartFile))
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(0)).importBeerXml(anyObject());

    }

    @Test
    public void testUploadRecipeParseFailureFile() throws Exception {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "myrecipe.xml",
                "text/plain", "domain data".getBytes());
        List<RecipeDto> recipes = new ArrayList<>();
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        when(importRecipeService.importBeerXml(any(InputStream.class))).thenThrow(new ImportedRecipeUploadException());

        // When
        mockMvc.perform(fileUpload("/api/recipes/upload")
                .file(mockMultipartFile))
                .andExpect(status().isBadRequest());

        // Then
        verify(importRecipeService, times(1)).importBeerXml(any(InputStream.class));
    }

}
