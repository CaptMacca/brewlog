package brews.controllers;

import brews.domain.dto.BrewDto;
import brews.domain.dto.BrewerDto;
import brews.domain.dto.RecipeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.handler.BrewsControllerExceptionHandler;
import brews.services.BrewService;
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

public class BrewControllerTest {

    @Mock
    BrewService brewService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        BrewController brewController = new BrewController(brewService);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(brewController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetBrews() throws Exception {

        // Given
        BrewerDto brewer = new BrewerDto();
        brewer.setId(1L);

        List<BrewDto> brews = new ArrayList<>();
        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setBrewer(brewer);
        brews.add(brew);

        when(brewService.getAllBrews()).thenReturn(brews);

        // When
        mockMvc.perform(get("/api/brews"))
                .andExpect(status().isOk());

        // Then
        verify(brewService, times(1)).getAllBrews();
    }

    @Test
    public void testGetBrewsFatal() throws Exception {

        // Given
        when(brewService.getAllBrews()).thenThrow(new NullPointerException());

        // When
        mockMvc.perform(get("/api/brews"))
                .andExpect(status().is5xxServerError());

        // Then
        verify(brewService, times(1)).getAllBrews();
    }

    @Test
    public void testGetBrew() throws Exception {

        // Given
        BrewerDto brewer = new BrewerDto();
        brewer.setId(1L);

        BrewDto brew = new BrewDto();
        brew.setBrewer(brewer);
        brew.setId(1L);

        when(brewService.getBrew(anyLong())).thenReturn(brew);

        // When
        mockMvc.perform(get("/api/brews/1"))
                .andExpect(status().isOk());
        // Then
        verify(brewService, times(1)).getBrew(anyLong());
    }

    @Test
    public void testGetBrewUnknownId() throws Exception {
        // Given
        BrewerDto brewer = new BrewerDto();
        brewer.setId(1L);

        BrewDto brew = new BrewDto();
        brew.setBrewer(brewer);
        brew.setId(1L);

        when(brewService.getBrew(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(get("/api/brews/1222222222"))
                .andExpect(status().isNotFound());

        // Then
        verify(brewService, times(1)).getBrew(anyLong());
    }

    @Test
    public void testCreateNewBrew() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        BrewerDto brewer = new BrewerDto();
        brewer.setId(1L);

        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setBrewer(brewer);
        brew.setRecipe(recipe);

        when(brewService.saveBrew(any(BrewDto.class))).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(brew)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brewer.id", is(1)));

        // Then
        verify(brewService, times(1)).saveBrew(any(BrewDto.class));
    }

    @Test
    public void testCreateNewBrewNoRecipe() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        BrewerDto brewer = new BrewerDto();
        brewer.setId(1L);

        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setBrewer(brewer);

        when(brewService.saveBrew(any(BrewDto.class))).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brew)))
                .andExpect(status().isBadRequest());

        // Then
        verify(brewService,times(0)).saveBrew(any(BrewDto.class));

    }

    @Test
    public void testCreateNewBrewNoBrewer() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        BrewDto brew = new BrewDto();
        brew.setRecipe(recipe);

        when(brewService.saveBrew(any(BrewDto.class))).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brew)))
                .andExpect(status().isBadRequest());

        // Then
    }

    @Test
    public void testUpdateBrew() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("mock");

        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setRecipe(recipe);

        when(brewService.updateBrew(any(BrewDto.class))).thenReturn(brew);

        // When
        mockMvc.perform(put("/api/brews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(brew)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.recipe.name", is("mock")));

        // Then
        verify(brewService, times(1)).updateBrew(any(BrewDto.class));

    }

    @Test
    public void testUpdateBrewUnknownId() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setRecipe(recipe);

        when(brewService.updateBrew(any(BrewDto.class))).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(put("/api/brews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(brew)))
                .andExpect(status().isNotFound());

        // Then
        verify(brewService, times(1)).updateBrew(any(BrewDto.class));
    }

    @Test
    public void testDeleteBrew() throws Exception {
        // Given

        // When
        mockMvc.perform(delete("/api/brews/1")).andExpect(status().isNoContent());

        // Then
        verify(brewService, times(1)).deleteBrew(anyLong());
    }

    @Test
    public void testDeleteBrewUnknowId() throws Exception {
        //Given
        doThrow(new BrewsEntityNotFoundException()).when(brewService).deleteBrew(anyLong());

        // When
        mockMvc.perform(delete("/api/brews/1")).andExpect(status().isNotFound());

        // Then
        verify(brewService, times(1)).deleteBrew(anyLong());
    }
}
