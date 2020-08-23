package brews.controllers;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.dto.*;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.handler.BrewsControllerExceptionHandler;
import brews.mapper.domain.BrewMapper;
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

    @Mock
    BrewMapper brewMapper;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        BrewController brewController = new BrewController(brewService, brewMapper);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(brewController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetBrews() throws Exception {

        // Given
        User brewer = new User();
        brewer.setFirstName("joe");
        brewer.setSurname("brewer");
        brewer.setEmail("user@somewhere.com");

        List<Brew> brews = new ArrayList<>();
        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(brewer);
        brews.add(brew);

        when(brewService.getAllBrewsForUser(anyString())).thenReturn(brews);

        // When
        mockMvc.perform(get("/api/brews")
                .param("username","anyuser"))
                .andExpect(status().isOk());

        // Then
        verify(brewService, times(1)).getAllBrewsForUser(anyString());
    }

    @Test
    public void testGetBrewsFatal() throws Exception {

        // Given
        when(brewService.getAllBrewsForUser(anyString())).thenThrow(new NullPointerException());

        // When
        mockMvc.perform(get("/api/brews")
                .param("username", anyString()))
                .andExpect(status().is5xxServerError());

        // Then
        verify(brewService, times(1)).getAllBrewsForUser(anyString());
    }

    @Test
    public void testGetBrew() throws Exception {

        // Given
        User user = new User();
        user.setFirstName("joe");

        Brew brew = new Brew();
        brew.setUser(user);
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
        UpdateUserRequest user = new UpdateUserRequest();
        user.setFirstName("joe");

        BrewDto brew = new BrewDto();
        brew.setUser(user);
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
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");

        User user = new User();
        user.setId(1L);
        user.setFirstName("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        RecipeDto recipeDto = new RecipeDto();
        recipe.setId(1L);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("joe");

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setUser(updateUserRequest);

        CreateBrewRequest createBrew = new CreateBrewRequest();
        createBrew.setBrew(brewDto);
        createBrew.setUsername("joe");
        createBrew.setRecipe(recipeDto);


        when(brewService.saveBrew(any(Brew.class), anyString())).thenReturn(brew);
        when(brewMapper.toBrew(any(BrewDto.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBrew)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.user.firstName", is("joe")));

        // Then
        verify(brewService, times(1)).saveBrew(any(Brew.class), anyString());
    }

    @Test
    public void testCreateNewBrewNoRecipe() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        User user = new User();
        user.setFirstName("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);

        CreateBrewRequest createBrew = new CreateBrewRequest();
        createBrew.setBrew(brewMapper.toBrewDto(brew));
        createBrew.setUsername("joe");

        when(brewService.saveBrew(any(Brew.class), anyString())).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBrew)))
                .andExpect(status().isBadRequest());

        // Then
        verify(brewService,times(0)).saveBrew(any(Brew.class), anyString());

    }

    @Test
    public void testCreateNewBrewNoUsername() throws Exception {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");

        Brew brew = new Brew();
        brew.setRecipe(recipe);

        CreateBrewRequest createBrew = new CreateBrewRequest();
        createBrew.setBrew(brewMapper.toBrewDto(brew));

        when(brewService.saveBrew(any(Brew.class), anyString())).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBrew)))
                .andExpect(status().isBadRequest());

        // Then
    }

    @Test
    public void testUpdateBrew() throws Exception {
        // Given
        UpdateBrewRequest updateBrewRequest = new UpdateBrewRequest();
        updateBrewRequest.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);

        when(brewService.updateBrew(any(Brew.class))).thenReturn(brew);
        when(brewMapper.updateBrewDtotoBrew(any(UpdateBrewRequest.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);

        // When
        mockMvc.perform(put("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBrewRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));

        // Then
        verify(brewService, times(1)).updateBrew(any(Brew.class));

    }

    @Test
    public void testUpdateBrewUnknownId() throws Exception {
        // Given
        UpdateBrewRequest updateBrewRequest = new UpdateBrewRequest();
        updateBrewRequest.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);

        when(brewService.updateBrew(any(Brew.class))).thenThrow(new BrewsEntityNotFoundException());
        when(brewMapper.updateBrewDtotoBrew(any(UpdateBrewRequest.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);

        // When
        mockMvc.perform(put("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBrewRequest)))
                .andExpect(status().isNotFound());

        // Then
        verify(brewService, times(1)).updateBrew(any(Brew.class));
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
