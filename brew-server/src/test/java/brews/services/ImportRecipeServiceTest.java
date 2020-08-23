package brews.services;

import brews.MockImportedRecipes;
import brews.MockRecipe;
import brews.MockXMLRecipe;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.beerxml.ImportedRecipes;
import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeExistsException;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.mapper.domain.RecipeMapper;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.impl.ImportRecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.util.ArrayList;
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
//    @Mock
//    BeerXMLReaderService beerXMLReaderService;
    @Mock
    UserRepository userRepository;
    @Mock
    RecipeMapper recipeMapper;

    private ImportRecipeService importRecipeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        importRecipeService = new ImportRecipeServiceImpl(recipeRepository, userRepository);
    }

    @Test
    public void testImportRecipe() {

        // Given
//        InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();
//        ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();

        Recipe mockRecipe = MockRecipe.getRecipe();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(mockRecipe);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("joe");

//        when(beerXMLReaderService.readBeerXML(any(InputStream.class))).thenReturn(mockImportedRecipes);
        when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findRecipeByNameAndUser(anyString(),any(User.class))).thenReturn(Optional.empty()); // Force save of recipe
        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(Arrays.asList(recipeDto));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
//        ArgumentCaptor<ImportedRecipe> importedRecipeArgumentCaptor = ArgumentCaptor.forClass(List.class);

        // When
        recipes = importRecipeService.importRecipes(recipes,"joe");

        // Then
//        verify(beerXMLReaderService, times(1)).readBeerXML(any(InputStream.class));
//        verify(beerXMLRecipeMapper, times(1)).map(importedRecipeArgumentCaptor.capture());
        verify(recipeRepository, times(1)).findRecipeByNameAndUser(anyString(), any(User.class));
        verify(recipeRepository, times(1)).save(recipeArgumentCaptor.capture());
        verify(recipeRepository, times(1)).flush();
//        verify(recipeMapper, times(1)).toRecipeDtos(anyList());
        verify(userRepository, times(1)).findByUsername(anyString());

    }

    @Test(expected = ImportedRecipeExistsException.class)
    public void testImportExistingRecipe() {

        // Given
        InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();

        User mockBrewer = new User();
        mockBrewer.setId(1L);
        mockBrewer.setUsername("joe");

        ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();
        Recipe mockRecipe = MockRecipe.getRecipe();

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(mockRecipe);

//        when(beerXMLReaderService.readBeerXML(any(InputStream.class))).thenReturn(mockImportedRecipes);
        when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findRecipeByNameAndUser(anyString(), any(User.class))).thenReturn(Optional.of(mockRecipe));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockBrewer));

        // When
        recipes = importRecipeService.importRecipes(recipes, "mock");

        // Then - Should throw the exception
//        verify(beerXMLReaderService, times(1)).readBeerXML(any(InputStream.class));
        verify(beerXMLRecipeMapper, times(1)).map(any(ImportedRecipe.class));
        verify(recipeRepository, times(1)).findRecipeByNameAndUser(anyString(),any(User.class));
        verify(recipeRepository, times(0)).save(any(Recipe.class));
        verify(recipeRepository, times(0)).flush();
        verify(recipeMapper, times(0)).toRecipeDtos(anyList());
        verify(userRepository, times(1)).findByUsername(anyString());
    }

}
