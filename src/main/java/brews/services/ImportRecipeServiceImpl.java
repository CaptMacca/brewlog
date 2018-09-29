package brews.services;

import brews.domain.Recipe;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.beerxml.ImportedRecipes;
import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeExistsException;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.mapper.domain.RecipeMapper;
import brews.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service to import a BeerXML recipe file and transform and mapHop the important bits for the
 * BrewLog App.
 */
@Slf4j
@Service
public class ImportRecipeServiceImpl implements ImportRecipeService {

    private final RecipeRepository recipeRepository;
    private final BeerXMLRecipeMapper beerXMLRecipeMapper;
    private final BeerXMLReaderService beerXMLReaderService;
    private final RecipeMapper recipeMapper;

    @Autowired
    public ImportRecipeServiceImpl(RecipeRepository recipeRepository, BeerXMLRecipeMapper beerXMLRecipeMapper, BeerXMLReaderService beerXMLReaderService, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.beerXMLRecipeMapper = beerXMLRecipeMapper;
        this.beerXMLReaderService = beerXMLReaderService;
        this.recipeMapper = recipeMapper;
    }

    /**
     * Import, transform and save the recipes in the xml file into our DB.
     */
    @Override
    @Transactional
    public List<RecipeDto> importBeerXml(InputStream contents) {

        log.debug("Importing beerxml file ");
        ImportedRecipes importedRecipes = beerXMLReaderService.readBeerXML(contents);

        List<Recipe> recipes = new ArrayList<>();

        log.debug("Saving recipes in database");
        List<ImportedRecipe> candidateRecipes = importedRecipes.getImportedRecipes();

        for (ImportedRecipe importedRecipe : candidateRecipes) {

            Recipe candidateRecipe = beerXMLRecipeMapper.map(importedRecipe);

            Optional<Recipe> existingRecipe = recipeRepository.findRecipeByName(candidateRecipe.getName());
            if (existingRecipe.isPresent()) {
                throw new ImportedRecipeExistsException("Recipe of same name already exists in recipe database");
            } else {
                log.debug(String.format("Saving recipe: %s",candidateRecipe.getName()));
                recipeRepository.save(candidateRecipe);
            }
            recipes.add(candidateRecipe);
        }

        recipeRepository.flush();

        return recipeMapper.toRecipeDtos(recipes);
    }

}
