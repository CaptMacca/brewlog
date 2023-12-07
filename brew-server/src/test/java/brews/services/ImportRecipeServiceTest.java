package brews.services;

import brews.MockImportedRecipes;
import brews.MockRecipe;
import brews.MockXMLRecipe;
import brews.app.presentation.dto.recipe.RecipeDto;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.beerxml.mapping.BeerXMLRecipeMapper;
import brews.domain.beerxml.model.ImportedRecipe;
import brews.domain.beerxml.model.ImportedRecipes;
import brews.domain.mapper.RecipeMapper;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.exceptions.ImportedRecipeExistsException;
import brews.services.exceptions.UserEntityNotFoundException;
import brews.services.impl.ImportRecipeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ImportRecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    BeerXMLRecipeMapper beerXMLRecipeMapper;
    @Mock
    UserRepository userRepository;
    @Mock
    RecipeMapper recipeMapper;

    private ImportRecipeService importRecipeService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        importRecipeService = new ImportRecipeServiceImpl(recipeRepository, userRepository);
    }

    @Test
    public void givenValidRecipeImportSucceeds() {

        // Given
        Recipe mockRecipe = MockRecipe.getRecipe();
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(mockRecipe);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("joe");

        when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findRecipeByNameAndUser(anyString(),any(User.class))).thenReturn(Optional.empty()); // Force save of recipe
        when(recipeMapper.toRecipeDtos(anyList())).thenReturn(Arrays.asList(recipeDto));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        // When
        recipes = importRecipeService.importRecipes(recipes,"joe");

        // Then
        verify(recipeRepository, times(1)).findRecipeByNameAndUser(anyString(), any(User.class));
        verify(recipeRepository, times(recipes.size())).save(recipeArgumentCaptor.capture());
        verify(userRepository, times(1)).findByUsername(anyString());

    }

    @Test()
    public void givenExistingRecipeImportThrowsImportRecipeExistsException() {

        Assertions.assertThrows(ImportedRecipeExistsException.class, () -> {
            // Given
            InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();

            User mockBrewer = new User();
            mockBrewer.setId(1L);
            mockBrewer.setUsername("joe");

            ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();
            Recipe mockRecipe = MockRecipe.getRecipe();

            List<Recipe> recipes = new ArrayList<>();
            recipes.add(mockRecipe);


            when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
            when(recipeRepository.findRecipeByNameAndUser(anyString(), any(User.class))).thenReturn(Optional.of(mockRecipe));
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockBrewer));

            // When
            recipes = importRecipeService.importRecipes(recipes, "mock");

            // Then - Should throw the exception
            verify(beerXMLRecipeMapper, times(1)).map(any(ImportedRecipe.class));
            verify(recipeRepository, times(1)).findRecipeByNameAndUser(anyString(), any(User.class));
            verify(recipeRepository, times(0)).saveAllAndFlush(anyList());
            verify(recipeRepository, times(0)).flush();
            verify(recipeMapper, times(0)).toRecipeDtos(anyList());
            verify(userRepository, times(1)).findByUsername(anyString());
        });
    }

    @Test()
    public void givenUnknownUserImportThrowsUserEntityNotFoundException() {

        Assertions.assertThrows(UserEntityNotFoundException.class, () -> {
            // Given
            InputStream mockedFile = MockXMLRecipe.getMockedXMLRecipe();

            User mockBrewer = new User();
            mockBrewer.setId(1L);
            mockBrewer.setUsername("joe");

            ImportedRecipes mockImportedRecipes = MockImportedRecipes.getImportedRecipes();
            Recipe mockRecipe = MockRecipe.getRecipe();

            List<Recipe> recipes = new ArrayList<>();
            recipes.add(mockRecipe);


            when(beerXMLRecipeMapper.map(any(ImportedRecipe.class))).thenReturn(mockRecipe);
            when(recipeRepository.findRecipeByNameAndUser(anyString(), any(User.class))).thenReturn(Optional.of(mockRecipe));
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

            // When
            recipes = importRecipeService.importRecipes(recipes, "mock");

            // Then - Should throw the exception
            verify(beerXMLRecipeMapper, times(1)).map(any(ImportedRecipe.class));
            verify(recipeRepository, times(1)).findRecipeByNameAndUser(anyString(), any(User.class));
            verify(recipeRepository, times(0)).save(any(Recipe.class));
            verify(recipeRepository, times(0)).flush();
            verify(recipeMapper, times(0)).toRecipeDtos(anyList());
            verify(userRepository, times(1)).findByUsername(anyString());
        });
    }
}
