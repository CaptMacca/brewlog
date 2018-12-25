package brews.services;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.domain.MeasurementType;
import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.MeasurementMapper;
import brews.repository.BrewsRepository;
import brews.repository.MeasurementRepository;
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
    private final MeasurementMapper measurementMapper;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, BrewsRepository brewsRepository,
                                  MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.brewsRepository = brewsRepository;
        this.measurementMapper = measurementMapper;
    }

    @Override
    @Transactional
    public List<MeasurementTypeDto> getMeasurementTypes() {
        // Transform the Enum of Measurement Types into a list for angular consumption
        List<MeasurementTypeDto> measurementTypeDtos = new ArrayList<>();
        MeasurementType[] measurementTypes = MeasurementType.values();

        for (MeasurementType measurementType : measurementTypes) {
            MeasurementTypeDto temp = new MeasurementTypeDto();
            temp.setDescription(measurementType.toString());
            temp.setCode(measurementType.name());

            measurementTypeDtos.add(temp);
        }
        return measurementTypeDtos;
    }

    @Override
    @Transactional
    public MeasurementDto getMeasurement(Long id) {

        log.debug(String.format("Retrieve measurement with id: %d", id));
        MeasurementDto measurementDto;

        Measurement measurement = measurementRepository.getOne(id);

        if (measurement != null) {
            measurementDto = measurementMapper.toMeasurementDto(measurement);
        } else {
            throw new BrewsEntityNotFoundException(String.format("Measurement for measurement id: %d could not be found", id));
        }

        return measurementDto;
    }

    @Override
    @Transactional
    public List<MeasurementDto> getMeasurementsForBrew(Long id) {
        log.debug(String.format("Retrieving measurements for brew id : %d", id));
        List<Measurement> measurements;

        measurements = measurementRepository.findMeasurementsByBrewId(id);

        return measurementMapper.toMeasurementDtos(measurements);
    }

    @Override
    @Transactional
    public MeasurementDto createMeasurement(MeasurementDto measurementDto) {

        log.info("Saving measurement: " + measurementDto.toString());
        Long brewId = measurementDto.getBrewId();
        Measurement detachedMeasurement = measurementMapper.toMeasurement(measurementDto);

        Measurement newMeasurement;

        if (brewId == null) {
            throw new IllegalArgumentException("Measurement is not linked to a brew");
        } else {
            Brew brew = brewsRepository.getOne(brewId);
            if (brew == null) {
                throw new BrewsEntityNotFoundException(String.format("Brew could not be found for brew id: %d", brewId));
            } else {
                detachedMeasurement.setBrew(brew);
            }
        }

        newMeasurement = measurementRepository.saveAndFlush(detachedMeasurement);

        log.debug(String.format("Saved measurement: %s", newMeasurement.toString()));
        return measurementMapper.toMeasurementDto(newMeasurement);
    }


    @Override
    @Transactional
    public MeasurementDto updateMeasurement( MeasurementDto measurementDto) {

        log.info("Saving measurement: " + measurementDto.toString());
//        Measurement detachedMeasurement = measurementMapper.toMeasurement(measurementDto);

        Measurement savedMeasurement;

        if (measurementDto.getId() == null) {
            throw new IllegalArgumentException("Measurement does not have a measurement id");
        } else {
            log.debug("Measurement has an id, updating measurement");
            Measurement attachedMeasurement = measurementRepository.getOne(measurementDto.getId());
            measurementMapper.updateFromMeasurementDto(measurementDto, attachedMeasurement);
            savedMeasurement = measurementRepository.save(attachedMeasurement);
        }

        log.debug(String.format("Saved measurement: %s", savedMeasurement.toString()));
        return measurementMapper.toMeasurementDto(savedMeasurement);
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
