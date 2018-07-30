package brews.controllers;

import brews.domain.dto.MeasurementDto;
import brews.domain.dto.MeasurementTypeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.handler.BrewsControllerExceptionHandler;
import brews.services.MeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MeasurementControllerTest {

    @Mock
    MeasurementService measurementService;

    MeasurementController measurementController;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        measurementController = new MeasurementController(measurementService);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(measurementController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetMeasurementTypes() throws Exception {

        // Given
        List<MeasurementTypeDto> measurements = new ArrayList<>();

        MeasurementTypeDto measurementType1 = new MeasurementTypeDto();
        measurementType1.setCode("Code1");
        measurementType1.setDescription("Description1");
        MeasurementTypeDto measurementType2 = new MeasurementTypeDto();
        measurementType2.setCode("Code2");
        measurementType2.setDescription("Description2");

        measurements.add(measurementType1);
        measurements.add(measurementType2);

        when(measurementService.getMeasurementTypes()).thenReturn(measurements);

        // When
        mockMvc.perform(get("/api/measurement/types")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].code", is("Code1")))
                .andExpect(jsonPath("$[1].code", is("Code2")));

        // Then
        verify(measurementService, times(1)).getMeasurementTypes();

    }

    @Test
    public void testGetMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setBrewId(1L);
        measurement.setType("type");
        measurement.setValue(123.00);

        when(measurementService.getMeasurement(anyLong())).thenReturn(measurement);

        // When
        mockMvc.perform(get("/api/measurement/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.brewId", is(1)))
                .andExpect(jsonPath("$.type", is("type")))
                .andExpect(jsonPath("$.value", is(123.0)));

        // Then
        verify(measurementService, times(1)).getMeasurement(anyLong());
    }

    @Test
    public void testCreateMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setBrewId(1L);
        measurement.setType("type");
        measurement.setValue(123.00);

        when(measurementService.createMeasurement(any(MeasurementDto.class))).thenReturn(measurement);

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(measurement)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brewId", is(1)))
                .andExpect(jsonPath("$.value", is(123.0)));

        // Then
        verify(measurementService, times(1)).createMeasurement(any(MeasurementDto.class));
    }

    @Test
    public void testCreateMeasurementNoBrew() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setType("type");
        measurement.setValue(123.00);

        when(measurementService.createMeasurement(any(MeasurementDto.class))).thenThrow(new IllegalArgumentException());

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(measurement)))
                .andExpect(status().isBadRequest());

        // Then
        verify(measurementService, times(1)).createMeasurement(any(MeasurementDto.class));
    }

    @Test
    public void testCreateMeasurementUnknownBrew() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setType("type");
        measurement.setValue(123.00);

        when(measurementService.createMeasurement(any(MeasurementDto.class))).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(measurement)))
                .andExpect(status().isNotFound());

        // Then
        verify(measurementService, times(1)).createMeasurement(any(MeasurementDto.class));
    }

    @Test
    public void testUpdateMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setBrewId(1L);
        measurement.setType("type");
        measurement.setValue(123.00);

        when(measurementService.updateMeasurement(anyLong(), any(MeasurementDto.class))).thenReturn(measurement);

        // When
        mockMvc.perform(put("/api/measurement/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(measurement))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brewId", is(1)))
                .andExpect(jsonPath("$.value", is(123.0)));

        // Then
        verify(measurementService, times(1)).updateMeasurement(anyLong(), any(MeasurementDto.class));
    }

    @Test
    public void testUpdateUnknownMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setBrewId(1L);
        measurement.setType("type");
        measurement.setValue(123.00);

        when(measurementService.updateMeasurement(anyLong(), any(MeasurementDto.class))).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(put("/api/measurement/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(measurement))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        // Then
        verify(measurementService, times(1)).updateMeasurement(anyLong(), any(MeasurementDto.class));
    }

    @Test
    public void testDeleteMeasurement() throws Exception {

        // When
        mockMvc.perform(delete("/api/measurement/1"))
                .andExpect(status().isNoContent());

        // Then
        verify(measurementService, times(1)).deleteMeasurement(anyLong());

    }

    @Test
    public void testDeleteUnknownMeasurement() throws Exception {

        // Given
        doThrow(new BrewsEntityNotFoundException()).when(measurementService).deleteMeasurement(anyLong());

        // When
        mockMvc.perform(delete("/api/measurement/1"))
                .andExpect(status().isNotFound());

        // Then
        verify(measurementService, times(1)).deleteMeasurement(anyLong());

    }

}
