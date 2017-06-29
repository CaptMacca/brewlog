package brews.services;

import brews.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Steve on 24/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportImportedRecipeServiceIT {

    @Autowired
    ImportRecipeService importRecipeService;

    @Test
    public void testImportRecipe() {

        List<Recipe> recipes = importRecipeService.importBeerXml("c:/temp/zombie_dust_clone.xml");
        assert(recipes.size()>0);

    }
}
