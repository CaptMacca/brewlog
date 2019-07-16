package brews.controllers;

import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeUploadException;
import brews.services.ImportRecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/recipes")
@CrossOrigin(origins = "*")
@Api("API for uploading a recipe in beer xml format, note this only supports beer.xml v1 files")
public final class UploadRecipeController {

    private final ImportRecipeService importRecipeService;

    public UploadRecipeController(ImportRecipeService importRecipeService) {
        this.importRecipeService = importRecipeService;
    }

    @PostMapping("upload")
    @ApiOperation("Handles the upload of a beer.xml file, will fail if a recipe with the same roleName already exists in the repository")
    public ResponseEntity<List<RecipeDto>> uploadRecipe(@RequestParam("file") MultipartFile uploadfile, @RequestParam("user") String user) {

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
            List<RecipeDto> recipes = new ArrayList<>();
            InputStream fileContents = uploadfile.getInputStream();
            if (fileContents != null) {
                recipes = importRecipeService.importBeerXml(fileContents, user);
            }

            return new ResponseEntity<>(recipes, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new ImportedRecipeUploadException("Exception encountered importing the uploaded",e);
        }
    }
}
