package brews.controllers;

import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;
import brews.exceptions.RecipeServiceException;
import brews.services.ImportRecipeService;
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
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class UploadRecipeController {

    private final ImportRecipeService importRecipeService;

    public UploadRecipeController(ImportRecipeService importRecipeService) {
        this.importRecipeService = importRecipeService;
    }

    @PostMapping("/recipes/upload")
    public ResponseEntity<?> uploadRecipe(@RequestParam("file") MultipartFile uploadfile) {

        log.debug("File upload requested.");
        if (uploadfile.isEmpty()) {
            log.error("File to be uploaded is empty ?");
            return new ResponseEntity<>("Please upload a file", HttpStatus.BAD_REQUEST);
        }

        if (!uploadfile.getOriginalFilename().endsWith(".xml")) {
            log.error("File is not a beer xml file.");
            return new ResponseEntity<>("Please load a beersmith .xml file", HttpStatus.BAD_REQUEST);
        }

        List<RecipeDto> recipes = new ArrayList<>();
        try {
            InputStream fileContents = uploadfile.getInputStream();
            if (fileContents != null) {
                recipes = importRecipeService.importBeerXml(fileContents);
            }

        } catch (IOException e) {
            log.error("IOException uploading file:", e);
            return new ResponseEntity<>("Upload failed due to IO problems", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RecipeServiceException e) {
            String msg = "Recipe of same name already exists in recipe database";
            log.warn(msg);
            return new ResponseEntity<Object>(msg, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(recipes, HttpStatus.CREATED);
    }
}
