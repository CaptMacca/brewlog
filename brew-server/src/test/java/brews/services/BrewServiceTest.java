package brews.services;

import brews.app.presentation.dto.brew.BrewDto;
import brews.app.presentation.dto.user.UpdateUserDto;
import brews.domain.Brew;
import brews.domain.Measurement;
import brews.domain.Recipe;
import brews.domain.User;
import brews.services.exceptions.BrewEntityNotFoundException;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.impl.BrewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BrewServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    BrewsRepository brewsRepository;

    @Mock
    UserRepository userRepository;

    private BrewService brewService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        brewService = new BrewServiceImpl(recipeRepository, brewsRepository, userRepository);
    }

    @Test
    public void GetAllBrewsSucceeds() {

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
    public void GetAllBrewsForKnownUserSucceeds() {

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

        when(brewsRepository.findBrewsByUserUsername(anyString())).thenReturn(brews);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        List<Brew> test = brewService.getAllBrewsForUser(anyString());

        // Then
        assertThat(test).isNotEmpty();
        verify(brewsRepository, times(1)).findBrewsByUserUsername(anyString());
    }

    @Test
    public void GetKnownBrewSucceeds() {

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
    public void findTop5BrewsByUsername() throws Exception {

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

        when(brewsRepository.findTop5BrewsByUserUsernameOrderByBrewDateDesc(anyString())).thenReturn(brews);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        List<Brew> test = brewService.getTop5BrewsForUser(anyString());

        // Then
        assertThat(test).isNotEmpty();
        verify(brewsRepository, times(1)).findTop5BrewsByUserUsernameOrderByBrewDateDesc(anyString());
    }

    @Test
    public void FindAllBrewsForRecipeSucceeds() throws Exception {

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
    public void SaveBrewSucceeds() {

        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("joe");

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setValue(12.0);

        Set<Measurement> measurements = new HashSet<>();
        measurements.add(measurement);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);
        brew.setMeasurements(measurements);

        when(brewsRepository.save(any())).thenReturn(brew);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        Brew test = brewService.saveBrew(brew, user);

        // Then
        verify(recipeRepository, times(1)).getById(anyLong());
        verify(brewsRepository, times(1)).save(any(Brew.class));
    }

    @Test
    public void SaveBrewWithUnknownUserThrowsUserNameNotFound() throws Exception {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            // Given
            Recipe recipe = new Recipe();
            recipe.setId(1L);

            User user = new User();
            user.setId(1L);
            user.setUsername("joe");

            Measurement measurement = new Measurement();
            measurement.setId(1L);
            measurement.setValue(12.0);

            Set<Measurement> measurements = new HashSet<>();
            measurements.add(measurement);

            Brew brew = new Brew();
            brew.setId(1L);
            brew.setUser(user);
            brew.setRecipe(recipe);
            brew.setMeasurements(measurements);

            when(brewsRepository.save(any())).thenReturn(brew);
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

            // When
            Brew test = brewService.saveBrew(brew, user);

            // Then
        });
    }

    @Test
    public void UpdateKnownBrewSucceeds() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("joe");

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setValue(12.0);

        Set<Measurement> measurements = new HashSet<>();
        measurements.add(measurement);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);
        brew.setMeasurements(measurements);

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
    public void UpdateUnknownBrewThrowsBrewsEntityNotFoundException() {
        Assertions.assertThrows(BrewEntityNotFoundException.class, () -> {
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
            when(brewsRepository.getOne(anyLong())).thenThrow(new BrewEntityNotFoundException());

            // When
            brewService.updateBrew(1L, brew, user);

            // Then
            verify(brewsRepository, times(1)).getOne(anyLong());
            verify(brewsRepository, times(0)).saveAndFlush(any(Brew.class));
        });
    }

    @Test
    public void DeleteKnownBrewSucceeds() {

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

    @Test
    public void UpdateBrewWithUnknownUserThrowsUserNotFoundException() {

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
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
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

            // When
            brewService.updateBrew(1L, brew, user);

            // Then

        });
    }

    @Test()
    public void DeleteUnknownBrewThrowsBrewsEntityNotFound() throws Exception {

        Assertions.assertThrows(BrewEntityNotFoundException.class, () -> {
            // Given
            when(brewsRepository.findById(anyLong())).thenThrow(new BrewEntityNotFoundException());

            // When
            brewService.deleteBrew(1L);

            // Then
            verify(brewsRepository, times(1)).findById(anyLong());
            verify(brewsRepository, times(0)).delete(any(Brew.class));
        });
    }

    @Test
    public void GetNotesSucceeded() throws Exception {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);
        brew.setNotes("notes");
        brew.setTastingNotes("tasting notes");

        // When
        when(brewsRepository.findById(anyLong())).thenReturn(Optional.of(brew));

        String notes = brewService.getNotesForBrew(anyLong());

        // Then
        assertThat(notes).isEqualTo("notes");
   }

    @Test
    public void GetTastingNotesSucceeded() throws Exception {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);
        brew.setNotes("notes");
        brew.setTastingNotes("tasting notes");

        // When
        when(brewsRepository.findById(anyLong())).thenReturn(Optional.of(brew));

        String tastingNotes = brewService.getTastingNotesForBrew(anyLong());

        // Then
        assertThat(tastingNotes).isEqualTo("tasting notes");
    }
}