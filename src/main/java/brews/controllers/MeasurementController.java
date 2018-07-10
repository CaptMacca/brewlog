package brews.controllers;

import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;
import brews.exceptions.MeasurementServiceException;
import brews.services.MeasurementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The measurement api
 */
@Slf4j
@RestController
@RequestMapping("api/measurement")
@CrossOrigin(origins = "*")
@Api(description = "API for creating, retrieving, deleting and updating measurements for a brew")
public final class MeasurementController {

    public final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/types")
    @ApiOperation("Returns all measurement types for which a value can be recorded")
    public List<MeasurementTypeDto> getMeasurementTypes() {
        return measurementService.getMeasurementTypes();
    }

    @GetMapping("{id}")
    @ApiOperation("Get a specific measurement identified by the id")
    public MeasurementDto getMeasurement(@PathVariable Long id) {
        return measurementService.getMeasurement(id);
    }

    @PostMapping()
    @ApiOperation("Creates a new measurement")
    public ResponseEntity<?> createMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug(String.format("Creating new measurement"));
        return saveMeasurement(measurementDto);
    }

    @PutMapping()
    @ApiOperation("Updates a measurement identified by the id")
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


    @DeleteMapping("{id}")
    @ApiOperation("Deletes a measurement identified by the id")
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

        return new ResponseEntity<>(measurements, HttpStatus.NO_CONTENT);

    }
}
