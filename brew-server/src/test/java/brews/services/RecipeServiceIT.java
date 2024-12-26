package brews.services;

import brews.domain.Recipe;
import brews.repository.RecipeRepository;
import brews.services.exceptions.RecipeEntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("IT")
@Slf4j
public class RecipeServiceIT {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

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
    public void testFindAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        assertThat(recipes.size()).isGreaterThanOrEqualTo(0).isEqualTo(2);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testFindAllRecipesForUser() {
        List<Recipe> recipes = recipeService.getAllRecipesForUser("testuser");
        assertThat(recipes.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testFindTop5RatedRecipesForUser() {
        List<Recipe> recipes = recipeService.getTop5RatedRecipesForUser("testuser");
        assertThat(recipes.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testFindRecipeById() {
        Recipe recipe = recipeService.getRecipeById(1L);
        assertThat(recipe).isNotNull();
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testUpdateRecipe() {
        Recipe recipe = recipeService.getRecipeById(1L);
        recipe.setName("Updated Recipe");
        Recipe updatedRecipe = recipeService.updateRecipe(1L, recipe);
        assertThat(updatedRecipe.getName()).isEqualTo("Updated Recipe");
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testUpdateRating() {
        Recipe recipe = recipeService.getRecipeById(1L);
        Short rating = 5;
        Recipe updatedRecipe = recipeService.updateRating(1L, rating);
        assertThat(updatedRecipe.getRating()).isEqualTo(rating);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testDeleteRecipe() {
        recipeService.deleteRecipe(1L);
        assertThatExceptionOfType(RecipeEntityNotFoundException.class).isThrownBy(() -> recipeService.getRecipeById(1L));
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testGetNotesForRecipe() {
        String notes = recipeService.getNotesForRecipe(1L);
        assertThat(notes).isEqualTo("Test Notes");
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testGetNotesForRecipeWithNoNotes() {
        String notes = recipeService.getNotesForRecipe(2L);
        assertThat(notes).isNull();
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testRecipeNotFoundExceptionWithNoRecipe() {
        assertThatExceptionOfType(RecipeEntityNotFoundException.class).isThrownBy(() -> recipeService.getRecipeById(3L));
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testUpdateRecipeWithNoRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(3L);
        assertThatExceptionOfType(RecipeEntityNotFoundException.class).isThrownBy(() -> recipeService.updateRecipe(3L, recipe));
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testUpdateRatingWithNoRecipe() {
        Short rating = 5;
        assertThatExceptionOfType(RecipeEntityNotFoundException.class).isThrownBy(() -> recipeService.updateRating(33L, rating));
    }

}
