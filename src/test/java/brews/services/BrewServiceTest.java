package brews.services;

import brews.domain.Brew;
import brews.domain.Brewer;
import brews.domain.Recipe;
import brews.domain.dto.BrewDto;
import brews.domain.dto.BrewerDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BrewServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    BrewsRepository brewsRepository;

    @Mock
    BrewMapper brewMapper;

    private BrewService brewService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        brewService = new BrewServiceImpl(recipeRepository, brewsRepository, brewMapper);
    }

    @Test
    public void testGetAllBrews() {

        // Given
        BrewerDto brewerDto = new BrewerDto();
        brewerDto.setId(1L);

        List<BrewDto> brews = new ArrayList<>();
        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer(brewerDto);
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
        BrewerDto brewerDto = new BrewerDto();
        brewerDto.setId(1L);

        Brewer brewer = new Brewer();
        brewer.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer(brewerDto);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setBrewer(brewer);

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

        BrewerDto brewerDto = new BrewerDto();
        brewerDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer(brewerDto);
        brewDto.setRecipe(recipeDto);
        brews.add(brewDto);

        when(brewMapper.toBrewDtos(anyList())).thenReturn(brews);

        // When
        brewService.getBrewsForRecipe(1L);

        // Then
        verify(brewsRepository,times(1)).findBrewsByRecipeId(anyLong());
        verify(brewMapper, times(1)).toBrewDtos(anyList());
    }

    @Test
    public void testSaveBrew() {

        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        BrewerDto brewerDto = new BrewerDto();
        brewerDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer(brewerDto);
        brewDto.setRecipe(recipeDto);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Brewer brewer = new Brewer();
        brewer.setId(1L);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setBrewer(brewer);
        brew.setRecipe(recipe);

        when(brewMapper.toBrew(any(BrewDto.class))).thenReturn(brew);
        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(brewsRepository.save(any())).thenReturn(brew);

        // When
        BrewDto test = brewService.saveBrew(brewDto);

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

        BrewerDto brewerDto = new BrewerDto();
        brewerDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer(brewerDto);
        brewDto.setRecipe(recipeDto);

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());

        Brewer brewer = new Brewer();
        brewer.setId(1L);

        Brew brew = new Brew();
        brew.setId(brewDto.getId());
        brew.setBrewer(brewer);
        brew.setRecipe(recipe);

        when(brewMapper.toBrewDto(any(Brew.class))).thenReturn(brewDto);
        when(brewsRepository.getOne(anyLong())).thenReturn(brew);
        when(brewsRepository.saveAndFlush(any(Brew.class))).thenReturn(brew);

        // When
        BrewDto test = brewService.updateBrew(brewDto);

        // Then
        assertThat(test.getId()).isEqualTo(1L);
        verify(brewsRepository, times(1)).getOne(anyLong());
        verify(brewsRepository, times(1)).saveAndFlush(any(Brew.class));
        verify(brewMapper, times(1)).updateFromBrewDto(any(BrewDto.class), any(Brew.class));
        verify(brewMapper, times(1)).toBrewDto(any(Brew.class));

    }

    @Test(expected = BrewsEntityNotFoundException.class)
    public void testUpdateUnknownBrew() {
        // Given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        BrewerDto brewerDto = new BrewerDto();
        brewerDto.setId(1L);

        BrewDto brewDto = new BrewDto();
        brewDto.setId(1L);
        brewDto.setBrewer(brewerDto);
        brewDto.setRecipe(recipeDto);

        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());

        Brewer brewer = new Brewer();
        brewer.setId(1L);

        Brew brew = new Brew();
        brew.setId(brewDto.getId());
        brew.setBrewer(brewer);
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

        Brewer brewer = new Brewer();
        brewer.setId(1L);


        Brew brew = new Brew();
        brew.setId(1L);
        brew.setBrewer(brewer);
        brew.setRecipe(recipe);

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