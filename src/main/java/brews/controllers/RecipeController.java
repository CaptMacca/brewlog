package brews.controllers;

import brews.domain.Recipe;
import brews.repository.RecipeRepository;
import brews.services.ImportRecipeService;
import brews.services.RecipeExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 8/05/2017.
 */
@RestController
@RequestMapping("api/v1/brews")
@CrossOrigin(origins = "*")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeRepository recipeRepository;
    private final ImportRecipeService importRecipeService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, ImportRecipeService importRecipeService) {
        this.recipeRepository = recipeRepository;
        this.importRecipeService = importRecipeService;
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
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe existingRecipe = recipeRepository.findOne(id);

        if (existingRecipe==null) {
            return new ResponseEntity<>("Recipe could not be found to delete",HttpStatus.BAD_REQUEST);
        }

        BeanUtils.copyProperties(existingRecipe,recipe);
        recipe = recipeRepository.saveAndFlush(recipe);
        return new ResponseEntity<>(recipe,HttpStatus.OK);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        Recipe existingRecipe = recipeRepository.findOne(id);

        if (existingRecipe==null) {
            return new ResponseEntity<>("Recipe could not be found to delete",HttpStatus.BAD_REQUEST);
        }

        recipeRepository.delete(existingRecipe);
        List<Recipe> recipes = recipeRepository.findAll();
        return new ResponseEntity<>(recipes, HttpStatus.ACCEPTED);
    }

    @PostMapping("/recipes/upload")
    public ResponseEntity<?> uploadRecipe(@RequestParam("file") MultipartFile uploadfile) {

        logger.debug("File upload requested." );
        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("Please upload a file", HttpStatus.BAD_REQUEST);
        }

        if (!uploadfile.getOriginalFilename().endsWith(".xml")) {
            return new ResponseEntity<>("Please load a beersmith .xml file",HttpStatus.BAD_REQUEST);
        }

        List<Recipe> recipes=new ArrayList<>();
        try {
            InputStream fileContents = uploadfile.getInputStream();
            if (fileContents!=null) {
              recipes = importRecipeService.importBeerXml(fileContents);
            }

        } catch (IOException e) {
            logger.error("IOException uploading file:", e);
            return new ResponseEntity<>("Upload failed due to IO problems", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RecipeExistsException e) {
            String msg = "Recipe of same name already exists in recipe database";
            logger.warn(msg);
            return new ResponseEntity<Object>(msg,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(recipes, HttpStatus.ACCEPTED);
    }

}
