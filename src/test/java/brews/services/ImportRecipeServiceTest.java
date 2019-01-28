package brews.services;

import brews.MockImportedRecipes;
import brews.MockRecipe;
import brews.MockXMLRecipe;
import brews.domain.Brewer;
import brews.domain.Recipe;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.beerxml.ImportedRecipes;
import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeExistsException;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.mapper.domain.RecipeMapper;
import brews.repository.BrewerRepository;
import brews.repository.RecipeRepository;
import brews.services.impl.ImportRecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ImportRecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    BeerXMLRecipeMapper beerXMLRecipeMapper;
    @Mock
    BeerXMLReaderService beerXMLReaderService;
    @Mock
    BrewerRepository brewerRepository;
    @Mock
    RecipeMapper recipeMapper;

    private ImportRecipeService importRecipeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        importRecipeService = new ImportRecipeServiceImpl(recipeRepository, beerXMLRecipeMapper, beerXMLReaderService, recipeMapper, brewerRepository);
    }

    @Test
    public void testImportRecipe() {

        // Given
        InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();
        ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();
        Recipe mockRecipe = MockRecipe.getRecipe();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        Brewer mockBrewer = new Brewer();
        mockBrewer.setId(1L);
        mockBrewer.setUserid("joe");

        when(beerXMLReaderService.readBeerXML(any(InputStream.class))).thenReturn(mockImportedRecipes);
        when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(Optional.empty()); // Force save of recipe
        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(Arrays.asList(recipeDto));
        when(brewerRepository.findOneByUserid(anyString())).thenReturn(mockBrewer);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        ArgumentCaptor<String> recipeNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ImportedRecipe> importedRecipeArgumentCaptor = ArgumentCaptor.forClass(ImportedRecipe.class);

        // When
        List<RecipeDto> recipes = importRecipeService.importBeerXml(mockedFile,"joe");

        // Then
        assertThat(recipes).isNotEmpty();
        verify(beerXMLReaderService, times(1)).readBeerXML(any(InputStream.class));
        verify(beerXMLRecipeMapper, times(1)).map(importedRecipeArgumentCaptor.capture());
        verify(recipeRepository, times(1)).findRecipeByName(recipeNameArgumentCaptor.capture());
        verify(recipeRepository, times(1)).save(recipeArgumentCaptor.capture());
        verify(recipeRepository, times(1)).flush();
        verify(recipeMapper, times(1)).toRecipeDtos(anyList());
        verify(brewerRepository, times(1)).findOneByUserid(anyString());

    }

    @Test(expected = ImportedRecipeExistsException.class)
    public void testImportExistingRecipe() {

        // Given
        InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();

        Brewer mockBrewer = new Brewer();
        mockBrewer.setId(1L);
        mockBrewer.setUserid("joe");

        ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();
        Recipe mockRecipe = MockRecipe.getRecipe();

        when(beerXMLReaderService.readBeerXML(any(InputStream.class))).thenReturn(mockImportedRecipes);
        when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(Optional.of(mockRecipe));
        when(brewerRepository.findOneByUserid(anyString())).thenReturn(mockBrewer);

        // When
        List<RecipeDto> recipes = importRecipeService.importBeerXml(mockedFile, "");

        // Then - Should throw the exception
        verify(beerXMLReaderService, times(1)).readBeerXML(any(InputStream.class));
        verify(beerXMLRecipeMapper, times(1)).map(any(ImportedRecipe.class));
        verify(recipeRepository, times(1)).findRecipeByName(anyString());
        verify(recipeRepository, times(0)).save(any(Recipe.class));
        verify(recipeRepository, times(0)).flush();
        verify(recipeMapper, times(0)).toRecipeDtos(anyList());
        verify(brewerRepository, times(1)).findOneByUserid(anyString());
    }

}
