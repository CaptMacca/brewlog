package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.dto.BrewDto;
import brews.domain.dto.UpdateUserRequest;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.impl.BrewServiceImpl;
import org.junit.Before;
import org.junit.Test;
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
    UserRepository userRepository;

    private BrewService brewService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        brewService = new BrewServiceImpl(recipeRepository, brewsRepository, userRepository);
    }

    @Test
    public void testGetAllBrews() {

        // Given

        // When
        List<Brew> test = brewService.getAllBrews();

        // Then
        assertThat(test).isNotEmpty();
        verify(brewsRepository, times(1)).findAll();
    }

    @Test
    public void testGetBrew() {

        // Given
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("joe");

        User user = new User();
        user.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setUser(updateUserRequest);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);

        when(brewsRepository.getOne(anyLong())).thenReturn(brew);

        // When
        Brew test = brewService.getBrew(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(brewsRepository, times(1)).getOne(anyLong());
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
        user.setFirstName("joe");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        when(brewsRepository.save(any())).thenReturn(brew);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        Brew test = brewService.saveBrew(brew, "joe");

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
        user.setFirstName("joe");


        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        when(brewsRepository.getOne(anyLong())).thenReturn(brew);
        when(brewsRepository.save(any(Brew.class))).thenReturn(brew);

        // When
        Brew test = brewService.updateBrew(brew);

        // Then
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(1)).save(any(Brew.class));
    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testUpdateUnknownBrew() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User User = new User();
        User.setId(1L);

        Brew brew = new Brew();
        brew.setId(1l);
        brew.setUser(User);
        brew.setRecipe(recipe);

        when(brewsRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        brewService.updateBrew(brew);

        // Then
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(0)).saveAndFlush(any(Brew.class));
    }


    @Test
    public void testDeleteBrew() {

        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User User = new User();
        User.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(User);
        brew.setRecipe(recipe);

        Set<Brew> brews = new HashSet<>();
        brews.add(brew);

        recipe.setBrews(brews);

        when(brewsRepository.getOne(anyLong())).thenReturn(brew);

        // When
        brewService.deleteBrew(1L);

        // Then
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(1)).delete(any(Brew.class));
    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testDeleteBrewUnknownBrew() {

        // Given
        when(brewsRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        brewService.deleteBrew(1L);

        // Then
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(0)).delete(any(Brew.class));
    }
}