package brews.mapper.domain;

import brews.domain.*;
import brews.domain.dto.FermentableDto;
import brews.domain.dto.HopDto;
import brews.domain.dto.RecipeDto;
import brews.domain.dto.YeastDto;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses= {IngredientMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RecipeMapper {

    @Mappings({
            @Mapping(source = "estimatedABV", target = "estimatedAbv"),
            @Mapping(source = "ibu", target = "estimatedIbu"),
    })
    public abstract RecipeDto toRecipeDto(Recipe recipe);

    public abstract List<RecipeDto> toRecipeDtos(List<Recipe> recipes);

    @InheritInverseConfiguration
    public abstract Recipe toRecipe(RecipeDto recipeDto);

    @InheritInverseConfiguration
    public abstract List<Recipe> toRecipes(List<RecipeDto> recipeDtos);

    public abstract void updateFromRecipeDto(RecipeDto recipeDto, @MappingTarget Recipe recipe);

    @AfterMapping
    public void afterDtoMapping(final Recipe recipe, @MappingTarget final RecipeDto recipeDto) {
        Set<FermentableDto> fermentableDtos =
          recipe.getIngredients()
                .stream()
                .filter(Fermentable.class::isInstance)
                .map(Fermentable.class::cast)
                .map(f -> new IngredientMapperImpl().toFermentableDto(f))
                .collect(Collectors.toSet());

        Set<HopDto> hopDtos =
          recipe.getIngredients()
            .stream()
            .filter(Hop.class::isInstance)
            .map(Hop.class::cast)
            .sorted(Comparator.comparing(Hop::getAdditionTime))
            .map(h -> new IngredientMapperImpl().toHopDto(h))
            .collect(Collectors.toSet());

        Set<YeastDto> yeastDtos =
          recipe.getIngredients()
            .stream()
            .filter(Yeast.class::isInstance)
            .map(Yeast.class::cast)
            .map(y -> new IngredientMapperImpl().toYeastDto(y))
            .collect(Collectors.toSet());


        recipeDto.setFermentables(fermentableDtos);
        recipeDto.setHops(hopDtos);
        recipeDto.setYeasts(yeastDtos);
    }
}
