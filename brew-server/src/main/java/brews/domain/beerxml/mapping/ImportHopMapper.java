package brews.domain.beerxml.mapping;

import brews.domain.Hop;
import brews.domain.Recipe;
import brews.domain.beerxml.model.ImportedHop;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ImportHopMapper {

    public List<Hop> map(List<ImportedHop> importedHops, Recipe recipe) {
        return importedHops.stream()
                .filter(Objects::nonNull)
                .map(importedHop -> map(importedHop, recipe))
                .collect(Collectors.toList());
    }

    public Hop map(ImportedHop importedHop, Recipe recipe) {
        Hop hop = new Hop();
        hop.setName(importedHop.getName());
        hop.setAlpha(importedHop.getAlpha());
        hop.setHopUsage(importedHop.getUse());
        hop.setAmount(importedHop.getAmount());
        hop.setAdditionTime(importedHop.getTime());
        hop.setRecipe(recipe);

        return hop;
    }
}
