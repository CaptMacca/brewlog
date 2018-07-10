package brews.services;

import brews.domain.dto.RecipeDto;

import java.io.InputStream;
import java.util.List;

public interface ImportRecipeService {
    List<RecipeDto> importBeerXml(InputStream contents);
}

