package brews.services;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.domain.MeasurementType;
import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;
import brews.mapper.domain.MeasurementMapper;
import brews.repository.BrewsRepository;
import brews.repository.MeasurementRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MeasurementServiceTest {

    @Mock
    MeasurementRepository measurementRepository;
    @Mock
    BrewsRepository brewsRepository;
    @Mock
    MeasurementMapper measurementMapper;

    private MeasurementService measurementService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        measurementService = new MeasurementServiceImpl(measurementRepository, brewsRepository, measurementMapper);
    }

    @Test
    public void testGetMeasurementTypes() {

        // Given

        // When
        List<MeasurementTypeDto> test = measurementService.getMeasurementTypes();

        // Then
        assertThat(test).hasSize(MeasurementType.values().length);
    }

    @Test
    public void testGetMeasurement() {

        // Given
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDto.setType(MeasurementType.FG.toString());

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurement.setType(MeasurementType.FG);

        when(measurementRepository.getOne(anyLong())).thenReturn(measurement);
        when(measurementMapper.toMeasurementDto(any(Measurement.class))).thenReturn(measurementDto);

        // When
        MeasurementDto test = measurementService.getMeasurement(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(measurementRepository, times(1)).getOne(anyLong());
        verify(measurementMapper, times(1)).toMeasurementDto(any(Measurement.class));

    }

    @Test
    public void testGetMeasurementsForBrew() {

        // Given
        List<MeasurementDto> measurementDtos = new ArrayList<>();
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDto.setType(MeasurementType.FG.toString());
        measurementDtos.add(measurementDto);

        List<Measurement> measurements = new ArrayList<>();
        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurement.setType(MeasurementType.FG);
        measurements.add(measurement);

        when(measurementMapper.toMeasurementDtos(anyList())).thenReturn(measurementDtos);
        when(measurementRepository.findMeasurementsByBrewId(anyLong())).thenReturn(measurements);

        // When
        List<MeasurementDto> test = measurementService.getMeasurementsForBrew(1L);

        // Then
        assertThat(test).hasSize(1);
        verify(measurementRepository, times(1)).findMeasurementsByBrewId(anyLong());
        verify(measurementMapper, times(1)).toMeasurementDtos(anyList());
    }

    @Test
    public void testCreateMeasurement() {

        // Given
        Brew brew = new Brew();
        brew.setId(1L);

        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDto.setType(MeasurementType.FG.toString());
        measurementDto.setBrewId(1L);

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setType(MeasurementType.FG);
        measurement.setBrew(brew);

        when(measurementMapper.toMeasurement(any(MeasurementDto.class))).thenReturn(measurement);
        when(brewsRepository.getOne(anyLong())).thenReturn(brew);
        when(measurementRepository.saveAndFlush(any(Measurement.class))).thenReturn(measurement);
        when(measurementMapper.toMeasurementDto(any(Measurement.class))).thenReturn(measurementDto);

        // When
        MeasurementDto fixture = measurementService.createMeasurement(measurementDto);

        // Then
        assertThat(fixture.getId()).isEqualTo(1L);
        verify(measurementMapper, times(1)).toMeasurement(any(MeasurementDto.class));
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(measurementMapper, times(1)).toMeasurementDto(any(Measurement.class));
    }

    @Test
    public void testUpdateMeasurement() {

        // Given
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDto.setType(MeasurementType.FG.toString());

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurement.setType(MeasurementType.FG);

        doNothing().when(measurementMapper).updateFromMeasurementDto(measurementDto,measurement);
        when(measurementRepository.getOne(anyLong())).thenReturn(measurement);
        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        when(measurementMapper.toMeasurementDto(any(Measurement.class))).thenReturn(measurementDto);

        // When
        MeasurementDto test = measurementService.updateMeasurement(measurementDto);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(measurementMapper, times(1)).updateFromMeasurementDto(any(MeasurementDto.class),any(Measurement.class));
        verify(measurementRepository, times(1)).getOne(anyLong());
        verify(measurementRepository, times(1)).save(any(Measurement.class));
        verify(measurementMapper, times(1)).toMeasurementDto(any(Measurement.class));

    }

    @Test
    public void testDeleteMeasurement() {

        // Given
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDto.setType(MeasurementType.FG.toString());

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurement.setType(MeasurementType.FG);

        when(measurementRepository.getOne(anyLong())).thenReturn(measurement);

        // When
        measurementService.deleteMeasurement(1L);

        // Then
        verify(measurementRepository, times(1)).getOne(anyLong());
        verify(measurementRepository, times(1)).delete(any(Measurement.class));

    }
}