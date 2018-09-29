package brews.mapper.domain;

import brews.domain.Measurement;
import brews.domain.dto.MeasurementDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeasurementMapper {

    @Mapping(source = "brew.id", target = "brewId")
    MeasurementDto toMeasurementDto(Measurement measurement);

    List<MeasurementDto> toMeasurementDtos(List<Measurement> measurements);

    @InheritInverseConfiguration
    Measurement toMeasurement(MeasurementDto measurementDto);

    @InheritInverseConfiguration
    List<Measurement> toMeasurements(List<MeasurementDto> measurementDtos);

    void updateFromMeasurementDto(MeasurementDto measurementDto, @MappingTarget Measurement measurement);

}
