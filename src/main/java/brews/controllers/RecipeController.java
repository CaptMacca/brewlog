package brews.controllers;

import brews.domain.dto.RecipeDto;
import brews.exceptions.RecipeServiceException;
import brews.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The recipe API.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDto>> getAll() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PutMapping("/recipes")
    public ResponseEntity<?> updateRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipeDto;
        try {
            updatedRecipeDto = recipeService.saveRecipe(recipeDto);
        } catch (RecipeServiceException e) {
            return new ResponseEntity<>("Recipe could not be updated", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedRecipeDto, HttpStatus.OK);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {

        try {
            recipeService.deleteRecipe(id);

        } catch (RecipeServiceException e) {
            return new ResponseEntity<>("Recipe could not be found to delete", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.getAll(), HttpStatus.ACCEPTED);
    }

}
