package brews.repository;

import brews.domain.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class RecipeRepositoryIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    public void testFindAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        assertThat(recipes.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testFindOneRecipe() {
        Recipe recipe = recipeRepository.getOne(1L);
        assertThat(recipe.getId()).isEqualTo(1L);
    }
}
