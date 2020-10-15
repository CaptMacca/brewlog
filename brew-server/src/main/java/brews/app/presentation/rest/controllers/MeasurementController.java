package brews.app.presentation.rest.controllers;

import brews.domain.Measurement;
import brews.app.presentation.dto.brew.MeasurementDto;
import brews.domain.mapper.MeasurementMapper;
import brews.services.MeasurementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@Api("API for creating, retrieving, deleting and updating measurements for a brew")
public final class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementMapper measurementMapper;

    @GetMapping("{id}")
    @ResponseBody
    @ApiOperation(value="Get a specific measurement identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public MeasurementDto getMeasurement(@PathVariable Long id) {
        return measurementMapper.toMeasurementDto(measurementService.getMeasurement(id));
    }

    @GetMapping("/brew/{id}")
    @ResponseBody
    @ApiOperation(value="Get a specific measurement identified by the id",  authorizations = { @Authorization(value="jwtToken")})
    public List<MeasurementDto> getMeasurementsForBrew(@PathVariable Long id) {
        return measurementMapper.toMeasurementDtos(measurementService.getMeasurementsForBrew(id));
    }

    @PostMapping()
    @ResponseBody
    @ApiOperation(value="Saves new or updated measurements", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<List<MeasurementDto>> saveMeasurement(@RequestBody List<MeasurementDto> measurementDtos) {
        log.debug("Saving measurements");
        List<Measurement> saveMeasurement = measurementMapper.toMeasurements(measurementDtos);
        return ResponseEntity.ok(measurementMapper.toMeasurementDtos(this.measurementService.saveMeasurements(saveMeasurement)));
    }

    @PutMapping("{id}")
    @ResponseBody
    @ApiOperation(value="Updates a measurement identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<MeasurementDto> updateMeasurement(@RequestBody MeasurementDto measurementDto) {
        log.debug(String.format("Updating measurement with id: %d", measurementDto.getId()));
        Measurement updateMeasurement = measurementMapper.toMeasurement(measurementDto);
        return ResponseEntity.ok(measurementMapper.toMeasurementDto(this.measurementService.updateMeasurement(updateMeasurement)));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value="Deletes a measurement identified by the id",  authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
        log.debug(String.format("Deleting measurement with id: %d", id));
        this.measurementService.deleteMeasurement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
