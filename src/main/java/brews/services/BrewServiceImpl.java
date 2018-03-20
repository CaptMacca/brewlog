package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.dto.BrewDto;
import brews.exceptions.BrewServiceException;
import brews.mapper.BrewDtoMapper;
import brews.mapper.BrewMapper;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BrewServiceImpl implements BrewService {

    private final RecipeRepository recipeRepository;
    private final BrewsRepository brewsRepository;
    private final BrewDtoMapper brewDtoMapper;
    private final BrewMapper brewMapper;

    public BrewServiceImpl(RecipeRepository recipeRepository, BrewsRepository brewsRepository, BrewDtoMapper brewDtoMapper, BrewMapper brewMapper) {
        this.recipeRepository = recipeRepository;
        this.brewsRepository = brewsRepository;
        this.brewDtoMapper = brewDtoMapper;
        this.brewMapper = brewMapper;
    }

    @Override
    public List<BrewDto> getAllBrews() {
        return brewDtoMapper.map(brewsRepository.findAll());
    }

    @Override
    public BrewDto getBrew(Long id) {
        return brewDtoMapper.map(brewsRepository.findOne(id));
    }

    @Override
    public BrewDto newBrew(BrewDto brewDto) {
        Recipe recipe = recipeRepository.findOne(brewDto.getRecipe().getId());

        Brew newBrew = new Brew();
        newBrew.setBrewDate(DateTime.now().toDate());
        newBrew.setBrewer(brewDto.getBrewer());
        newBrew.setRecipe(recipe);
        newBrew.setMeasurements(null);

        newBrew = brewsRepository.save(newBrew);

        return brewDtoMapper.map(newBrew);
    }

    @Override
    public BrewDto saveBrew(BrewDto brewDto) {
        Brew detachedBrew = brewMapper.map(brewDto);
        Brew existingBrew = brewsRepository.findOne(detachedBrew.getId());

        if (existingBrew == null) {
            throw new BrewServiceException("Brew for id: " + brewDto.getId() + "could not be found");
        }

        BeanUtils.copyProperties(detachedBrew, existingBrew);
        Brew updatedbrew = brewsRepository.saveAndFlush(existingBrew);
        return brewDtoMapper.map(updatedbrew);
    }

    @Override
    public void deleteBrew(Long id) {

        Brew existingBrew = brewsRepository.findOne(id);

        if (existingBrew == null) {
            throw new BrewServiceException("Brew for id:" + id + "could not be found.");
        }

        brewsRepository.delete(id);
    }
}
