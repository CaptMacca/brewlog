package brews.mapper;

import brews.domain.*;
import brews.domain.dto.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RecipeDtoMapper {

    private final HopDtoMapper hopDtoMapper;
    private final FermentableDtoMapper fermentableDtoMapper;
    private final YeastDtoMapper yeastDtoMapper;
    private final MashDtoMapper mashDtoMapper;

    @Autowired
    public RecipeDtoMapper(HopDtoMapper hopDtoMapper,
                           FermentableDtoMapper fermentableDtoMapper,
                           YeastDtoMapper yeastDtoMapper,
                           MashDtoMapper mashDtoMapper) {
        this.hopDtoMapper = hopDtoMapper;
        this.fermentableDtoMapper = fermentableDtoMapper;
        this.yeastDtoMapper = yeastDtoMapper;
        this.mashDtoMapper = mashDtoMapper;
    }

    public RecipeDto map(Recipe recipe) {

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setStyle(recipe.getStyle());
        recipeDto.setType(recipe.getType());
        recipeDto.setBatchSize(recipe.getBatchSize());
        recipeDto.setEstimatedAbv(recipe.getEstimatedABV());
        recipeDto.setEstimatedColour(recipe.getEstimatedColour());
        recipeDto.setBoilTime(recipe.getBoilTime());
        recipeDto.setOriginalGravity(recipe.getOriginalGravity());
        recipeDto.setFinalGravity(recipe.getFinalGravity());
        recipeDto.setEstimatedIbu(recipe.getIbu());
        recipeDto.setNotes(recipe.getNotes());

        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients != null) {
            recipeDto.setHops(ingredients.stream()
                    .filter(Hop.class::isInstance)
                    .map(Hop.class::cast)
                    .map(hopDtoMapper::map)
                    .collect(Collectors.toList()));

            recipeDto.setFermentables(ingredients.stream()
                    .filter(Fermentable.class::isInstance)
                    .map(Fermentable.class::cast)
                    .map(fermentableDtoMapper::map)
                    .collect(Collectors.toList()));

            recipeDto.setYeasts(ingredients.stream()
                    .filter(Yeast.class::isInstance)
                    .map(Yeast.class::cast)
                    .map(yeastDtoMapper::map)
                    .collect(Collectors.toList()));
        }

        List<Mash> mashes = recipe.getMashes();
        if (mashes != null) {
            recipeDto.setMashes(mashes.stream()
                    .map(mashDtoMapper::map)
                    .collect(Collectors.toList()));
        }

        return recipeDto;
    }

    public List<RecipeDto> map(List<Recipe> recipes) {
        return recipes.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
