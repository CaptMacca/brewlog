package brews.services.impl;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.dto.BrewDto;
import brews.domain.dto.UpdateBrewDto;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.mapper.domain.BrewMapper;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.BrewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BrewServiceImpl implements BrewService {

    private final RecipeRepository recipeRepository;
    private final BrewsRepository brewsRepository;
    private final UserRepository userRepository;
    private final BrewMapper brewMapper;

    public BrewServiceImpl(RecipeRepository recipeRepository, BrewsRepository brewsRepository, BrewMapper brewMapper, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.brewsRepository = brewsRepository;
        this.brewMapper = brewMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<BrewDto> getAllBrews() {
        return brewMapper.toBrewDtos(brewsRepository.findAll());
    }

    @Override
    @Transactional
    public List<BrewDto> getAllBrewsForUser(String username) {
        return brewMapper.toBrewDtos(brewsRepository.findBrewsByUserUsername(username));
    }


    @Override
    @Transactional
    public BrewDto getBrew(Long id) {
        return brewMapper.toBrewDto(brewsRepository.getOne(id));
    }

    @Override
    @Transactional
    public List<BrewDto> getBrewsForRecipe(Recipe recipe) {
        return brewMapper.toBrewDtos(brewsRepository.findBrewsByRecipe(recipe));
    }

    @Override
    @Transactional
    public BrewDto saveBrew(BrewDto brewDto, String username) {

        Brew brew = brewMapper.toBrew(brewDto);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );

        Recipe recipe = recipeRepository.getOne(brew.getRecipe().getId());
        brew.setRecipe(recipe);
        brew.setUser(user);
        brew.setMeasurements(null);

        brew = brewsRepository.save(brew);

        return brewMapper.toBrewDto(brew);

    }

    @Override
    @Transactional
    public BrewDto updateBrew(UpdateBrewDto updateBrewDto) {

        Long id = updateBrewDto.getId();

        Brew brew = brewsRepository.getOne(id);

        if (brew == null) {
            throw new BrewsEntityNotFoundException(String.format("Brew with id %d could not be found to update.", id));
        }

        brewMapper.updateFromBrewDto(updateBrewDto,brew);
        Brew updatedBrew = brewsRepository.save(brew);
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
