package brews.mapper.domain;

import brews.domain.Brew;
import brews.domain.dto.BrewDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        uses={IngredientMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrewMapper {

    BrewDto toBrewDto(Brew brew);
    List<BrewDto> toBrewDtos(List<Brew> brews);

    @InheritInverseConfiguration
    Brew toBrew(BrewDto brewDto);

    @InheritInverseConfiguration
    List<Brew> toBrews(List<BrewDto> brewDtos);

    void updateFromBrewDto(BrewDto brewDto, @MappingTarget Brew brew);

}
