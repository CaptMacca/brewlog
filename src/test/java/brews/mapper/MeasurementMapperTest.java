package brews.mapper;

import brews.domain.Measurement;
import brews.domain.MeasurementType;
import brews.domain.dto.MeasurementDto;
import brews.mapper.domain.MeasurementMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MeasurementMapperTest {

    MeasurementMapper measurementMapper;

    @Before
    public void setup() {
        this.measurementMapper = Mappers.getMapper(MeasurementMapper.class);
    }

    @Test
    public void testMeasurementToMeasurementDtoMapper() {
        // Given
        Date currentDate = new Date();
        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setType(MeasurementType.FG);
        measurement.setMeasurementDate(currentDate);
        measurement.setValue(1050.0);

        // When
        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(measurement);

        // Then
        assertNotNull(measurementDto);
        assertThat(measurementDto, instanceOf(MeasurementDto.class));
        assertThat(1L, is(equalTo(measurementDto.getId())));
        assertThat("FG", is(equalTo(measurementDto.getType())));
        assertThat(currentDate, is(equalTo(measurementDto.getMeasurementDate())));
        assertThat(1050.0, is(equalTo(measurementDto.getValue())));
    }

    @Test
    public void testListofMeasurementsToMeasurementDtoMapper() {
        // Given
        Measurement measurement1 = new Measurement();
        Measurement measurement2 = new Measurement();
        measurement1.setId(1L);
        measurement2.setId(2L);
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);

        // When
        List<MeasurementDto> measurementDtos = measurementMapper.toMeasurementDtos(measurements);

        // Then
        assertNotNull(measurementDtos);
        assertThat(2, is(equalTo(measurementDtos.size())));
        assertThat(measurementDtos.get(0), instanceOf(MeasurementDto.class));
        assertThat(measurementDtos.get(1), instanceOf(MeasurementDto.class));
        assertThat(1L, is(equalTo(measurementDtos.get(0).getId())));
        assertThat(2L, is(equalTo(measurementDtos.get(1).getId())));
    }

    @Test
    public void testMeasurementDtoToMeasurementMapper() {
        // Given
        Date currentDate = new Date();
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(1L);
        measurementDto.setType("FG");
        measurementDto.setMeasurementDate(currentDate);
        measurementDto.setValue(1010.0);

        // When
        Measurement measurement = measurementMapper.toMeasurement(measurementDto);

        // Then
        assertNotNull(measurement);
        assertThat(measurement, instanceOf(Measurement.class));
        assertThat(1L, is(equalTo(measurement.getId())));
        assertThat(MeasurementType.FG, is(equalTo(measurement.getType())));
        assertThat(currentDate, is(equalTo(measurement.getMeasurementDate())));
        assertThat(1010.0, is(equalTo(measurement.getValue())));
    }

    @Test
    public void testMeasurementDtosToMeasurementsMapper(){
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
        assertNotNull(measurements);
        assertThat(2, is(equalTo(measurements.size())));
        assertThat(measurements.get(0), instanceOf(Measurement.class));
        assertThat(measurements.get(1), instanceOf(Measurement.class));
        assertThat(1L, is(equalTo(measurements.get(0).getId())));
        assertThat(2L, is(equalTo(measurements.get(1).getId())));
    }

    @Test
    public void testUpdateMeasurementFromMeasurementDto() {
        // Given
        Date currentDate = new Date();
        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setType(MeasurementType.FG);
        measurement.setMeasurementDate(currentDate);
        measurement.setValue(1050.0);

        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(measurement);

        measurementDto.setValue(1040.0);

        // When
        measurementMapper.updateFromMeasurementDto(measurementDto, measurement);

        // Then
        assertThat(1L, is(equalTo(measurement.getId())));
        assertThat(MeasurementType.FG, is(equalTo(measurement.getType())));
        assertThat(currentDate, is(equalTo(measurement.getMeasurementDate())));
        assertThat(1040.0, is(equalTo(measurement.getValue())));
    }
}
