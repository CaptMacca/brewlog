package brews.util.transformer.beerxml.mapping;

import brews.domain.Recipe;
import brews.domain.Yeast;
import brews.util.transformer.beerxml.model.ImportedYeast;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ImportYeastMapper {

    public List<Yeast> map(List<ImportedYeast> importedYeasts, Recipe recipe) {
        return importedYeasts.stream()
                .filter(Objects::nonNull)
                .map(importedYeast -> map(importedYeast, recipe))
                .collect(Collectors.toList());
    }

    public Yeast map(ImportedYeast importedYeast, Recipe recipe) {
        Yeast yeast = new Yeast();
        yeast.setName(importedYeast.getName());
        yeast.setLaboratory(importedYeast.getLaboratory());
        yeast.setProductId(importedYeast.getProductId());
        yeast.setAmount(importedYeast.getAmount());
        yeast.setRecipe(recipe);

        return yeast;
    }
}
