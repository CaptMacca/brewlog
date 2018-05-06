package brews.controllers;

import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;
import brews.exceptions.MeasurementServiceException;
import brews.services.MeasurementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class MeasurementController {

    public final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/measurement/types")
    public List<MeasurementTypeDto> getMeasurementTypes() {
        return measurementService.getMeasurementTypes();
    }

    @GetMapping("/measurement/{id}")
    public MeasurementDto getMeasurement(@PathVariable Long id) {
        return measurementService.getMeasurement(id);
    }

    @PostMapping("/measurement")
    public ResponseEntity<?> createMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug(String.format("Creating new measurement"));
        return saveMeasurement(measurementDto);
    }

    @PutMapping("/measurement")
    public ResponseEntity<?> updateMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug(String.format("Updating measurement id: %d", measurementDto.getId()));
        return saveMeasurement(measurementDto);
    }

    private ResponseEntity<?> saveMeasurement(MeasurementDto measurementDto) {

        MeasurementDto savedMeasurement = null;

        try {
            savedMeasurement = this.measurementService.saveMeasurement(measurementDto);
        } catch (MeasurementServiceException mse) {
            log.error("Error saving measurement.",mse);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedMeasurement, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/measurement/{id}")
    public ResponseEntity<?> deleteMeasurement(@PathVariable Long id) {
        List<MeasurementDto> measurements = null;


        try {
            MeasurementDto attachedMeasurement = this.measurementService.getMeasurement(id);
            Long brewId = attachedMeasurement.getBrewId();
            this.measurementService.deleteMeasurement(id);

            measurements = this.measurementService.getMeasurementsForBrew(brewId);
        } catch (MeasurementServiceException mse) {
            log.error("Error deleting measurement.", mse);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(measurements, HttpStatus.OK);

    }
}
