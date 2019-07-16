package brews.mapper.domain;

import brews.domain.Fermentable;
import brews.domain.Hop;
import brews.domain.Ingredient;
import brews.domain.Yeast;
import brews.domain.dto.FermentableDto;
import brews.domain.dto.HopDto;
import brews.domain.dto.IngredientDto;
import brews.domain.dto.YeastDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {

    HopDto toHopDto(Hop hop);
    List<HopDto> toHopDtos(List<Hop> hops);

    @InheritInverseConfiguration
    Hop toHop(HopDto hopDto);

    @InheritInverseConfiguration
    List<Hop> toHops(List<HopDto> hopDtos);

    YeastDto toYeastDto(Yeast yeast);
    List<YeastDto> toYeastDtos(List<Yeast> yeasts);

    @InheritInverseConfiguration
    Yeast toYeast(YeastDto yeastDto);

    @InheritInverseConfiguration
    List<Yeast> toYeasts(List<YeastDto> yeastDtos);

    FermentableDto toFermentableDto(Fermentable fermentable);
    List<FermentableDto> toFermentableDtos(List<Fermentable> fermentables);

    @InheritInverseConfiguration
    Fermentable toFermentable(FermentableDto fermentableDto);

    @InheritInverseConfiguration
    List<Fermentable> toFermentables(List<FermentableDto> fermentableDtos);

    default IngredientDto ingredientToIngredientDto(Ingredient ingredient) {

        if (ingredient instanceof Yeast) {
            return this.toYeastDto((Yeast) ingredient);
        } else if (ingredient instanceof Hop) {
            return this.toHopDto((Hop) ingredient);
        } else {
            return this.toFermentableDto((Fermentable) ingredient);
        }
    }

    @InheritInverseConfiguration
    default Ingredient ingredientDtoToIngredient(IngredientDto ingredientDto) {
        if (ingredientDto instanceof YeastDto) {
            return this.toYeast((YeastDto) ingredientDto);
        } else if (ingredientDto instanceof HopDto) {
            return this.toHop((HopDto) ingredientDto);
        } else {
            return this.toFermentable((FermentableDto) ingredientDto);
        }
    }
}
