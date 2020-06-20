package brews.controllers;

import brews.domain.Measurement;
import brews.domain.dto.MeasurementDto;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MeasurementControllerTest {

    @Mock
    MeasurementService measurementService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        MeasurementController measurementController = new MeasurementController(measurementService);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(measurementController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setBrewId(1L);
        measurement.setValue(123.00);

        when(measurementService.getMeasurement(anyLong())).thenReturn(measurement);

        // When
        mockMvc.perform(get("/api/measurement/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brewId", is(1)))
                .andExpect(jsonPath("$.value", is(123.0)));

        // Then
        verify(measurementService, times(1)).getMeasurement(anyLong());
    }

    @Test
    public void testCreateMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(-1L);
        measurement.setBrewId(1L);
        measurement.setValue(123.00);

        List<MeasurementDto> measurementDtos = new ArrayList<>();
        measurementDtos.add(measurement);

        when(measurementService.saveMeasurements(anyList())).thenReturn(measurementDtos);

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDtos)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(measurementService, times(1)).saveMeasurements(anyList());
    }

    @Test
    public void testCreateMeasurementNoBrew() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setValue(123.00);

        List<MeasurementDto> measurementDtos = new ArrayList<>();
        measurementDtos.add(measurement);

        when(measurementService.saveMeasurements(anyList())).thenThrow(new IllegalArgumentException());

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDtos)))
                .andExpect(status().isBadRequest());

        // Then
        verify(measurementService, times(1)).saveMeasurements(anyList());
    }

    @Test
    public void testCreateMeasurementUnknownBrew() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setValue(123.00);

        List<MeasurementDto> measurementDtos = new ArrayList<>();

        when(measurementService.saveMeasurements(anyList())).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDtos)))
                .andExpect(status().isNotFound());

        // Then
        verify(measurementService, times(1)).saveMeasurements(anyList());
    }

    @Test
    public void testUpdateMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setBrewId(1L);
        measurement.setValue(123.00);

        List<MeasurementDto> measurementDtos = new ArrayList<>();
        measurementDtos.add(measurement);

        when(measurementService.saveMeasurements(anyList())).thenReturn(measurementDtos);

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDtos))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(measurementService, times(1)).saveMeasurements(anyList());
    }

    @Test
    public void testUpdateUnknownMeasurement() throws Exception {
        // Given
        MeasurementDto measurement = new MeasurementDto();
        measurement.setId(1L);
        measurement.setBrewId(1L);
        measurement.setValue(123.00);

        List<MeasurementDto> measurementDtos = new ArrayList<>();
        measurementDtos.add(measurement);

        when(measurementService.saveMeasurements(anyList())).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(post("/api/measurement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDtos))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Then
        verify(measurementService, times(1)).saveMeasurements(anyList());
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
