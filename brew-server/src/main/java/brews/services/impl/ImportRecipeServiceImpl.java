package brews.services.impl;

import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.exceptions.ImportedRecipeExistsException;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.ImportRecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service to import a BeerXML recipe file and transform and mapHop the important bits for the
 * BrewLog App.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ImportRecipeServiceImpl implements ImportRecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    /**
     * Import, transform and save the recipes in the xml file into our DB.
     */
    @Transactional
    public List<Recipe> importRecipes(List<Recipe> candidateRecipes, String username) {

        log.debug("Retrieving user");
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

        log.debug("Saving recipes in database");
        List<Recipe> recipes = new ArrayList<>();

        // Check if a recipe has already been uploaded
        candidateRecipes.forEach(candidateRecipe -> {
            recipeRepository.findRecipeByNameAndUser(candidateRecipe.getName(), user)
              .ifPresentOrElse(
                recipe -> {
                  throw new ImportedRecipeExistsException("Recipe of same name already exists in recipe database for this user");
                },
                () -> {
                    log.debug(String.format("Saving recipe: %s", candidateRecipe.getName()));
                    candidateRecipe.setUser(user);
                    recipes.add(candidateRecipe);
                }
            );
        });

        recipeRepository.saveAllAndFlush(recipes);

        return candidateRecipes;
    }

}
