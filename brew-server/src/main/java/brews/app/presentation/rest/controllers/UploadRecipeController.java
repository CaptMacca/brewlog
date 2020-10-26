package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.recipe.RecipeDto;
import brews.domain.Recipe;
import brews.domain.exceptions.ImportedRecipeUploadException;
import brews.domain.mapper.RecipeMapper;
import brews.services.ImportRecipeService;
import brews.util.transformer.beerxml.BeerXMLTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Upload Recipe Api
 */
@Slf4j
@RestController
@RequestMapping("api/recipes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name="Recipes", description = "API for uploading a recipe in beer xml format, note this only supports beer.xml v1 files")
public final class UploadRecipeController {

    private final ImportRecipeService importRecipeService;
    private final RecipeMapper recipeMapper;
    private final BeerXMLTransformer beerXMLTransformer;

    @PostMapping("upload")
    @ResponseBody
    @Operation(
      description="Handles the upload of a beer.xml files, will fail if a recipe with the same name already exists in the repository",
      security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<List<RecipeDto>> uploadRecipe(@RequestParam("files") MultipartFile[] uploadfiles, @RequestParam("user") String user) {
        List<RecipeDto> recipes = new ArrayList<>();

        for (MultipartFile file : uploadfiles) {
           // Each xml file can contain multiple recipes
           recipes.addAll(uploadRecipe(file, user));
        }

        return new ResponseEntity<>(recipes, HttpStatus.CREATED);
    }

    private List<RecipeDto> uploadRecipe(MultipartFile uploadfile, String user) {
        log.debug("File upload requested.");
        if (uploadfile.isEmpty()) {
            throw new IllegalArgumentException("File to be uploaded is empty ?");
        }

        if (!uploadfile.getOriginalFilename().endsWith(".xml")) {
            throw new IllegalArgumentException("File is not a beer xml file.");
        }

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User has not been supplied.");
        }

        try {
            InputStream fileContents = uploadfile.getInputStream();
            List<Recipe> recipes = beerXMLTransformer.convertBeerXmlRecipes(fileContents);
            importRecipeService.importRecipes(recipes, user);
            return this.recipeMapper.toRecipeDtos(recipes);
        } catch (IOException e) {
            throw new ImportedRecipeUploadException("Exception encountered importing the uploaded",e);
        }
    }

}
