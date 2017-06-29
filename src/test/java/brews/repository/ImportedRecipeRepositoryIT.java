package brews.repository;

import brews.BrewlogApplication;
import brews.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by Steve on 17/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
