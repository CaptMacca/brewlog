package brews.services;

import brews.beerxml.ImportedRecipe;
import brews.beerxml.ImportedRecipes;
import brews.domain.Recipe;
import brews.mapper.RecipeMapper;
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

    public List<Recipe> importBeerXml(InputStream contents) throws RecipeExistsException {

        logger.debug("Importing beerxml file ");
        ImportedRecipes importedRecipes = unmarshallBeerXML(contents);

        List<Recipe> recipes = new ArrayList<>();

        logger.debug("Saving recipes in database");
        List<ImportedRecipe> candidateRecipes = importedRecipes.getImportedRecipes();

        candidateRecipes.forEach((importedRecipe)-> {
            Recipe candidateRecipe = recipeMapper.map(importedRecipe);

            // Update an already uploaded recipe
            Recipe existingRecipe = recipeRepository.findRecipeByName(candidateRecipe.getName());
            if (existingRecipe == null) {
                candidateRecipe = saveRecipe(candidateRecipe);
            } else{
                // TODO: Revisit for cascading update but throw exception at this point
                //candidateRecipe = updateRecipe(existingRecipe,candidateRecipe);
                throw new RecipeExistsException();
            }
            recipes.add(candidateRecipe);
        });

        return recipes;
    }

    /*
    private Recipe updateRecipe(Recipe existingRecipe,Recipe recipe) {

        if (recipe != null) {
            // For each ingredient attempt to find it and update

            List<Ingredient> existingIngredients = existingRecipe.getIngredients();
            List<Mash> existingMashSteps = existingRecipe.getMashes();

            recipe.getIngredients()
                  .forEach(ingredient -> {
                      Optional<Ingredient> candidateIngredient =
                                existingIngredients.stream()
                                    .filter((Ingredient p) -> p.getName().equals(ingredient.getName()))
                                    .findAny();

                      if (candidateIngredient.isPresent()) {
                           Ingredient existingIngredient = candidateIngredient.get();

                           if (existingIngredient instanceof Hop) {
                               Hop existingHop = (Hop) existingIngredient;
                               Hop newHop = (Hop) ingredient;

                               BeanUtils.copyProperties(newHop,existingHop,"id");
                               ingredientRepository.saveAndFlush(existingHop);
                           } else if (existingIngredient instanceof Yeast) {
                               Yeast existingYeast = (Yeast) existingIngredient;
                               Yeast newYeast = (Yeast) ingredient;

                               BeanUtils.copyProperties(newYeast,existingYeast,"id");
                               ingredientRepository.saveAndFlush(existingYeast);
                           } else if (existingIngredient instanceof Fermentable) {
                               Fermentable existingFermentable = (Fermentable) existingIngredient;
                               Fermentable newFermentable = (Fermentable) ingredient;

                               BeanUtils.copyProperties(newFermentable,existingFermentable,"id");
                               ingredientRepository.saveAndFlush(existingFermentable);
                           }
                      }
                  });


            recipe.getMashes().forEach((Mash mash) -> {
                Optional<Mash> candidateMash =
                        existingMashSteps.stream()
                                .filter((Mash p) -> p.getName().equals(mash.getName()))
                                .findAny();

                BeanUtils.copyProperties(candidateMash,mash,"id");
                mashRepository.save(mash);
            });

            BeanUtils.copyProperties(recipe,existingRecipe,"id");
            recipeRepository.saveAndFlush(existingRecipe);
        }

        return recipe;
    }
    */

    private Recipe saveRecipe(Recipe recipe) {
        if (recipe != null) {
            logger.debug("Saving recipe: " + recipe.getName());
            recipe.getIngredients().forEach(ingredient -> {
                        logger.debug("Saving ingredient: " + ingredient.getName());
                        ingredientRepository.save(ingredient);
                    });
            recipe.getMashes().forEach(mash -> {
                logger.debug("Saving mash: " + mash.getName());
                mashRepository.save(mash);
            });

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
