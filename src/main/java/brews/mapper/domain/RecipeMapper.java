package brews.mapper.domain;

import brews.domain.Recipe;
import brews.domain.dto.RecipeDto;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring",
        uses= IngredientMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {

    @Mappings({
            @Mapping(source = "estimatedABV", target = "estimatedAbv"),
            @Mapping(source = "ibu", target = "estimatedIbu")
    })
    RecipeDto toRecipeDto(Recipe recipe);

    List<RecipeDto> toRecipeDtos(List<Recipe> recipes);

    @InheritInverseConfiguration
    Recipe toRecipe(RecipeDto recipeDto);

    @InheritInverseConfiguration
    List<Recipe> toRecipes(List<RecipeDto> recipeDtos);
}
