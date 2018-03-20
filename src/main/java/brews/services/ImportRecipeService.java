package brews.services;

import brews.domain.Recipe;
import brews.exceptions.RecipeServiceException;

import java.io.InputStream;
import java.util.List;

public interface ImportRecipeService {
    List<Recipe> importBeerXml(InputStream contents) throws RecipeServiceException;
}
