package brews.services;

import brews.MockImportedRecipes;
import brews.MockRecipe;
import brews.MockXMLRecipe;
import brews.domain.Recipe;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.beerxml.ImportedRecipes;
import brews.exceptions.ImportRecipeServiceException;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ImportRecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    BeerXMLRecipeMapper beerXMLRecipeMapper;
    @Mock
    BeerXMLReaderService beerXMLReaderService;

    ImportRecipeService importRecipeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        importRecipeService = new ImportRecipeServiceImpl(recipeRepository, beerXMLRecipeMapper, beerXMLReaderService);
    }

    @Test
    public void testImportRecipe() {

        // Given
        InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();
        ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();
        Recipe mockRecipe = MockRecipe.getRecipe();

        when(beerXMLReaderService.readBeerXML(any(InputStream.class))).thenReturn(mockImportedRecipes);
        when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(Optional.ofNullable(null)); // Force save of recipe

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        ArgumentCaptor<String> recipeNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ImportedRecipe> importedRecipeArgumentCaptor = ArgumentCaptor.forClass(ImportedRecipe.class);

        // When
        List<Recipe> recipes = importRecipeService.importBeerXml(mockedFile);

        // Then
        assertNotNull(recipes);
        verify(beerXMLRecipeMapper, times(1)).map(importedRecipeArgumentCaptor.capture());
        verify(recipeRepository, times(1)).findRecipeByName(recipeNameArgumentCaptor.capture());
        verify(recipeRepository, times(1)).save(recipeArgumentCaptor.capture());

    }

    @Test(expected = ImportRecipeServiceException.class)
    public void testImportExistingRecipe() {

        // Given
        InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();

        ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();
        Recipe mockRecipe = MockRecipe.getRecipe();

        when(beerXMLReaderService.readBeerXML(any(InputStream.class))).thenReturn(mockImportedRecipes);
        when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(Optional.of(mockRecipe));

        // When
        List<Recipe> recipes = importRecipeService.importBeerXml(mockedFile);

        // Then - Should throw the exception
    }

}
