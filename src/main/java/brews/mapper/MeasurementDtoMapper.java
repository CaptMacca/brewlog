package brews.mapper;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.domain.dto.MeasurementDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MeasurementDtoMapper {

    public MeasurementDto map(Measurement measurement) {
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(measurement.getId());
        measurementDto.setType(measurement.getType().name());
        measurementDto.setMeasurementDate(measurement.getMeasurementDate());
        measurementDto.setValue(measurement.getValue());
        measurementDto.setTypeDescription(measurement.getType().toString());

        Brew brew = measurement.getBrew();
        if (brew!=null) {
            measurementDto.setBrewId(brew.getId());
        }

        return measurementDto;
    }

    public List<MeasurementDto> map(List<Measurement> measurements) {
        return measurements.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());

    }
}
