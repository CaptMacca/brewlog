package brews.mapper.domain;

import brews.domain.Mash;
import brews.domain.dto.MashDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MashMapper {

    MashDto toMashDto(Mash mash);
    List<MashDto> toMashDtos(List<Mash> mashes);

    @InheritInverseConfiguration
    Mash toMash(MashDto mashDto);

    @InheritInverseConfiguration
    List<Mash> toMashes(List<MashDto> mashDtos);

}