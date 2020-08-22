package brews.mapper.domain;

import brews.domain.Brew;
import brews.domain.dto.BrewDto;
import brews.domain.dto.UpdateBrewRequest;
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

    UpdateBrewRequest toUpdateBrewDto(Brew brew);
    List<UpdateBrewRequest> toUpdateBrewDtos(List<Brew> brews);

    @InheritInverseConfiguration
    Brew toBrew(BrewDto brewDto);

    @InheritInverseConfiguration
    Brew updateBrewDtotoBrew(UpdateBrewRequest brewDto);

    @InheritInverseConfiguration
    List<Brew> updateBrewDtostoBrews(List<BrewDto> brewDtos);

    @InheritInverseConfiguration
    List<Brew> toBrews(List<UpdateBrewRequest> updateBrewRequests);

    void updateFromBrewDto(UpdateBrewRequest brewDto, @MappingTarget Brew brew);

}
