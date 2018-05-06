package brews.mapper;

import brews.domain.Measurement;
import brews.domain.MeasurementType;
import brews.domain.dto.MeasurementDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MeasurementMapper {

    public Measurement map(MeasurementDto measurementDto) {

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurement.setType(MeasurementType.valueOf(measurementDto.getType()));
        measurement.setMeasurementDate(measurementDto.getMeasurementDate());
        measurement.setValue(measurementDto.getValue());

        return measurement;
    }

    public List<Measurement> map(List<MeasurementDto> measurementDtos) {
        return measurementDtos.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());

    }
}
