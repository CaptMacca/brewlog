package brews.services;

import brews.app.presentation.dto.brew.BrewDto;
import brews.app.presentation.dto.user.UpdateUserDto;
import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.services.exceptions.BrewsEntityNotFoundException;
import brews.domain.mapper.BrewMapper;
import brews.repository.BrewsRepository;
import brews.repository.MeasurementRepository;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.impl.BrewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BrewServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    BrewsRepository brewsRepository;

    @Mock
    MeasurementRepository measurementRepository;

    @Mock
    BrewMapper brewMapper;

    @Mock
    UserRepository userRepository;

    private BrewService brewService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        brewService = new BrewServiceImpl(recipeRepository, brewsRepository, userRepository, brewMapper);
    }

    @Test
    public void testGetAllBrews() {

        // Given
        List<Brew> brews = new ArrayList<>();

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setFirstName("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);
        brews.add(brew);

        when(brewsRepository.findAll()).thenReturn(brews);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        List<Brew> test = brewService.getAllBrews();

        // Then
        assertThat(test).isNotEmpty();
        verify(brewsRepository, times(1)).findAll();
    }

    @Test
    public void testGetBrew() {

        // Given
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("joe");

        User user = new User();
        user.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);

        when(brewsRepository.findById(anyLong())).thenReturn(Optional.of(brew));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        Brew test = brewService.getBrew(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(brewsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetBrewsForRecipe() {

        // Given
        List<Brew> brews = new ArrayList<>();

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setFirstName("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);
        brews.add(brew);

        when(brewService.getBrewsForRecipe(any(Recipe.class))).thenReturn(brews);

        // When
        brewService.getBrewsForRecipe(new Recipe());

        // Then
        verify(brewsRepository,times(1)).findBrewsByRecipe(any(Recipe.class));
    }

    @Test
    public void testSaveBrew() {

        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        when(brewsRepository.save(any())).thenReturn(brew);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        Brew test = brewService.saveBrew(brew, user);

        // Then
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(1)).save(any(Brew.class));
    }

    @Test
    public void testUpdateBrew() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        when(brewsRepository.findById(anyLong())).thenReturn(Optional.of(brew));
        when(brewsRepository.save(any(Brew.class))).thenReturn(brew);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        Brew test = brewService.updateBrew(1L, brew, user);

        // Then
        verify(brewsRepository, times(1)).findById(anyLong());
        verify(brewsRepository, times(1)).save(any(Brew.class));
    }

    @Test()
    public void testUpdateUnknownBrew() {
        Assertions.assertThrows(BrewsEntityNotFoundException.class, () -> {
            // Given
            Recipe recipe = new Recipe();
            recipe.setId(1L);

            User user = new User();
            user.setId(1L);
            user.setUsername("joe");

            Brew brew = new Brew();
            brew.setId(1l);
            brew.setUser(user);
            brew.setRecipe(recipe);

            when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
            when(brewsRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

            // When
            brewService.updateBrew(1L, brew, user);

            // Then
            verify(brewsRepository, times(1)).getOne(anyLong());
            verify(brewsRepository, times(0)).saveAndFlush(any(Brew.class));
        });
    }


    @Test
    public void testDeleteBrew() {

        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        Set<Brew> brews = new HashSet<>();
        brews.add(brew);

        recipe.setBrews(brews);

        when(brewsRepository.findById(anyLong())).thenReturn(Optional.of(brew));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        brewService.deleteBrew(1L);

        // Then
        verify(brewsRepository, times(1)).findById(anyLong());
        verify(brewsRepository, times(1)).delete(any(Brew.class));
    }

    @Test()
    public void testDeleteBrewUnknownBrew() {

        Assertions.assertThrows(BrewsEntityNotFoundException.class, () -> {
            // Given
            when(brewsRepository.findById(anyLong())).thenThrow(new BrewsEntityNotFoundException());

            // When
            brewService.deleteBrew(1L);

            // Then
            verify(brewsRepository, times(1)).findById(anyLong());
            verify(brewsRepository, times(0)).delete(any(Brew.class));
        });
    }
}