package brews.services;

import brews.MockRecipe;
import brews.domain.Recipe;
import brews.domain.mapper.RecipeMapper;
import brews.repository.RecipeRepository;
import brews.services.exceptions.ImportedRecipeExistsException;
import brews.services.exceptions.UserEntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("IT")
@Slf4j
public class ImportRecipeServiceIT {

    @Autowired
    ImportRecipeService importRecipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeMapper recipeMapper;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.1"))
      .withDatabaseName("brews")
      .withUsername("brews")
      .withPassword("brews");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterEach
    public void cleanUp() {
        recipeRepository.deleteAllInBatch();
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void givenRecipesImportRecipesSucceeds() {

        Recipe mockRecipe = MockRecipe.getRecipe();
        mockRecipe.setId(null);
        mockRecipe.setVersionId(1L);
        List<Recipe> importedRecipes = new ArrayList<>();
        importedRecipes.add(mockRecipe);

        int recipesCountBefore = recipeRepository.findRecipesByUserUsername("testuser").size();
        List<Recipe> recipes = importRecipeService.importRecipes(importedRecipes,"testuser");
        List<Recipe> usersRecipes = recipeRepository.findRecipesByUserUsername("testuser");
        assertThat(usersRecipes.size()).isEqualTo(recipesCountBefore+1);
    }

    @Test()
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void givenExistingRecipeImportThrowsImportRecipeExistsException() {

        Assertions.assertThrows(ImportedRecipeExistsException.class, () -> {
            //Import a real mocked recipe the first time ok
            Recipe mockRecipe = MockRecipe.getRecipe();
            mockRecipe.setId(null);
            mockRecipe.setVersionId(1L);
            List<Recipe> recipes = new ArrayList<>();
            recipes.add(mockRecipe);
            importRecipeService.importRecipes(recipes, "testuser");
            // Repeat the import of the same recipe to trigger the exception
            importRecipeService.importRecipes(recipes, "testuser");
        });
    }

    @Test()
    public void givenUnknownUserImportThrowsUserEntityNotFoundException() {

        Assertions.assertThrows(UserEntityNotFoundException.class, () -> {
            Recipe mockRecipe = MockRecipe.getRecipe();
            List<Recipe> recipes = new ArrayList<>();
            recipes.add(mockRecipe);
            importRecipeService.importRecipes(recipes, "nonexistantuser");
        });
    }

}
