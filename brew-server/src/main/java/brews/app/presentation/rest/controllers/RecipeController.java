package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.recipe.RecipeDto;
import brews.app.presentation.dto.user.UpdateRatingRequest;
import brews.domain.Recipe;
import brews.domain.mapper.RecipeMapper;
import brews.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Recipes", description = "API for updating, retrieving and deleting recipes. Recipes cannot be created," +
              " see the upload endpoint for importing a beer.xml file to create a recipe")
public final class RecipeController {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @GetMapping("/all")
    @ResponseBody
    @Operation(description = "Returns all recipes stored in the repository", security = @SecurityRequirement(name="bearerAuth"))
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<RecipeDto>> getAll() {
        List<RecipeDto> recipesResponse = recipeMapper.toRecipeDtos(recipeService.getAllRecipes());
        return ResponseEntity.ok(recipesResponse);
    }

    @GetMapping()
    @ResponseBody
    @Operation(description = "Returns all recipes stored in the repository for a user", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<List<RecipeDto>> getAllRecipesForUser(@RequestParam String username) {
        List<RecipeDto> recipesResponse = recipeMapper.toRecipeDtos(recipeService.getAllRecipesForUser(username));
        return ResponseEntity.ok(recipesResponse);
    }

    @GetMapping(value = "{id}/notes", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @Operation(description = "Returns the recipe notes for a recipe", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<String> getRecipeNotes(@PathVariable long id) {
        return ResponseEntity.ok(recipeService.getNotesForRecipe(id));
    }

    @GetMapping("/top5")
    @ResponseBody
    @Operation(description = "Returns top 5 rate recipes stored in the repository for a user", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<List<RecipeDto>> getTop5RatedRecipesForUser(@RequestParam String username) {
        List<RecipeDto> recipesResponse = recipeMapper.toRecipeDtos(recipeService.getTop5RatedRecipesForUser(username));
        return ResponseEntity.ok(recipesResponse);
    }

    @GetMapping("{id}")
    @ResponseBody
    @Operation(description = "Returns a recipe identified by the id", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        RecipeDto recipeResponse = recipeMapper.toRecipeDto(recipeService.getRecipeById(id));
        return ResponseEntity.ok(recipeResponse);
    }

    @PutMapping("{id}")
    @ResponseBody
    @Operation(description = "Updates a recipe identified by the id", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.toRecipe(recipeDto);
        RecipeDto updatedRecipeResponse = recipeMapper.toRecipeDto(recipeService.updateRecipe(id, recipe));
        return ResponseEntity.ok(updatedRecipeResponse);
    }

    @PutMapping("{id}/rating")
    @ResponseBody
    @Operation(description = "Update the recipe rating", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<RecipeDto> updateRating(@RequestBody UpdateRatingRequest updateRatingRequest) {
        Recipe updateRecipe = recipeMapper.toRecipe(updateRatingRequest.getRecipeDto());
        Short rating = updateRatingRequest.getRating();
        RecipeDto updatedRecipeResponse = recipeMapper.toRecipeDto(
          recipeService.updateRating(updateRecipe, rating)
        );
        return ResponseEntity.ok(updatedRecipeResponse);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Deletes a recipe identified by the id", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
