package brews.mapper;

import brews.beerxml.*;
import brews.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Optional<List<ImportedFermentable>> _fermentables = Optional.ofNullable(source.getImportedFermentables());
        Optional<List<ImportedHop>> _hops = Optional.ofNullable(source.getImportedHops());
        Optional<List<ImportedYeast>> _yeasts = Optional.ofNullable(source.getImportedYeasts());
        Optional<ImportedMash> _mashes = Optional.ofNullable(source.getImportedMash());

        if (_fermentables.isPresent()) {
            ingredients.addAll(
                _fermentables.get()
                    .stream()
                    .collect(
                        ()-> new ArrayList<>(),
                        (list, importedFermentable) -> {
                            Fermentable fermentable = new Fermentable();
                            fermentable.setName(importedFermentable.getName());
                            fermentable.setAmount(importedFermentable.getDisplayAmount());
                            fermentable.setAddAfterBoil(Boolean.valueOf(importedFermentable.getAddAfterBoil()));
                            fermentable.setRecipe(dest);

                            list.add(fermentable);
                        },
                        (list1,list2) -> list1.addAll(list2)
                )
            );
        }

        if (_hops.isPresent()) {
            ingredients.addAll(
                _hops.get()
                    .stream()
                    .collect(
                        () -> new ArrayList<>(),
                        (list, importedHop) -> {
                            Hop hop = new Hop();
                            hop.setName(importedHop.getName());
                            hop.setAlpha(importedHop.getAlpha());
                            hop.setHopUsage(importedHop.getUse());
                            hop.setAmount(importedHop.getDisplayAmount());
                            hop.setAdditionTime(importedHop.getDisplayTime());
                            hop.setRecipe(dest);

                            list.add(hop);
                        },
                        (list1,list2) -> list1.addAll(list2)
                    )
            );

        }

        if (_yeasts.isPresent()) {
            ingredients.addAll(
                    _yeasts.get()
                        .stream()
                        .collect(
                            () -> new ArrayList<>(),
                            (list, importedYeast) -> {
                                Yeast yeast = new Yeast();
                                yeast.setName(importedYeast.getName());
                                yeast.setLaboratory(importedYeast.getLaboratory());
                                yeast.setProductId(importedYeast.getProductId());
                                yeast.setAmount(importedYeast.getDisplayAmount());
                                yeast.setRecipe(dest);

                                list.add(yeast);
                            },
                            (list1,list2) -> list1.addAll(list2)
                    )
            );
    }

        if (_mashes.isPresent()) {
            ImportedMash _importedMash = _mashes.get();
            Optional<List<ImportedMashStep>> _mashSteps
                    = Optional.ofNullable(_importedMash.getImportedMashSteps());

            if (_mashSteps.isPresent()) {
                mashes.addAll(
                        _mashSteps.get()
                                .stream()
                                .collect(
                                    () -> new ArrayList<>(),
                                    (list,importedMashStep) -> {
                                        Mash mash = new Mash();
                                        mash.setName(importedMashStep.getName());
                                        mash.setStepTemp(importedMashStep.getDisplayStepTemp());
                                        mash.setStepTime(importedMashStep.getStepTime());
                                        mash.setRecipe(dest);

                                        list.add(mash);
                                    },
                                    (list1,list2) -> list1.addAll(list2)
                                )
                );
            }
        }

        dest.setIngredients(ingredients);
        dest.setMashes(mashes);

        return dest;
    }
}
