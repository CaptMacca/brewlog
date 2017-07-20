package brews.services;

import brews.domain.Recipe;
import brews.mapper.RecipeMapper;
import brews.repository.IngredientRepository;
import brews.repository.MashRepository;
import brews.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;

/**
 * Created by Steve on 17/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportRecipeTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    MashRepository mashRepository;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImportRecipe() {


        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(null);

    }
}
