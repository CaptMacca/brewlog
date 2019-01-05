package brews.mapper.domain;

import brews.domain.Brewer;
import brews.domain.dto.BrewerDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrewerMapper {

    BrewerDto toBrewerDto(Brewer brewer);
    List<BrewerDto> toBrewerDtos(List<Brewer> brewers);

    @InheritInverseConfiguration
    Brewer toBrewer(BrewerDto brewerDto);

    @InheritInverseConfiguration
    List<Brewer> toBrewers(List<BrewerDto> brewerDtos);

    void updateFromBrewDto(BrewerDto brewerDto, @MappingTarget Brewer brewer);
}
