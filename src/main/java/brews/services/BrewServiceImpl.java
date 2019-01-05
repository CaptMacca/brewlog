package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.dto.BrewDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.BrewMapper;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BrewServiceImpl implements BrewService {

    private final RecipeRepository recipeRepository;
    private final BrewsRepository brewsRepository;
    private final BrewMapper brewMapper;

    public BrewServiceImpl(RecipeRepository recipeRepository, BrewsRepository brewsRepository, BrewMapper brewMapper) {
        this.recipeRepository = recipeRepository;
        this.brewsRepository = brewsRepository;
        this.brewMapper = brewMapper;
    }

    @Override
    @Transactional
    public List<BrewDto> getAllBrews() {
        return brewMapper.toBrewDtos(brewsRepository.findAll());
    }

    @Override
    @Transactional
    public BrewDto getBrew(Long id) {
        return brewMapper.toBrewDto(brewsRepository.getOne(id));
    }

    @Override
    @Transactional
    public List<BrewDto> getBrewsForRecipe(Long recipeId) {
        return brewMapper.toBrewDtos(brewsRepository.findBrewsByRecipeId(recipeId));
    }

    @Override
    @Transactional
    public BrewDto saveBrew(BrewDto brewDto) {

        Brew brew = brewMapper.toBrew(brewDto);

        Recipe recipe = recipeRepository.getOne(brew.getRecipe().getId());
        brew.setRecipe(recipe);
        brew.setMeasurements(null);

        brew = brewsRepository.save(brew);

        return brewMapper.toBrewDto(brew);
    }

    @Override
    @Transactional
    public BrewDto updateBrew(BrewDto brewDto) {

        Long id = brewDto.getId();

        Brew brew = brewsRepository.getOne(id);

        if (brew == null) {
            throw new BrewsEntityNotFoundException(String.format("Brew with id %d could not be found to update.", id));
        }

        brewMapper.updateFromBrewDto(brewDto,brew);
        Brew updatedBrew = brewsRepository.saveAndFlush(brew);
        return brewMapper.toBrewDto(updatedBrew);
    }

    @Override
    @Transactional
    public void deleteBrew(Long id) {

        Brew existingBrew = brewsRepository.getOne(id);

        if (existingBrew == null) {
            throw new BrewsEntityNotFoundException(String.format("Brew with id %d could not be found to delete.", id));
        }

        brewsRepository.delete(existingBrew);
    }
}
