package brews;

import brews.util.transformer.beerxml.model.ImportedRecipe;
import brews.util.transformer.beerxml.model.ImportedRecipes;

import java.util.ArrayList;
import java.util.List;

public class MockImportedRecipes {

    public static ImportedRecipes getImportedRecipes() {

        List<ImportedRecipe> recipes = new ArrayList<>();
        recipes.add(MockImportedRecipe.getImportedRecipe());
        ImportedRecipes importedRecipes = new ImportedRecipes();
        importedRecipes.setImportedRecipes(recipes);
        return importedRecipes;
    }
}
