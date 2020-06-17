package brews.controllers;

import brews.domain.dto.RecipeDto;
import brews.domain.dto.UpdateRatingDto;
import brews.services.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * The recipe API.
 */
@Slf4j
@RestController
@RequestMapping("api/recipes")
@CrossOrigin(origins = "*")
@ApiOperation("API for updating, retrieving and deleting recipes. Recipes cannot be created," +
              " see the upload endpoint for importing a beer.xml file to create a recipe")
public final class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    @ApiOperation("Returns all recipes stored in the repository")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<RecipeDto>> getAll() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping()
    @ApiOperation("Returns all recipes stored in the repository for a user")
    public ResponseEntity<List<RecipeDto>> getAllRecipesForUser(@RequestParam String username) {
        return ResponseEntity.ok(recipeService.getAllRecipesForUser(username));
    }

    @GetMapping("/top5")
    @ApiOperation("Returns top 5 rate recipes stored in the repository for a user")
    public ResponseEntity<List<RecipeDto>> getTop5RatedRecipesForUser(@RequestParam String username) {
        return ResponseEntity.ok(recipeService.getTop5RatedRecipesForUser(username));
    }


    @GetMapping("{id}")
    @ApiOperation("Returns a recipe identified by the id")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PutMapping("{id}")
    @ApiOperation("Updates a recipe identified by the id")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipeDto = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipeDto);
    }

    @PutMapping("{id}/rating")
    @ApiOperation("Update the recipe rating")
    public ResponseEntity<RecipeDto> updateRating(@RequestBody UpdateRatingDto updateRatingDto) {
        RecipeDto updateRecipeDto = recipeService.updateRating(updateRatingDto.getId(), updateRatingDto.getRating());
        return ResponseEntity.ok(updateRecipeDto);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a recipe identified by the id")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
