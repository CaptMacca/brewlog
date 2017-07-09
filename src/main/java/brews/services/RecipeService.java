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

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ImportedRecipes.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ImportedRecipes importedRecipes = (ImportedRecipes) jaxbUnmarshaller.unmarshal(file);

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

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }



}
