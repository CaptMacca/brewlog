package brews.mapper.beerxml;

import brews.domain.Mash;
import brews.domain.Recipe;
import brews.domain.beerxml.ImportedMash;
import brews.domain.beerxml.ImportedMashStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ImportMashMapper {

    public List<Mash> map(ImportedMash importedMash, Recipe recipe) {

        if (importedMash != null) {
            List<ImportedMashStep> steps = importedMash.getImportedMashSteps();

            if (steps != null) {
                // Now return a converted list of mash steps
                return steps.stream()
                        .filter(Objects::nonNull)
                        .map(f -> map(f, recipe))
                        .collect(Collectors.toList());
            }
        }

        return new ArrayList<>();
    }

    public Mash map(ImportedMashStep importedMashStep, Recipe recipe) {
        Mash mash = new Mash();
        mash.setName(importedMashStep.getName());
        mash.setStepTemp(importedMashStep.getStepTemp());
        mash.setStepTime(importedMashStep.getStepTime());
        mash.setRecipe(recipe);

        return mash;
    }
}
