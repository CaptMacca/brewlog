package brews.domain.mapper;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.app.presentation.dto.brew.MeasurementDto;
import brews.domain.mapper.MeasurementMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MeasurementMapperTest {

    private MeasurementMapper measurementMapper;

    @Before
    public void setup() {
        this.measurementMapper = Mappers.getMapper(MeasurementMapper.class);
    }

    @Test
    public void testMeasurementToMeasurementDtoMapper() {
        // Given
        OffsetDateTime currentDate = OffsetDateTime.now();
        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setMeasurementDate(currentDate);
        measurement.setValue(1050.0);

        // When
        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(measurement);

        // Then
        assertThat(measurementDto).isNotNull();
        assertThat(measurementDto).isInstanceOf(MeasurementDto.class);
        assertThat(measurementDto.getId()).isEqualTo(1L);
        assertThat(measurementDto.getMeasurementDate()).isEqualTo(currentDate);
        assertThat(measurementDto.getValue()).isEqualTo(1050.0);
    }

    @Test
    public void testListofMeasurementsToMeasurementDtoMapper() {
        // Given
        Brew brew = new Brew();
        brew.setId(1L);

        Measurement measurement1 = new Measurement();
        Measurement measurement2 = new Measurement();
        measurement1.setId(1L);
        measurement1.setBrew(brew);
        measurement2.setId(2L);
        measurement2.setBrew(brew);
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);

        // When
        List<MeasurementDto> measurementDtos = measurementMapper.toMeasurementDtos(measurements);

        // Then
        assertThat(measurementDtos).hasSize(2);
        assertThat(measurementDtos.get(0)).isInstanceOf(MeasurementDto.class);
        assertThat(measurementDtos.get(1)).isInstanceOf(MeasurementDto.class);
        assertThat(measurementDtos.get(0).getId()).isEqualTo(1L);
        assertThat(measurementDtos.get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void testMeasurementDtoToMeasurementMapper() {
        // Given
        OffsetDateTime currentDate = OffsetDateTime.now();
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDto.setMeasurementDate(currentDate);
        measurementDto.setValue(1010.0);

        // When
        Measurement measurement = measurementMapper.toMeasurement(measurementDto);

        // Then
        assertThat(measurement).isInstanceOf(Measurement.class);
        assertThat(measurement.getId()).isEqualTo(1L);
        assertThat(measurement.getMeasurementDate()).isEqualTo(currentDate);
        assertThat(measurement.getValue()).isEqualTo(1010.0);
    }

    @Test
    public void testMeasurementDtosToMeasurementsMapper() {
        // Given
        MeasurementDto measurementDto1 = new MeasurementDto();
        MeasurementDto measurementDto2 = new MeasurementDto();
        measurementDto1.setId(1L);
        measurementDto2.setId(2L);
        List<MeasurementDto> measurementDtos = new ArrayList<>();
        measurementDtos.add(measurementDto1);
        measurementDtos.add(measurementDto2);

        // When
        List<Measurement> measurements = measurementMapper.toMeasurements(measurementDtos);

        // Then
        assertThat(measurements).hasSize(2);
        assertThat(measurements.get(0)).isInstanceOf(Measurement.class);
        assertThat(measurements.get(1)).isInstanceOf(Measurement.class);
        assertThat(measurements.get(0).getId()).isEqualTo(1L);
        assertThat(measurements.get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void testUpdateMeasurementFromMeasurementDto() {
        // Given
        OffsetDateTime currentDate = OffsetDateTime.now();
        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setMeasurementDate(currentDate);
        measurement.setValue(1050.0);

        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(measurement);
        measurementDto.setValue(1040.0);

        // When
        measurementMapper.updateFromMeasurementDto(measurementDto, measurement);

        // Then
        assertThat(measurement.getId()).isEqualTo(1L);
        assertThat(measurement.getMeasurementDate()).isEqualTo(currentDate);
        assertThat(measurement.getValue()).isEqualTo(1040.0);
    }
}
