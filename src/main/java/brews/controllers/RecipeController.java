package brews.controllers;

import brews.domain.Recipe;
import brews.repository.RecipeRepository;
import brews.services.UploadRecipeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Steve on 8/05/2017.
 */
@RestController
@RequestMapping("api/v1/brews")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final UploadRecipeService uploadRecipeService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, UploadRecipeService uploadRecipeService) {
        this.uploadRecipeService = uploadRecipeService;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/recipes")
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @GetMapping("/recipes/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeRepository.findOne(id);
    }

    @PutMapping("/recipes/{id}")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe existingRecipe = recipeRepository.findOne(id);
        BeanUtils.copyProperties(recipe,existingRecipe);
        return recipeRepository.saveAndFlush(existingRecipe);
    }

    @DeleteMapping("/recipes/{id}")
    public Recipe deleteRecipe(@PathVariable Long id) {
        Recipe existingRecipe = recipeRepository.findOne(id);
        recipeRepository.delete(existingRecipe);
        return existingRecipe;
    }

    @PostMapping("/recipes/upload")
    public @ResponseBody String uploadRecipe(String fileName) {

        uploadRecipeService.uploadFileToS3(fileName);

        return "Import succeeded";
    }

}
