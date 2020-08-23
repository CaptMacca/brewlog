package brews.controllers;

import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.beerxml.ImportedRecipes;
import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeExistsException;
import brews.exceptions.ImportedRecipeUploadException;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.mapper.domain.RecipeMapper;
import brews.services.ImportRecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/recipes")
@CrossOrigin(origins = "*")
@Api("API for uploading a recipe in beer xml format, note this only supports beer.xml v1 files")
public final class UploadRecipeController {

    private final ImportRecipeService importRecipeService;
    private final BeerXMLRecipeMapper beerXMLRecipeMapper;
    private final RecipeMapper recipeMapper;

    public UploadRecipeController(ImportRecipeService importRecipeService, BeerXMLRecipeMapper beerXMLRecipeMapper, RecipeMapper recipeMapper) {
        this.importRecipeService = importRecipeService;
        this.beerXMLRecipeMapper = beerXMLRecipeMapper;
        this.recipeMapper = recipeMapper;
    }

    @PostMapping("upload")
    @ApiOperation(
      value="Handles the upload of a beer.xml files, will fail if a recipe with the same name already exists in the repository",
      authorizations = { @Authorization(value="jwtToken") })
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
            List<Recipe> recipes = convertBeerXmlRecipes(fileContents);
            importRecipeService.importRecipes(recipes, user);
            return this.recipeMapper.toRecipeDtos(recipes);
        } catch (IOException e) {
            throw new ImportedRecipeUploadException("Exception encountered importing the uploaded",e);
        }
    }

    private ImportedRecipes readBeerXML(InputStream contents) {

        ImportedRecipes importedRecipes = null;

        try {
            log.debug("Unmarshalling the beerxml file");
            JAXBContext jaxbContext = JAXBContext.newInstance(ImportedRecipes.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            importedRecipes = (ImportedRecipes) jaxbUnmarshaller.unmarshal(contents);

            log.debug("Beerxml has been unmarshalled");
            if ((importedRecipes != null) && (importedRecipes.getImportedRecipes() != null)) {
                log.debug("Found " + importedRecipes.getImportedRecipes().size() + " recipes in the beer xml file.");
            }
        } catch (JAXBException e) {
            log.error("Exception unmarshalling the beerxml file");
        }

        return importedRecipes;
    }

    private List<Recipe> convertBeerXmlRecipes(InputStream contents) {

        log.debug("Importing beerxml file ");
        ImportedRecipes importedRecipes = readBeerXML(contents);

         new ArrayList<>();

        log.debug("Saving recipes in database");
        List<ImportedRecipe> candidateRecipes = importedRecipes.getImportedRecipes();
        List<Recipe> recipes = candidateRecipes.stream().map(beerXMLRecipeMapper::map).collect(Collectors.toList());
        return recipes;
    }
}
