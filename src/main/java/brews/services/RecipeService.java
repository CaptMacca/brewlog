package brews.services;

import brews.beerxml.ImportedRecipe;
import brews.beerxml.ImportedRecipes;
import brews.mapper.RecipeMapper;
import brews.domain.Ingredient;
import brews.domain.Mash;
import brews.domain.Recipe;
import brews.repository.IngredientRepository;
import brews.repository.MashRepository;
import brews.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 30/04/2017.
 */
@Service
public class RecipeService {

    private final static Logger logger = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    MashRepository mashRepository;
    @Autowired
    RecipeMapper recipeMapper;

    public RecipeService() {}


    public List<Recipe> importBeerXml(File file) {

        ImportedRecipes importedRecipes = unmarshallBeerXML(file);

        List<Recipe> recipes = new ArrayList<>();

        for (ImportedRecipe importedRecipe : importedRecipes.getImportedRecipes()) {
            Recipe recipe = recipeMapper.map(importedRecipe);

            if (recipe != null) {

                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredientRepository.saveAndFlush(ingredient);
                }

                for (Mash mash : recipe.getMashes()) {
                    mashRepository.saveAndFlush(mash);
                }

                recipeRepository.save(recipe);
                recipeRepository.flush();

                recipes.add(recipe);
            }
        }

        return recipes;

    }

    private ImportedRecipes unmarshallBeerXML(File file) {

        ImportedRecipes importedRecipes=null;

        try {
            logger.debug("Unmarshall the beerxml file");
            JAXBContext jaxbContext = JAXBContext.newInstance(ImportedRecipes.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            importedRecipes = (ImportedRecipes) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            logger.error("Exception unmarshalling beerxml");
        }

        return importedRecipes;
    }
}
