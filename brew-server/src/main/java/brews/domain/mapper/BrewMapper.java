package brews.domain.mapper;

import brews.app.presentation.dto.brew.BrewDto;
import brews.app.presentation.dto.brew.UpdateBrewDto;
import brews.domain.Brew;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses={RecipeMapper.class,IngredientMapper.class, MeasurementMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrewMapper {

    @Mappings({
      @Mapping(source = "recipe.id", target = "recipeId"),
      @Mapping(source = "recipe.name", target = "recipeName"),
      @Mapping(source = "user.username", target = "username"),
    })
    BrewDto toBrewDto(Brew brew);

    List<BrewDto> toBrewDtos(List<Brew> brews);

    UpdateBrewDto toUpdateBrewDto(Brew brew);
    List<UpdateBrewDto> toUpdateBrewDtos(List<Brew> brews);

    @InheritInverseConfiguration
    Brew toBrew(BrewDto brewDto);

    @InheritInverseConfiguration
    Brew updateBrewDtotoBrew(UpdateBrewDto brewDto);

    @InheritInverseConfiguration
    List<Brew> updateBrewDtostoBrews(List<BrewDto> brewDtos);

    @InheritInverseConfiguration
    List<Brew> toBrews(List<UpdateBrewDto> updateBrewDtos);

    void updateFromBrewDto(UpdateBrewDto brewDto, @MappingTarget Brew brew);

}
