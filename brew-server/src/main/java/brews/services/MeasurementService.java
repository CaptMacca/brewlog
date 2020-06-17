package brews.services;

import brews.domain.Measurement;
import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;

import java.util.List;

public interface MeasurementService {

//    List<MeasurementTypeDto> getMeasurementTypes();

    MeasurementDto getMeasurement(Long id);

    List<MeasurementDto> getMeasurementsForBrew(Long id);

    List<MeasurementDto> saveMeasurements(List<MeasurementDto> measurements);

    MeasurementDto createMeasurement(MeasurementDto measurementDto);

    MeasurementDto updateMeasurement(MeasurementDto measurementDto);

    void deleteMeasurement(Long measurementId);
}
