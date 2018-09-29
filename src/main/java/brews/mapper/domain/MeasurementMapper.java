package brews.mapper.domain;

import brews.domain.Measurement;
import brews.domain.dto.MeasurementDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeasurementMapper {

    MeasurementDto toMeasurementDto(Measurement measurement);

    List<MeasurementDto> toMeasurementDtos(List<Measurement> measurements);

    @InheritInverseConfiguration
    Measurement toMeasurement(MeasurementDto measurementDto);

    @InheritInverseConfiguration
    List<Measurement> toMeasurements(List<MeasurementDto> measurementDtos);

    void updateFromMeasurementDto(MeasurementDto measurementDto, @MappingTarget Measurement measurement);

}
