package brews.controllers;

import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;
import brews.domain.dto.UpdateRatingRequest;
import brews.mapper.domain.RecipeMapper;
import brews.services.RecipeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
    private final RecipeMapper recipeMapper;

    public RecipeController(RecipeService recipeService, RecipeMapper recipeMapper) {
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
    }

    @GetMapping("/all")
    @ApiOperation(value="Returns all recipes stored in the repository", authorizations = { @Authorization(value="jwtToken")})
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<RecipeDto>> getAll() {
        List<RecipeDto> recipesResponse = recipeMapper.toRecipeDtos(recipeService.getAllRecipes());
        return ResponseEntity.ok(recipesResponse);
    }

    @GetMapping()
    @ApiOperation(value="Returns all recipes stored in the repository for a user", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<List<RecipeDto>> getAllRecipesForUser(@RequestParam String username) {
        List<RecipeDto> recipesResponse = recipeMapper.toRecipeDtos(recipeService.getAllRecipesForUser(username));
        return ResponseEntity.ok(recipesResponse);
    }

    @GetMapping("/top5")
    @ApiOperation(value="Returns top 5 rate recipes stored in the repository for a user", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<List<RecipeDto>> getTop5RatedRecipesForUser(@RequestParam String username) {
        List<RecipeDto> recipesResponse = recipeMapper.toRecipeDtos(recipeService.getTop5RatedRecipesForUser(username));
        return ResponseEntity.ok(recipesResponse);
    }

    @GetMapping("{id}")
    @ApiOperation(value="Returns a recipe identified by the id",  authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        RecipeDto recipeResponse = recipeMapper.toRecipeDto(recipeService.getRecipeById(id));
        return ResponseEntity.ok(recipeResponse);
    }

    @PutMapping("{id}")
    @ApiOperation(value="Updates a recipe identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.toRecipe(recipeDto);
        RecipeDto updatedRecipeResponse = recipeMapper.toRecipeDto(recipeService.updateRecipe(id, recipe));
        return ResponseEntity.ok(updatedRecipeResponse);
    }

    @PutMapping("{id}/rating")
    @ApiOperation(value="Update the recipe rating", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<RecipeDto> updateRating(@RequestBody UpdateRatingRequest updateRatingRequest) {
        RecipeDto updatedRecipeResponse = recipeMapper.toRecipeDto(
          recipeService.updateRating(updateRatingRequest.getId(), updateRatingRequest.getRating())
        );
        return ResponseEntity.ok(updatedRecipeResponse);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value="Deletes a recipe identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
