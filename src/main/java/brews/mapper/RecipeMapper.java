package brews.mapper;

import brews.domain.Recipe;
import brews.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    private final HopMapper hopMapper;
    private final FermentableMapper fermentableMapper;
    private final YeastMapper yeastMapper;
    private final MashMapper mashMapper;

    @Autowired
    public RecipeMapper(HopMapper hopMapper,
                        FermentableMapper fermentableMapper,
                        YeastMapper yeastMapper,
                        MashMapper mashMapper) {
        this.hopMapper = hopMapper;
        this.fermentableMapper = fermentableMapper;
        this.yeastMapper = yeastMapper;
        this.mashMapper = mashMapper;
    }

    public Recipe map(RecipeDto recipeDto) {

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());
        recipe.setStyle(recipeDto.getStyle());
        recipe.setType(recipeDto.getType());
        recipe.setBatchSize(recipeDto.getBatchSize());
        recipe.setEstimatedABV(recipeDto.getEstimatedAbv());
        recipe.setEstimatedColour(recipeDto.getEstimatedColour());
        recipe.setBoilTime(recipeDto.getBoilTime());
        recipe.setOriginalGravity(recipeDto.getOriginalGravity());
        recipe.setFinalGravity(recipeDto.getFinalGravity());
        recipe.setIbu(recipeDto.getEstimatedIbu());
        recipe.setNotes(recipeDto.getNotes());

        List<HopDto> hopDtos = recipeDto.getHops();
        if (hopDtos != null) {
            hopDtos.stream()
                    .map(hopMapper::map)
                    .forEach(recipe::addIngredient);
        }

        List<FermentableDto> fermentableDtos = recipeDto.getFermentables();
        if (fermentableDtos != null) {
            fermentableDtos.stream()
                    .map(fermentableMapper::map)
                    .forEach(recipe::addIngredient);
        }

        List<YeastDto> yeastDtos = recipeDto.getYeasts();
        if (yeastDtos != null) {
            yeastDtos.stream()
                    .map(yeastMapper::map)
                    .forEach(recipe::addIngredient);
        }

        List<MashDto> mashDtos = recipeDto.getMashes();
        if (mashDtos != null) {
            mashDtos.stream()
                    .map(mashMapper::map)
                    .forEach(recipe::addMash);
        }

        return recipe;
    }

    public List<Recipe> map(List<RecipeDto> recipeDtos) {
        return recipeDtos.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
