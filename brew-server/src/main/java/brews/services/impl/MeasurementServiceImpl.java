package brews.services.impl;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.domain.exceptions.BrewsEntityNotFoundException;
import brews.infrastructure.data.jpa.repository.BrewsRepository;
import brews.infrastructure.data.jpa.repository.MeasurementRepository;
import brews.services.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final BrewsRepository brewsRepository;

    @Override
    @Transactional
    public Measurement getMeasurement(Long id) {
        log.debug(String.format("Retrieve measurement with id: %d", id));
        return Optional.of(measurementRepository.getOne(id)).orElseThrow(
          () -> new BrewsEntityNotFoundException(String.format("Measurement for id: %d could not be found.", id))
        );
    }

    @Override
    @Transactional
    public List<Measurement> getMeasurementsForBrew(Long id) {
        log.debug(String.format("Retrieving measurements for brew id : %d", id));
        return measurementRepository.findMeasurementsByBrewId(id);
    }

    @Override
    @Transactional
    public List<Measurement> saveMeasurements(List<Measurement> measurements) {
        List<Measurement> savedMeasurements = new ArrayList<>();
        for (Measurement measurement: measurements) {
            Long measurementId = measurement.getId();
            Measurement savedMeasurement = null;
            if (measurementId == null || measurementId <= 0) {
                savedMeasurement = createMeasurement(measurement.getBrew().getId(), measurement);
            } else {
                savedMeasurement = updateMeasurement(measurement);
            }
            savedMeasurements.add(savedMeasurement);
        }
        return savedMeasurements;
    }

    @Override
    @Transactional
    public Measurement createMeasurement(Long brewId, Measurement measurement) {
        log.info("Saving measurement: " + measurement.toString());
        Brew brew =
           Optional.of(brewsRepository.getOne(brewId)).orElseThrow(
                () -> new BrewsEntityNotFoundException(String.format("Brew could not be found for brew id: %d", brewId)));
        return measurementRepository.saveAndFlush(measurement);
    }


    @Override
    @Transactional
    public Measurement updateMeasurement(Measurement measurement) {
        log.info("Saving measurement: " + measurement.toString());
        Measurement attachedMeasurement = measurementRepository.getOne(measurement.getId());
        BeanUtils.copyProperties(measurement, attachedMeasurement);
        return measurementRepository.save(attachedMeasurement);
    }


    @Override
    @Transactional
    public void deleteMeasurement(Long id) {
        Measurement existingMeasurement = getMeasurement(id);
        measurementRepository.delete(existingMeasurement);
    }

}
