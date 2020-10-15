package brews.util.transformer.beerxml.mapping;

import brews.domain.Ingredient;
import brews.domain.Mash;
import brews.domain.Recipe;
import brews.util.transformer.beerxml.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Maps the deserialise beer xml domain to the brew log domain
 */
@Component
public class BeerXMLRecipeMapper {

    private final ImportFermentablesMapper importFermentablesMapper;
    private final ImportHopMapper importHopMapper;
    private final ImportYeastMapper importYeastMapper;
    private final ImportMashMapper importMashMapper;

    @Autowired
    public BeerXMLRecipeMapper(ImportFermentablesMapper importFermentablesMapper, ImportHopMapper importHopMapper, ImportYeastMapper importYeastMapper, ImportMashMapper importMashMapper) {
        this.importFermentablesMapper = importFermentablesMapper;
        this.importHopMapper = importHopMapper;
        this.importYeastMapper = importYeastMapper;
        this.importMashMapper = importMashMapper;
    }

    public Recipe map(ImportedRecipe source) {

        Recipe recipe = new Recipe();

        recipe.setEstimatedABV(source.getEstimatedAbv());
        recipe.setName(source.getName());
        recipe.setType(source.getType());
        recipe.setEstimatedColour(source.getEstimatedColour());
        recipe.setBatchSize(source.getDisplayBatchSize());
        recipe.setBoilTime(String.valueOf(source.getBoilTime()));
        recipe.setOriginalGravity(source.getDisplayOriginalGravity());
        recipe.setFinalGravity(source.getDisplayFinalGravity());
        recipe.setIbu(source.getIbu());
        recipe.setNotes(source.getNotes());

        if (source.getImportedStyle() != null) {
            recipe.setStyle(source.getImportedStyle().getName());
        }

        List<Ingredient> ingredients = new ArrayList<>();
        List<Mash> mashes = new ArrayList<>();

        List<ImportedFermentable> _fermentables = source.getImportedFermentables();
        List<ImportedHop> _hops = source.getImportedHops();
        List<ImportedYeast> _yeasts = source.getImportedYeasts();
        ImportedMash _mashes = source.getImportedMash();


        if (_fermentables != null) {
            ingredients.addAll(importFermentablesMapper.map(_fermentables, recipe));
        }

        if (_hops != null) {
            ingredients.addAll(importHopMapper.map(_hops, recipe));
        }

        if (_yeasts != null) {
            ingredients.addAll(importYeastMapper.map(_yeasts, recipe));
        }

        if (_mashes != null) {
            mashes.addAll(importMashMapper.map(_mashes, recipe));
        }

        Set<Ingredient> importedIngredients = new HashSet<>(ingredients);

        Set<Mash> importedMashes = new HashSet<>(mashes);

        recipe.setIngredients(importedIngredients);
        recipe.setMashes(importedMashes);

        return recipe;
    }


}
