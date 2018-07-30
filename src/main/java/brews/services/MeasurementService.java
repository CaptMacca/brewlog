package brews.services;

import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;

import java.util.List;

public interface MeasurementService {

    List<MeasurementTypeDto> getMeasurementTypes();

    MeasurementDto getMeasurement(Long id);

    List<MeasurementDto> getMeasurementsForBrew(Long id);

    MeasurementDto createMeasurement(MeasurementDto measurementDto);

    MeasurementDto updateMeasurement(Long id, MeasurementDto measurementDto);

    void deleteMeasurement(Long measurementId);
}
