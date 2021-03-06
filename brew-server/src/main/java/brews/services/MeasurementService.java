package brews.services;

import brews.domain.Measurement;

import java.util.List;

public interface MeasurementService {

    Measurement getMeasurement(Long id);

    List<Measurement> getMeasurementsForBrew(Long id);

    List<Measurement> saveMeasurements(List<Measurement> measurements);

    Measurement createMeasurement(Long brewId, Measurement measurement);

    Measurement updateMeasurement(Measurement measurement);

    void deleteMeasurement(Long measurementId);
}
