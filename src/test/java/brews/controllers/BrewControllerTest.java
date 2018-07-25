package brews.controllers;

import brews.domain.dto.BrewDto;
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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BrewControllerTest {

    @Mock
    BrewService brewService;

    BrewController brewController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        brewController = new BrewController(brewService);

        mockMvc = MockMvcBuilders.standaloneSetup(brewController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetBrews() throws Exception {

        // Given
        List<BrewDto> brews = new ArrayList<>();
        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setBrewer("a brewer");
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
        BrewDto brew = new BrewDto();
        brew.setBrewer("a brewer");
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
        BrewDto brew = new BrewDto();
        brew.setBrewer("a brewer");
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

        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setBrewer("a brewer");
        brew.setRecipe(recipe);

        when(brewService.saveBrew(anyObject())).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brew))
        )
                .andExpect(status().isAccepted());

        // Then
        verify(brewService, times(1)).saveBrew(anyObject());
    }

    @Test
    public void testCreateNewBrewNoRecipe() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setBrewer("a brewer");

        when(brewService.saveBrew(anyObject())).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brew))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateNewBrewNoBrewer() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        BrewDto brew = new BrewDto();
        brew.setId(1L);
        brew.setRecipe(recipe);

        when(brewService.saveBrew(anyObject())).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brew))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBrew() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    public void testUpdateBrewUnknownId() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    public void testDeleteBrew() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    public void testDeleteBrewUnknowId() throws Exception {
        throw new NotImplementedException();
    }
}
