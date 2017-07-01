package brews.mapper;

import brews.beerxml.*;
import brews.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 27/06/2017.
 */
@Component
public class RecipeMapper {

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

        List<Ingredient> ingredients = new ArrayList<>();
        List<Mash> mashes = new ArrayList<>();

        if (source.getImportedFermentables() != null) {

            for (ImportedFermentable importedFermentable : source.getImportedFermentables()) {
                Fermentable fermentable = new Fermentable();
                fermentable.setName(importedFermentable.getName());
                fermentable.setAmount(importedFermentable.getDisplayAmount());
                fermentable.setAddAfterBoil(Boolean.valueOf(importedFermentable.getAddAfterBoil()));
                //fermentable.setRecipe(dest);

                ingredients.add(fermentable);
            }
        }

        if (source.getImportedHops() != null) {

            for(ImportedHop importedHop : source.getImportedHops()) {
                Hop hop = new Hop();
                hop.setName(importedHop.getName());
                hop.setAlpha(importedHop.getAlpha());
                hop.setHopUsage(importedHop.getUse());
                hop.setAmount(importedHop.getDisplayAmount());
                hop.setAdditionTime(importedHop.getDisplayTime());
                //hop.setRecipe(dest);

                ingredients.add(hop);
            }
        }

        if (source.getImportedYeasts() != null) {
            for(ImportedYeast importedYeast: source.getImportedYeasts()) {
                Yeast yeast = new Yeast();
                yeast.setName(importedYeast.getName());
                yeast.setLaboratory(importedYeast.getLaboratory());
                yeast.setProductId(importedYeast.getProductId());
                yeast.setAmount(importedYeast.getDisplayAmount());
                //yeast.setRecipe(dest);

                ingredients.add(yeast);
            }
        }

        if (source.getImportedMash()!=null) {
            for(ImportedMashStep importedMashStep : source.getImportedMash().getImportedMashSteps()) {
                Mash mash = new Mash();
                mash.setName(importedMashStep.getName());
                mash.setStepTemp(importedMashStep.getDisplayStepTemp());
                mash.setStepTime(importedMashStep.getStepTime());

                mashes.add(mash);
            }
        }

        dest.setIngredients(ingredients);
        dest.setMashes(mashes);

        return dest;
    }
}
