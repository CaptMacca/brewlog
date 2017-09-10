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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 30/04/2017.
 */
@Service
public class ImportRecipeService {

    private final static Logger logger = LoggerFactory.getLogger(ImportRecipeService.class);

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final MashRepository mashRepository;
    private final RecipeMapper recipeMapper;

    @Autowired
    public ImportRecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, MashRepository mashRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.mashRepository = mashRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<Recipe> importBeerXml(InputStream contents) {

        logger.debug("Importing beerxml file ");
        ImportedRecipes importedRecipes = unmarshallBeerXML(contents);

        List<Recipe> recipes = new ArrayList<>();

        logger.debug("Saving recipes in database");
        List<ImportedRecipe> candidateRecipes = importedRecipes.getImportedRecipes();

        candidateRecipes.forEach((importedRecipe)-> {
            Recipe recipe = recipeMapper.map(importedRecipe);
            recipe = saveRecipe(recipe);
            recipes.add(recipe);
        });

        return recipes;

    }

    private Recipe saveRecipe(Recipe recipe) {
        if (recipe != null) {
            logger.debug("Saving recipe: " + recipe.getName());
            for (Ingredient ingredient : recipe.getIngredients()) {
                logger.debug("Saving ingredient: " + ingredient.getName());
                ingredientRepository.saveAndFlush(ingredient);
            }
            for (Mash mash : recipe.getMashes()) {
                logger.debug("Saving mash: " + mash.getName());
                mashRepository.saveAndFlush(mash);
            }

            recipeRepository.save(recipe);
            recipeRepository.flush();

        }
        return recipe;
    }

    private ImportedRecipes unmarshallBeerXML(InputStream contents) {

        ImportedRecipes importedRecipes=null;

        try {
            logger.debug("Unmarshalling the beerxml file");
            JAXBContext jaxbContext = JAXBContext.newInstance(ImportedRecipes.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            importedRecipes = (ImportedRecipes) jaxbUnmarshaller.unmarshal(contents);

            logger.debug("Beerxml has been unmarshalled");
            if ((importedRecipes!=null) && (importedRecipes.getImportedRecipes() !=null)) {
                logger.debug("Found " + importedRecipes.getImportedRecipes().size() + " recipes.");
            }
        } catch (JAXBException e) {
            logger.error("Exception unmarshalling beerxml");
        }

        return importedRecipes;
    }

}
