package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.brew.BrewDto;
import brews.app.presentation.dto.brew.CreateBrewDto;
import brews.app.presentation.dto.brew.UpdateBrewDto;
import brews.app.presentation.dto.recipe.RecipeDto;
import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.exceptions.BrewsEntityNotFoundException;
import brews.domain.mapper.BrewMapper;
import brews.domain.mapper.RecipeMapper;
import brews.services.BrewService;
import brews.app.presentation.rest.exceptionhandler.BrewsControllerExceptionHandler;
import brews.services.RecipeService;
import brews.services.UserService;
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

    @Mock
    UserService userService;

    @Mock
    RecipeService recipeService;

    @Mock
    RecipeMapper recipeMapper;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        BrewController brewController = new BrewController(brewService, recipeService, userService, brewMapper, recipeMapper);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(brewController)
                .setControllerAdvice(new BrewsControllerExceptionHandler())
                .build();
    }

    @Test
    public void WillSucceedForGetBrews() throws Exception {

        // Given
        User user = new User();
        user.setFirstName("joe");
        user.setUsername("joe");
        user.setSurname("brewer");
        user.setEmail("user@somewhere.com");

        List<Brew> brews = new ArrayList<>();
        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brews.add(brew);

        when(brewService.getAllBrewsForUser(anyString())).thenReturn(brews);
        when(userService.getCurrentUserDetails()).thenReturn(user);

        // When
        mockMvc.perform(get("/api/brews"))
                .andExpect(status().isOk());

        // Then
        verify(userService, times(1)).getCurrentUserDetails();
        verify(brewService, times(1)).getAllBrewsForUser(anyString());
    }

    @Test
    public void WillReturnInternalServerErrorOnFatalException() throws Exception {

        // Given
        User user = new User();
        user.setUsername("joe");

        when(brewService.getAllBrewsForUser(anyString())).thenThrow(new NullPointerException());
        when(userService.getCurrentUserDetails()).thenReturn(user);

        // When
        mockMvc.perform(get("/api/brews"))
                .andExpect(status().is5xxServerError());

        // Then
        verify(brewService, times(1)).getAllBrewsForUser(anyString());
    }

    @Test
    public void WillSucceedForRetrievalOfKnownBrew() throws Exception {

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
    public void WillReturnNotFoundForUnknownBrew() throws Exception {
        // Given
        BrewDto brew = new BrewDto();
        brew.setId(1L);

        when(brewService.getBrew(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        mockMvc.perform(get("/api/brews/1222222222"))
                .andExpect(status().isNotFound());

        // Then
        verify(brewService, times(1)).getBrew(anyLong());
    }

    @Test
    public void CreateNewBrewSucceeds() throws Exception {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");

        User user = new User();
        user.setId(1L);
        user.setFirstName("joe");
        user.setUsername("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setUsername("joe");

        CreateBrewDto createBrew = new CreateBrewDto();
        createBrew.setBrew(brewDto);
        createBrew.setRecipe(recipeDto);

        when(userService.getCurrentUserDetails()).thenReturn(user);
        when(brewService.saveBrew(any(Brew.class), any(User.class))).thenReturn(brew);
        when(brewMapper.toBrew(any(BrewDto.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(recipeMapper.toRecipe(any(RecipeDto.class))).thenReturn(recipe);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBrew)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("joe")));

        // Then
        verify(brewService, times(1)).saveBrew(any(Brew.class), any(User.class));
    }

    @Test
    public void CannotCreateNewBrewWithNoRecipe() throws Exception {
        // Given
        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);
        recipe.setName("Recipe");

        User user = new User();
        user.setFirstName("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);

        CreateBrewDto createBrew = new CreateBrewDto();
        createBrew.setBrew(brewMapper.toBrewDto(brew));

        when(userService.getCurrentUserDetails()).thenReturn(user);
        when(brewService.saveBrew(any(Brew.class), any(User.class))).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBrew)))
                .andExpect(status().isBadRequest());

        // Then
        verify(brewService,times(0)).saveBrew(any(Brew.class), any(User.class));

    }

    @Test
    public void CannotCreateNewBrewWithNoUsername() throws Exception {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");

        Brew brew = new Brew();
        brew.setRecipe(recipe);

        User user = new User();
        user.setFirstName("joe");

        CreateBrewDto createBrew = new CreateBrewDto();
        createBrew.setBrew(brewMapper.toBrewDto(brew));

        when(userService.getCurrentUserDetails()).thenReturn(user);
        when(brewService.saveBrew(any(Brew.class), any(User.class))).thenReturn(brew);

        ObjectMapper objectMapper = new ObjectMapper();

        // When
        mockMvc.perform(post("/api/brews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBrew)))
                .andExpect(status().isBadRequest());

        // Then
    }

    @Test
    public void UpdateExistingBrewSucceeds() throws Exception {
        // Given
        UpdateBrewDto updateBrewDto = new UpdateBrewDto();
        updateBrewDto.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("joe");

        when(userService.getCurrentUserDetails()).thenReturn(user);
        when(brewService.updateBrew(anyLong(), any(Brew.class), any(User.class))).thenReturn(brew);
        when(brewMapper.updateBrewDtotoBrew(any(UpdateBrewDto.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(brewMapper.toUpdateBrewDto(any(Brew.class))).thenReturn(updateBrewDto);

        // When
        mockMvc.perform(put("/api/brews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBrewDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));

        // Then
        verify(brewService, times(1)).updateBrew(anyLong(), any(Brew.class), any(User.class));

    }

    @Test
    public void CannotUpdateBrewWithUnknownId() throws Exception {
        // Given
        UpdateBrewDto updateBrewDto = new UpdateBrewDto();
        updateBrewDto.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("joe");

        when(userService.getCurrentUserDetails()).thenReturn(user);
        when(brewService.updateBrew(anyLong(), any(Brew.class), any(User.class))).thenThrow(new BrewsEntityNotFoundException());
        when(brewMapper.updateBrewDtotoBrew(any(UpdateBrewDto.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);

        // When
        mockMvc.perform(put("/api/brews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBrewDto)))
                .andExpect(status().isNotFound());

        // Then
        verify(brewService, times(1)).updateBrew(anyLong(), any(Brew.class), any(User.class));
    }

    @Test
    public void DeleteKnownBrewSucceeds() throws Exception {
        // Given

        // When
        mockMvc.perform(delete("/api/brews/1")).andExpect(status().isNoContent());

        // Then
        verify(brewService, times(1)).deleteBrew(anyLong());
    }

    @Test
    public void CannotDeleteBrewWithUnknowId() throws Exception {
        //Given
        doThrow(new BrewsEntityNotFoundException()).when(brewService).deleteBrew(anyLong());

        // When
        mockMvc.perform(delete("/api/brews/1")).andExpect(status().isNotFound());

        // Then
        verify(brewService, times(1)).deleteBrew(anyLong());
    }
}
