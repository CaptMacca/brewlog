package brews.repository;

import brews.domain.Recipe;
import brews.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("IT")
@Slf4j
public class RecipeRepositoryIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:15.1"))
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
        List<Recipe> recipes = recipeRepository.findAll();
        assertThat(recipes.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testFindOneRecipe() {
        Recipe recipe = recipeRepository.getOne(1L);
        assertThat(recipe.getId()).isEqualTo(1L);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testFindRecipeByNameAndUser() {
        User user = userRepository.getOne(1L);
        Optional<Recipe> recipe = recipeRepository.findRecipeByNameAndUser("Test Recipe", user);
        assertThat(recipe.isPresent()).isTrue();
        assertThat(recipe.get().getName()).isEqualTo("Test Recipe");
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testUpdateRecipe() {
        Recipe recipe = recipeRepository.getOne(1L);
        recipe.setName("Updated Recipe");
        recipeRepository.saveAndFlush(recipe);
        Recipe updatedRecipe = recipeRepository.getOne(1L);
        assertThat(updatedRecipe.getName()).isEqualTo("Updated Recipe");
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testDeleteRecipe() {
        Recipe recipe = recipeRepository.getOne(1L);
        recipeRepository.delete(recipe);
        List<Recipe> recipes = recipeRepository.findAll();
        // Two initial recipes should now be one
        assertThat(recipes.size()).isEqualTo(1);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testFindRecipesByUserUsername() {
        List<Recipe> recipes = recipeRepository.findRecipesByUserUsername("testuser");
        assertThat(recipes.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testFindTop5RatedByUserUsernameOrderByNameDesc() {
        List<Recipe> recipes = recipeRepository.findTop5RatedByUserUsernameOrderByNameDesc("testuser");
        assertThat(recipes.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void testSaveRecipe() {
        User user = userRepository.getOne(1L);
        List<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = new Recipe();
        recipe.setName("New Recipe");
        recipe.setUser(user);
        log.debug(String.format("Saving recipe: %s", recipe.toString()));
        recipeRepository.saveAndFlush(recipe);
        List<Recipe> updatedRecipes = recipeRepository.findAll();
        assertThat(updatedRecipes.size()).isEqualTo(recipes.size() + 1);
    }
}
