package brews.controllers;

import brews.domain.Recipe;
import brews.repository.RecipeRepository;
import brews.services.RecipeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Steve on 8/05/2017.
 */
@RestController
@RequestMapping("api/v1/brews")
public class RecipeController {

    @Value("${recipe.upload.folder}")
    private String UPLOADED_FOLDER;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.GET)
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeRepository.findOne(id);
    }

    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.PUT)
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe existingRecipe = recipeRepository.findOne(id);
        BeanUtils.copyProperties(recipe,existingRecipe);
        return recipeRepository.saveAndFlush(existingRecipe);
    }

    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.DELETE)
    public Recipe deleteRecipe(@PathVariable Long id) {
        Recipe existingRecipe = recipeRepository.findOne(id);
        recipeRepository.delete(existingRecipe);
        return existingRecipe;
    }

    @RequestMapping(value = "/recipes/import", method = RequestMethod.POST)
    public @ResponseBody String importRecipe(@RequestParam MultipartFile file) {

        String fileName = null;

        try {

            fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            BufferedOutputStream buffStream =
                    new BufferedOutputStream(new FileOutputStream(new File(UPLOADED_FOLDER + fileName)));
            buffStream.write(bytes);
            buffStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        recipeService.importBeerXml(new File(fileName));

        return "";
    }

}
