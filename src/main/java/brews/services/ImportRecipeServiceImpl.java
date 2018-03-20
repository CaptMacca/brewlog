package brews.services;

import brews.domain.Recipe;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.beerxml.ImportedRecipes;
import brews.exceptions.ImportRecipeServiceException;
import brews.exceptions.RecipeServiceException;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service to import a BeerXML recipe file and transform and map the important bits for the
 * BrewLog App.
 */
@Slf4j
@Service
public class ImportRecipeServiceImpl implements ImportRecipeService {

    private final RecipeRepository recipeRepository;
    private final BeerXMLRecipeMapper beerXMLRecipeMapper;
    private final BeerXMLReaderService beerXMLReaderService;

    @Autowired
    public ImportRecipeServiceImpl(RecipeRepository recipeRepository, BeerXMLRecipeMapper beerXMLRecipeMapper, BeerXMLReaderService beerXMLReaderService) {
        this.recipeRepository = recipeRepository;
        this.beerXMLRecipeMapper = beerXMLRecipeMapper;
        this.beerXMLReaderService = beerXMLReaderService;
    }

    /**
     * Import, transform and save the recipes in the xml file into our DB.
     */
    @Override
    public List<Recipe> importBeerXml(InputStream contents) throws RecipeServiceException {

        log.debug("Importing beerxml file ");
        ImportedRecipes importedRecipes = beerXMLReaderService.readBeerXML(contents);

        List<Recipe> recipes = new ArrayList<>();

        log.debug("Saving recipes in database");
        List<ImportedRecipe> candidateRecipes = importedRecipes.getImportedRecipes();

        for (ImportedRecipe importedRecipe : candidateRecipes) {

            Recipe candidateRecipe = beerXMLRecipeMapper.map(importedRecipe);

            Optional<Recipe> existingRecipe = recipeRepository.findRecipeByName(candidateRecipe.getName());
            if (existingRecipe.isPresent()) {
                // Update an already uploaded recipe
                log.error("Recipe already exists");
                throw new ImportRecipeServiceException("Recipe already has been uploaded");
            } else {
                log.debug("Saving recipe: " + candidateRecipe.getName());
                recipeRepository.save(candidateRecipe);
            }
            recipes.add(candidateRecipe);
        }

        recipeRepository.flush();

        return recipes;
    }

}
