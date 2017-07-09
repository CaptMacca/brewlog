package brews.repository;

import brews.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by Steve on 17/06/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ImportedRecipeRepositoryIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    public void testFindAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        assertThat(recipes.size(),is(greaterThanOrEqualTo(0)));
    }
}
