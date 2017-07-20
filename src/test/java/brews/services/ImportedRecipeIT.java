package brews.services;

import brews.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

/**
 * Created by Steve on 24/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportedRecipeIT {

    @Autowired
    RecipeService recipeService;

    @Test
    public void testImportRecipe() {

        File file = new File("c:/temp/zombie_dust_clone.xml");

        List<Recipe> recipes = recipeService.importBeerXml(file);
        assert(recipes.size()>0);

    }
}
