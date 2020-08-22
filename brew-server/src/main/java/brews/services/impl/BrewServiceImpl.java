package brews.services.impl;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.BrewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BrewServiceImpl implements BrewService {

    private final RecipeRepository recipeRepository;
    private final BrewsRepository brewsRepository;
    private final UserRepository userRepository;

    public BrewServiceImpl(RecipeRepository recipeRepository, BrewsRepository brewsRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.brewsRepository = brewsRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<Brew> getAllBrews() {
        return brewsRepository.findAll();
    }

    @Override
    @Transactional
    public List<Brew> getAllBrewsForUser(String username) {
        return brewsRepository.findBrewsByUserUsername(username);
    }

    @Override
    @Transactional
    public List<Brew> getTop5BrewsForUser(String username) {
        return brewsRepository.findTop5BrewsByUserUsernameOrderByBrewDateDesc(username);
    }

    @Override
    @Transactional
    public Brew getBrew(Long id) {
        return brewsRepository.getOne(id);
    }

    @Override
    @Transactional
    public List<Brew> getBrewsForRecipe(Recipe recipe) {
        return brewsRepository.findBrewsByRecipe(recipe);
    }

    @Override
    @Transactional
    public Brew saveBrew(Brew brew, String username) {
        log.debug("Saving brew {} for user {}", brew.getId(), username);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );

        Recipe recipe = recipeRepository.getOne(brew.getRecipe().getId());
        brew.setRecipe(recipe);
        brew.setUser(user);
        brew.setMeasurements(null);

        brew = brewsRepository.save(brew);

        return brew;
    }

    @Override
    @Transactional
    public Brew updateBrew(Brew updateBrew) {
        log.debug("Updating brew {}", updateBrew.getId());
        Long id = updateBrew.getId();

        Brew brew = brewsRepository.getOne(id);

        if (brew == null) {
            throw new BrewsEntityNotFoundException(String.format("Brew with id %d could not be found to update.", id));
        }

        BeanUtils.copyProperties(updateBrew, brew);
        Brew updatedBrew = brewsRepository.save(brew);
        return updatedBrew;
    }

    @Override
    @Transactional
    public void deleteBrew(Long id) {
        log.debug("Deleting brew with id {}", id);
        Brew existingBrew = brewsRepository.getOne(id);

        if (existingBrew == null) {
            throw new BrewsEntityNotFoundException(String.format("Brew with id %d could not be found to delete.", id));
        }

        brewsRepository.delete(existingBrew);
    }
}
