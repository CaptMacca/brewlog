package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.dto.BrewDto;
import brews.domain.dto.RecipeDto;
import brews.domain.dto.UpdateBrewDto;
import brews.domain.dto.UserDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.BrewMapper;
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
    BrewMapper brewMapper;

    @Mock
    UserRepository userRepository;

    private BrewService brewService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        brewService = new BrewServiceImpl(recipeRepository, brewsRepository, brewMapper, userRepository);
    }

    @Test
    public void testGetAllBrews() {

        // Given
        UserDto UserDto = new UserDto();
        UserDto.setId(1L);

        List<BrewDto> brews = new ArrayList<>();
        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setUser(UserDto);
        brews.add(brewDto);

        when(brewMapper.toBrewDtos(anyList())).thenReturn(brews);

        // When
        List<BrewDto> test = brewService.getAllBrews();

        // Then
        assertThat(test).isNotEmpty();
        verify(brewsRepository, times(1)).findAll();
        verify(brewMapper, times(1)).toBrewDtos(anyList());
    }

    @Test
    public void testGetBrew() {

        // Given
        UserDto UserDto = new UserDto();
        UserDto.setId(1L);

        User user = new User();
        user.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setUser(UserDto);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);

        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(brewsRepository.getOne(anyLong())).thenReturn(brew);

        // When
        BrewDto test = brewService.getBrew(1L);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewMapper, times(1)).toBrewDto(any(Brew.class));
    }

    @Test
    public void testGetBrewsForRecipe() {

        // Given
        List<BrewDto> brews = new ArrayList<>();

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        UserDto UserDto = new UserDto();
        UserDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setUser(UserDto);
        brewDto.setRecipe(recipeDto);
        brews.add(brewDto);

        when(brewMapper.toBrewDtos(anyList())).thenReturn(brews);
        when(brewService.getBrewsForRecipe(any(Recipe.class))).thenReturn(brews);

        // When
        List<BrewDto> dtos = brewService.getBrewsForRecipe(new Recipe());

        // Then
        verify(brewsRepository,times(1)).findBrewsByRecipe(any(Recipe.class));
        verify(brewMapper, times(1)).toBrewDtos(anyList());
    }

    @Test
    public void testSaveBrew() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        UserDto UserDto = new UserDto();
        UserDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setUser(UserDto);
        brewDto.setRecipe(recipeDto);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("joe");
        user.setEmail("joe@brewer.com");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        when(brewMapper.toBrew(any(BrewDto.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(brewsRepository.save(any())).thenReturn(brew);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        BrewDto test = brewService.saveBrew(brewDto, "joe");

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(recipeRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(1)).save(any(Brew.class));
        verify(brewMapper, times(1)).toBrewDto(any(Brew.class));
    }

    @Test
    public void testUpdateBrew() {
        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        UserDto UserDto = new UserDto();
        UserDto.setId(1L);

        UpdateBrewDto updateBrewDto = new UpdateBrewDto();
        updateBrewDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());

        User user = new User();
        user.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setUser(user);
        brew.setRecipe(recipe);

        when(brewMapper.toUpdateBrewDto(any(Brew.class))).thenReturn(updateBrewDto);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(brewsRepository.getOne(anyLong())).thenReturn(brew);
        when(brewsRepository.save(any(Brew.class))).thenReturn(brew);

        // When
        BrewDto test = brewService.updateBrew(updateBrewDto);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(1)).save(any(Brew.class));
        verify(brewMapper, times(1)).updateFromBrewDto(any(UpdateBrewDto.class), any(Brew.class));
        verify(brewMapper, times(1)).toBrewDto(any(Brew.class));

    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testUpdateUnknownBrew() {
        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        UserDto UserDto = new UserDto();
        UserDto.setId(1L);

        UpdateBrewDto brewDto = new UpdateBrewDto();
        brewDto.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());

        User User = new User();
        User.setId(1L);

        Brew brew = new Brew();
        brew.setId(brewDto.getId());
        brew.setUser(User);
        brew.setRecipe(recipe);

        when(brewMapper.toBrew(any(BrewDto.class))).thenReturn(brew);
        when(brewsRepository.getOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        brewService.updateBrew(brewDto);

        // Then
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(0)).saveAndFlush(any(Brew.class));
        verify(brewMapper, times(1)).toBrew(any(BrewDto.class));
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