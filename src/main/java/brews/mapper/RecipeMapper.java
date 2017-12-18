package brews.mapper;

import brews.beerxml.ImportedMash;
import brews.beerxml.ImportedRecipe;
import brews.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 27/06/2017.
 */
@Component("RecipeMapper")
public class RecipeMapper {

    public RecipeMapper() {
       super();
    }

    public Recipe map(ImportedRecipe source) {

        Recipe dest = new Recipe();

        dest.setEstimatedABV(source.getEstimatedAbv());
        dest.setName(source.getName());
        dest.setType(source.getType());
        dest.setEstimatedColour(source.getEstimatedColour());
        dest.setBatchSize(source.getDisplayBatchSize());
        dest.setBoilTime(String.valueOf(source.getBoilTime()));
        dest.setOriginalGravity(source.getDisplayOriginalGravity());
        dest.setFinalGravity(source.getDisplayFinalGravity());
        dest.setIbu(source.getIbu());
        dest.setNotes(source.getNotes());

        if (source.getImportedStyle()!=null) {
            dest.setStyle(source.getImportedStyle().getName());
        }

        List<Ingredient> ingredients = new ArrayList<>();
        List<Mash> mashes = new ArrayList<>();

        if (source.getImportedFermentables() != null) {
            source.getImportedFermentables()
                  .forEach(importedFermentable -> {
                      Fermentable fermentable = new Fermentable();
                      fermentable.setName(importedFermentable.getName());
                      fermentable.setAmount(importedFermentable.getDisplayAmount());
                      fermentable.setAddAfterBoil(Boolean.valueOf(importedFermentable.getAddAfterBoil()));
                      fermentable.setRecipe(dest);

                      ingredients.add(fermentable);
                  });

        }

        if (source.getImportedHops() != null) {
            source.getImportedHops()
                  .forEach(importedHop -> {
                      Hop hop = new Hop();
                      hop.setName(importedHop.getName());
                      hop.setAlpha(importedHop.getAlpha());
                      hop.setHopUsage(importedHop.getUse());
                      hop.setAmount(importedHop.getDisplayAmount());
                      hop.setAdditionTime(importedHop.getDisplayTime());
                      hop.setRecipe(dest);

                      ingredients.add(hop);
                    });

        }

        if (source.getImportedYeasts() != null) {
            source.getImportedYeasts()
                    .forEach(importedYeast -> {
                        Yeast yeast = new Yeast();
                        yeast.setName(importedYeast.getName());
                        yeast.setLaboratory(importedYeast.getLaboratory());
                        yeast.setProductId(importedYeast.getProductId());
                        yeast.setAmount(importedYeast.getDisplayAmount());
                        yeast.setRecipe(dest);

                        ingredients.add(yeast);
                    });
        }

        if (source.getImportedMash()!=null) {
            ImportedMash importedMash = source.getImportedMash();
            if (importedMash.getImportedMashSteps()!=null) {
                importedMash.getImportedMashSteps()
                        .forEach(importedMashStep -> {
                            Mash mash = new Mash();
                            mash.setName(importedMashStep.getName());
                            mash.setStepTemp(importedMashStep.getDisplayStepTemp());
                            mash.setStepTime(importedMashStep.getStepTime());
                            mash.setRecipe(dest);

                            mashes.add(mash);
                        });
            }
        }

        dest.setIngredients(ingredients);
        dest.setMashes(mashes);

        return dest;
    }
}
