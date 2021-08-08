package brews.domain.mapper;

import brews.domain.*;
import brews.app.presentation.dto.recipe.FermentableDto;
import brews.app.presentation.dto.recipe.HopDto;
import brews.app.presentation.dto.recipe.RecipeDto;
import brews.app.presentation.dto.recipe.YeastDto;
import org.mapstruct.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses= {IngredientMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RecipeMapper {

    @Mappings({
        @Mapping(source = "estimatedABV", target = "estimatedAbv"),
        @Mapping(source = "ibu", target = "estimatedIbu"),
        @Mapping(source = "user.username", target = "username")
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
        List<FermentableDto> fermentableDtos =
          recipe.getIngredients()
                .stream()
                .filter(Fermentable.class::isInstance)
                .map(Fermentable.class::cast)
                .map(f -> new IngredientMapperImpl().toFermentableDto(f))
                .collect(Collectors.toList());

        // Sort the hops by addition time desending
        SortedSet<HopDto> hopDtos;
        Comparator<HopDto> byAdditionTime= Comparator.comparing(HopDto::getAdditionTime).reversed();
        Supplier<TreeSet<HopDto>> supplier = () -> new TreeSet<>(byAdditionTime);

        hopDtos = recipe.getIngredients()
            .stream()
            .filter(Hop.class::isInstance)
            .map(Hop.class::cast)
            .map(h -> new IngredientMapperImpl().toHopDto(h))
            .collect(Collectors.toCollection(supplier));

        List<YeastDto> yeastDtos =
          recipe.getIngredients()
            .stream()
            .filter(Yeast.class::isInstance)
            .map(Yeast.class::cast)
            .map(y -> new IngredientMapperImpl().toYeastDto(y))
            .collect(Collectors.toList());


        recipeDto.setFermentables(fermentableDtos);
        recipeDto.setHops(hopDtos);
        recipeDto.setYeasts(yeastDtos);
    }
}
