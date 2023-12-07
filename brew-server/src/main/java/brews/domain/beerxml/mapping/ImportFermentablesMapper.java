package brews.domain.beerxml.mapping;

import brews.domain.Fermentable;
import brews.domain.Recipe;
import brews.domain.beerxml.model.ImportedFermentable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Maps to beer xml fermentable object to the Fermentable entity object
 */
@Component
public class ImportFermentablesMapper {

    public List<Fermentable> map(List<ImportedFermentable> importedFermentables, Recipe recipe) {
        return importedFermentables.stream()
                .filter(Objects::nonNull)
                .map(importedFermentable -> map(importedFermentable, recipe))
                .collect(Collectors.toList());
    }

    public Fermentable map(ImportedFermentable importedFermentable, Recipe recipe) {
        Fermentable fermentable = new Fermentable();
        fermentable.setName(importedFermentable.getName());
        fermentable.setAmount(importedFermentable.getAmount());
        fermentable.setAddAfterBoil(Boolean.valueOf(importedFermentable.getAddAfterBoil()));
        fermentable.setRecipe(recipe);
        return fermentable;
    }
}
