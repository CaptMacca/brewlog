package brews.services;

import brews.domain.Ingredient;
import brews.domain.Mash;
import brews.domain.Recipe;
import brews.repository.IngredientRepository;
import brews.repository.MashRepository;
import brews.repository.RecipeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    ImportRecipeService importRecipeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImportRecipe() {

        Recipe mockedRecipe = getMockedRecipe();

        Mockito.when(mashRepository.saveAndFlush(any(Mash.class))).thenReturn(mockedRecipe.getMashes().get(0));
        Mockito.when(ingredientRepository.saveAndFlush(any(Ingredient.class))).thenReturn(mockedRecipe.getIngredients().get(0));
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(mockedRecipe);

        InputStream file = MockXMLRecipe.getMockedXMLRecipe();

        Assertions.assertThat(importRecipeService.importBeerXml(file));

    }

    private Recipe getMockedRecipe() {

        Ingredient mockedIngredient = new Ingredient();
        Mash mockedMash = new Mash();
        Recipe mockedRecipe = new Recipe();

        mockedRecipe.setBatchSize("12 L");
        mockedRecipe.setBoilTime("23");
        mockedRecipe.setId(1L);
        mockedRecipe.setEstimatedABV("3 EBC");
        mockedRecipe.setFinalGravity("1012");
        mockedRecipe.setName("Blah");

        mockedIngredient.setAmount("19");
        mockedIngredient.setId(1L);
        mockedIngredient.setName("blah");

        mockedMash.setId(1L);
        mockedMash.setName("blah");
        mockedMash.setStepTemp("23");
        mockedMash.setStepTime(222);

        List<Mash> mashes = new ArrayList<>();
        mashes.add(mockedMash);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(mockedIngredient);

        mockedRecipe.setMashes(mashes);
        mockedRecipe.setIngredients(ingredients);

        return mockedRecipe;
    }



}
