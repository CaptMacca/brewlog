package brews.controllers;

import brews.domain.Recipe;
import brews.repository.RecipeRepository;
import brews.services.ImportRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Steve on 8/05/2017.
 */
@RestController
@RequestMapping("api/v1/")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    ImportRecipeService importRecipeService;

    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @RequestMapping(value = "/recipes/import", method = RequestMethod.POST)
    public List<Recipe> importRecipe(@RequestBody String file) {
        List<Recipe> recipes = importRecipeService.importBeerXml(file);

        recipeRepository.save(recipes);

        return null;
    }

    @RequestMapping(value = "/recipes/{id}/delete", method = RequestMethod.DELETE)
    public Recipe deleteRecipe(@PathVariable Long id) {
        Recipe existingRecipe = recipeRepository.findOne(id);
        recipeRepository.delete(id);
        return existingRecipe;
    }

}
