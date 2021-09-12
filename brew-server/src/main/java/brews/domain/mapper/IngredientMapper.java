package brews.domain.mapper;

import brews.app.presentation.dto.recipe.FermentableDto;
import brews.app.presentation.dto.recipe.HopDto;
import brews.app.presentation.dto.recipe.IngredientDto;
import brews.app.presentation.dto.recipe.YeastDto;
import brews.domain.Fermentable;
import brews.domain.Hop;
import brews.domain.Ingredient;
import brews.domain.Yeast;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class IngredientMapper {

    public abstract HopDto toHopDto(Hop hop);
    public abstract List<HopDto> toHopDtos(List<Hop> hops);

    @InheritInverseConfiguration
    public abstract Hop toHop(HopDto hopDto);

    @InheritInverseConfiguration
    public abstract List<Hop> toHops(List<HopDto> hopDtos);

    public abstract YeastDto toYeastDto(Yeast yeast);
    public abstract List<YeastDto> toYeastDtos(List<Yeast> yeasts);

    @InheritInverseConfiguration
    public abstract Yeast toYeast(YeastDto yeastDto);

    @InheritInverseConfiguration
    public abstract List<Yeast> toYeasts(List<YeastDto> yeastDtos);

    public abstract FermentableDto toFermentableDto(Fermentable fermentable);
    public abstract List<FermentableDto> toFermentableDtos(List<Fermentable> fermentables);

    @InheritInverseConfiguration
    public abstract Fermentable toFermentable(FermentableDto fermentableDto);

    @InheritInverseConfiguration
    public abstract List<Fermentable> toFermentables(List<FermentableDto> fermentableDtos);

    public IngredientDto ingredientToIngredientDto(Ingredient ingredient) {

        if (ingredient instanceof Yeast) {
            return this.toYeastDto((Yeast) ingredient);
        } else if (ingredient instanceof Hop) {
            return this.toHopDto((Hop) ingredient);
        } else {
            return this.toFermentableDto((Fermentable) ingredient);
        }
    }

    @InheritInverseConfiguration
    public Ingredient ingredientDtoToIngredient(IngredientDto ingredientDto) {
        if (ingredientDto instanceof YeastDto) {
            return this.toYeast((YeastDto) ingredientDto);
        } else if (ingredientDto instanceof HopDto) {
            return this.toHop((HopDto) ingredientDto);
        } else {
            return this.toFermentable((FermentableDto) ingredientDto);
        }
    }
}
