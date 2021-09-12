package brews.services;

import brews.app.presentation.dto.brew.MeasurementDto;
import brews.domain.Brew;
import brews.domain.Measurement;
import brews.repository.BrewsRepository;
import brews.repository.MeasurementRepository;
import brews.services.impl.MeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MeasurementServiceTest {

    @Mock
    MeasurementRepository measurementRepository;
    @Mock
    BrewsRepository brewsRepository;

    private MeasurementService measurementService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        measurementService = new MeasurementServiceImpl(measurementRepository, brewsRepository);
    }

    @Test
    public void testGetMeasurement() {

        // Given
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());

        when(measurementRepository.findById(anyLong())).thenReturn(Optional.of(measurement));

        // When
        Measurement test = measurementService.getMeasurement(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(measurementRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetMeasurementsForBrew() {

        // Given
        List<MeasurementDto> measurementDtos = new ArrayList<>();
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDtos.add(measurementDto);

        List<Measurement> measurements = new ArrayList<>();
        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurements.add(measurement);

        when(measurementRepository.findMeasurementsByBrewId(anyLong())).thenReturn(measurements);

        // When
        List<Measurement> test = measurementService.getMeasurementsForBrew(1L);

        // Then
        assertThat(test).hasSize(1);
        verify(measurementRepository, times(1)).findMeasurementsByBrewId(anyLong());
    }

    @Test
    public void testCreateMeasurement() {

        // Given
        Brew brew = new Brew();
        brew.setId(1L);

        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setBrew(brew);

        when(brewsRepository.findById(anyLong())).thenReturn(Optional.of(brew));
        when(measurementRepository.saveAndFlush(any(Measurement.class))).thenReturn(measurement);

        // When
        Measurement fixture = measurementService.createMeasurement(1L, measurement);

        // Then
        assertThat(fixture.getId()).isEqualTo(1L);
        verify(brewsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateMeasurement() {

        // Given
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());

        when(measurementRepository.findById(anyLong())).thenReturn(Optional.of(measurement));
        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);

        // When
        Measurement test = measurementService.updateMeasurement(measurement);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(measurementRepository, times(1)).findById(anyLong());
        verify(measurementRepository, times(1)).save(any(Measurement.class));
    }

    @Test
    public void testDeleteMeasurement() {

        // Given
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());

        when(measurementRepository.findById(anyLong())).thenReturn(Optional.of(measurement));

        // When
        measurementService.deleteMeasurement(1L);

        // Then
        verify(measurementRepository, times(1)).findById(anyLong());
        verify(measurementRepository, times(1)).delete(any(Measurement.class));

    }
}