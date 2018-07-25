package brews.controllers;

import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;
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
    public ResponseEntity<MeasurementDto> createMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug("Creating new measurement");
        return saveMeasurement(measurementDto);
    }

    @PutMapping()
    @ApiOperation("Updates a measurement identified by the id")
    public ResponseEntity<MeasurementDto> updateMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug(String.format("Updating measurement with id: %d", measurementDto.getId()));
        return saveMeasurement(measurementDto);
    }

    private ResponseEntity<MeasurementDto> saveMeasurement(MeasurementDto measurementDto) {

        MeasurementDto savedMeasurement;

//        try {
            savedMeasurement = this.measurementService.saveMeasurement(measurementDto);
//        } catch (MeasurementNotFoundException mse) {
//            log.error("Error saving measurement. Measurement could not be found.",mse);
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        return new ResponseEntity<>(savedMeasurement, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("{id}")
    @ApiOperation("Deletes a measurement identified by the id")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
//        try {
            this.measurementService.deleteMeasurement(id);
//        } catch (MeasurementNotFoundException mse) {
//            log.error("Error deleting measurement. Measurement could not be found.", mse);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
