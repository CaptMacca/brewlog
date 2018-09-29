package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.dto.BrewDto;
import brews.domain.dto.RecipeDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.BrewMapper;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BrewServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    BrewsRepository brewsRepository;

    @Mock
    BrewMapper brewMapper;

    BrewService brewService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        brewService = new BrewServiceImpl(recipeRepository, brewsRepository, brewMapper);
    }

    @Test
    public void testGetAllBrews() {

        // Given
        List<BrewDto> brews = new ArrayList<>();
        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer("a brewer");
        brews.add(brewDto);

        when(brewMapper.toBrewDtos(anyListOf(Brew.class))).thenReturn(brews);

        // When
        List<BrewDto> test = brewService.getAllBrews();

        // Then
        assertNotNull(test);
        verify(brewsRepository, times(1)).findAll();
        verify(brewMapper, times(1)).toBrewDtos(anyListOf(Brew.class));
    }

    @Test
    public void testGetBrew() {

        // Given
        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer("a brewer");

        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);

        // When
        BrewDto test = brewService.getBrew(1L);

        // Then
        assertNotNull(test);
        assertEquals(1L, test.getId().longValue());
        verify(brewsRepository, times(1)).findOne(anyLong());
        verify(brewMapper, times(1)).toBrewDto(any(Brew.class));
    }

    @Test
    public void testGetBrewsForRecipe() {

        // Given
        List<BrewDto> brews = new ArrayList<>();

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer("a brewer");
        brewDto.setRecipe(recipeDto);
        brews.add(brewDto);

        when(brewMapper.toBrewDtos(anyListOf(Brew.class))).thenReturn(brews);

        // When
        List<BrewDto> test = brewService.getBrewsForRecipe(1L);

        // Then
        assertNotNull(test);
        verify(brewsRepository,times(1)).findBrewsByRecipeId(anyLong());
        verify(brewMapper, times(1)).toBrewDtos(anyListOf(Brew.class));

    }

    @Test
    public void testSaveBrew() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer("a brewer");
        brewDto.setRecipe(recipeDto);

        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);

        // When
        BrewDto test = brewService.saveBrew(brewDto);

        // Then
        assertNotNull(test);
        assertEquals(1L, test.getId().longValue());
        verify(recipeRepository, times(1)).findOne(anyLong());
        verify(brewsRepository, times(1)).save(any(Brew.class));
        verify(brewMapper, times(1)).toBrewDto(any(Brew.class));
    }

    @Test
    public void testUpdateBrew() {
        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer("a brewer");
        brewDto.setRecipe(recipeDto);

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());

        Brew brew = new Brew();
        brew.setId(brewDto.getId());
        brew.setBrewer(brewDto.getBrewer());
        brew.setRecipe(recipe);

        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(brewMapper.toBrew(any(BrewDto.class))).thenReturn(brew);
        when(brewsRepository.findOne(anyLong())).thenReturn(brew);

        // When
        BrewDto test = brewService.updateBrew(1L,brewDto);

        // Then
        assertNotNull(test);
        assertEquals(1L, test.getId().longValue());
        verify(brewsRepository, times(1)).findOne(anyLong());
        verify(brewsRepository, times(1)).saveAndFlush(any(Brew.class));
        verify(brewMapper, times(1)).toBrew(any(BrewDto.class));
        verify(brewMapper, times(1)).toBrewDto(any(Brew.class));

    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testUpdateUnknownBrew() {
        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer("a brewer");
        brewDto.setRecipe(recipeDto);

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());

        Brew brew = new Brew();
        brew.setId(brewDto.getId());
        brew.setBrewer(brewDto.getBrewer());
        brew.setRecipe(recipe);

        when(brewMapper.toBrew(any(BrewDto.class))).thenReturn(brew);
        when(brewsRepository.findOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        BrewDto test = brewService.updateBrew(1L,brewDto);

        // Then
        verify(brewsRepository, times(1)).findOne(anyLong());
        verify(brewsRepository, times(0)).saveAndFlush(any(Brew.class));
        verify(brewMapper, times(1)).toBrew(any(BrewDto.class));
    }


    @Test
    public void testDeleteBrew() {

        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setBrewer("a brewer");
        brew.setRecipe(recipe);

        when(brewsRepository.findOne(anyLong())).thenReturn(brew);

        // When
        brewService.deleteBrew(1L);

        // Then
        verify(brewsRepository, times(1)).findOne(anyLong());
        verify(brewsRepository, times(1)).delete(anyLong());
    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testDeleteBrewUnknownBrew() {

        // Given
        when(brewsRepository.findOne(anyLong())).thenThrow(new BrewsEntityNotFoundException());

        // When
        brewService.deleteBrew(1L);

        // Then
        verify(brewsRepository, times(1)).findOne(anyLong());
        verify(brewsRepository, times(0)).delete(anyLong());
    }
}