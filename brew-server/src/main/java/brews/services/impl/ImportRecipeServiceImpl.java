package brews.services.impl;

import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.beerxml.ImportedRecipe;
import brews.domain.beerxml.ImportedRecipes;
import brews.domain.dto.RecipeDto;
import brews.exceptions.ImportedRecipeExistsException;
import brews.mapper.beerxml.BeerXMLRecipeMapper;
import brews.mapper.domain.RecipeMapper;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.BeerXMLReaderService;
import brews.services.ImportRecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserRepository userRepository;
    private final BeerXMLRecipeMapper beerXMLRecipeMapper;
    private final BeerXMLReaderService beerXMLReaderService;
    private final RecipeMapper recipeMapper;

    @Autowired
    public ImportRecipeServiceImpl(RecipeRepository recipeRepository, BeerXMLRecipeMapper beerXMLRecipeMapper, BeerXMLReaderService beerXMLReaderService, RecipeMapper recipeMapper, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.beerXMLRecipeMapper = beerXMLRecipeMapper;
        this.beerXMLReaderService = beerXMLReaderService;
        this.recipeMapper = recipeMapper;
        this.userRepository = userRepository;
    }

    /**
     * Import, transform and save the recipes in the xml file into our DB.
     */
    @Override
    @Transactional
    public List<RecipeDto> importBeerXml(InputStream contents, String username) {

        log.debug("Retrieving user");
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));;

        log.debug("Importing beerxml file ");
        ImportedRecipes importedRecipes = beerXMLReaderService.readBeerXML(contents);

        List<Recipe> recipes = new ArrayList<>();

        log.debug("Saving recipes in database");
        List<ImportedRecipe> candidateRecipes = importedRecipes.getImportedRecipes();
        candidateRecipes.stream().map(beerXMLRecipeMapper::map).forEach(candidateRecipe -> {
            candidateRecipe.setUser(user);
            Optional<Recipe> existingRecipe = recipeRepository.findRecipeByNameAndUser(candidateRecipe.getName(), user);
            if (existingRecipe.isPresent()) {
                throw new ImportedRecipeExistsException("Recipe of same name already exists in recipe database for this user");
            } else {
                log.debug(String.format("Saving recipe: %s", candidateRecipe.getName()));
                recipeRepository.save(candidateRecipe);
            }
            recipes.add(candidateRecipe);
        });

        recipeRepository.flush();

        return recipeMapper.toRecipeDtos(recipes);
    }

}
