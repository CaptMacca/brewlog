package brews.controllers;

import brews.domain.dto.MeasurementDto;
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
@Api("API for creating, retrieving, deleting and updating measurements for a brew")
public final class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("{id}")
    @ApiOperation("Get a specific measurement identified by the id")
    public MeasurementDto getMeasurement(@PathVariable Long id) {
        return measurementService.getMeasurement(id);
    }

    @GetMapping("/brew/{id}")
    @ApiOperation("Get a specific measurement identified by the id")
    public List<MeasurementDto> getMeasurementsForBrew(@PathVariable Long id) {
        return measurementService.getMeasurementsForBrew(id);
    }

    @PostMapping()
    @ApiOperation("Creates a new measurement")
    public ResponseEntity<MeasurementDto> createMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug("Creating new measurement");
        MeasurementDto createdMeasurementDto = this.measurementService.createMeasurement(measurementDto);
        return ResponseEntity.ok(createdMeasurementDto);
    }

    @PutMapping()
    @ApiOperation("Updates a measurement identified by the id")
    public ResponseEntity<MeasurementDto> updateMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug(String.format("Updating measurement with id: %d", measurementDto.getId()));
        MeasurementDto savedMeasurementDto = this.measurementService.updateMeasurement(measurementDto);
        return ResponseEntity.ok(savedMeasurementDto);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a measurement identified by the id")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
        log.debug(String.format("Deleting measurement with id: %d", id));
        this.measurementService.deleteMeasurement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
