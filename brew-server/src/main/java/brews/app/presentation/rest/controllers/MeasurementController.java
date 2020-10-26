package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.brew.MeasurementDto;
import brews.domain.Measurement;
import brews.domain.mapper.MeasurementMapper;
import brews.services.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Measurements", description="API for creating, retrieving, deleting and updating measurements for a brew")
public final class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementMapper measurementMapper;

    @GetMapping("{id}")
    @ResponseBody
    @Operation(description="Get a specific measurement identified by the id", security = @SecurityRequirement(name="bearerAuth"))
    public MeasurementDto getMeasurement(@PathVariable Long id) {
        return measurementMapper.toMeasurementDto(measurementService.getMeasurement(id));
    }

    @GetMapping("/brew/{id}")
    @ResponseBody
    @Operation(description="Get a specific measurement identified by the id", security = @SecurityRequirement(name="bearerAuth"))
    public List<MeasurementDto> getMeasurementsForBrew(@PathVariable Long id) {
        return measurementMapper.toMeasurementDtos(measurementService.getMeasurementsForBrew(id));
    }

    @PostMapping()
    @ResponseBody
    @Operation(description="Saves new or updated measurements", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<List<MeasurementDto>> saveMeasurement(@RequestBody List<MeasurementDto> measurementDtos) {
        log.debug("Saving measurements");
        List<Measurement> saveMeasurement = measurementMapper.toMeasurements(measurementDtos);
        return ResponseEntity.ok(measurementMapper.toMeasurementDtos(this.measurementService.saveMeasurements(saveMeasurement)));
    }

    @PutMapping("{id}")
    @ResponseBody
    @Operation(description="Updates a measurement identified by the id", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<MeasurementDto> updateMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug(String.format("Updating measurement with id: %d", measurementDto.getId()));
        Measurement updateMeasurement = measurementMapper.toMeasurement(measurementDto);
        return ResponseEntity.ok(measurementMapper.toMeasurementDto(this.measurementService.updateMeasurement(updateMeasurement)));
    }

    @DeleteMapping("{id}")
    @Operation(description="Deletes a measurement identified by the id", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
        log.debug(String.format("Deleting measurement with id: %d", id));
        this.measurementService.deleteMeasurement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
