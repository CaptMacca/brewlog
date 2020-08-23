package brews.services.impl;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.MeasurementMapper;
import brews.repository.BrewsRepository;
import brews.repository.MeasurementRepository;
import brews.services.MeasurementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final BrewsRepository brewsRepository;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, BrewsRepository brewsRepository) {
        this.measurementRepository = measurementRepository;
        this.brewsRepository = brewsRepository;
    }

    @Override
    @Transactional
    public Measurement getMeasurement(Long id) {
        log.debug(String.format("Retrieve measurement with id: %d", id));
        return measurementRepository.getOne(id);
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

        if (brewId == null) {
            throw new IllegalArgumentException("Measurement is not linked to a brew");
        } else {
            Brew brew = brewsRepository.getOne(brewId);
            if (brew == null) {
                throw new BrewsEntityNotFoundException(String.format("Brew could not be found for brew id: %d", brewId));
            } else {
                measurement.setBrew(brew);
            }
        }

        Measurement newMeasurement = measurementRepository.saveAndFlush(measurement);

        log.debug(String.format("Saved measurement: %s", newMeasurement.toString()));
        return newMeasurement;
    }


    @Override
    @Transactional
    public Measurement updateMeasurement(Measurement measurement) {

        log.info("Saving measurement: " + measurement.toString());

        Measurement savedMeasurement;

        if (measurement.getId() == null) {
            throw new IllegalArgumentException("Measurement does not have a measurement id");
        } else {
            log.debug("Measurement has an id, updating measurement");
            Measurement attachedMeasurement = measurementRepository.getOne(measurement.getId());
            BeanUtils.copyProperties(measurement, attachedMeasurement);
            savedMeasurement = measurementRepository.save(attachedMeasurement);
        }

        log.debug(String.format("Saved measurement: %s", savedMeasurement.toString()));
        return savedMeasurement;
    }


    @Override
    @Transactional
    public void deleteMeasurement(Long id) {
        Measurement existingMeasurement = measurementRepository.getOne(id);

        if (existingMeasurement == null) {
            throw new BrewsEntityNotFoundException(String.format("Measurement for id: %d could not be found.", id));
        }
        measurementRepository.delete(existingMeasurement);
    }

}
