package brews.services.impl;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.exceptions.BrewsEntityNotFoundException;
import brews.domain.mapper.BrewMapper;
import brews.infrastructure.data.jpa.repository.BrewsRepository;
import brews.infrastructure.data.jpa.repository.MeasurementRepository;
import brews.infrastructure.data.jpa.repository.RecipeRepository;
import brews.infrastructure.data.jpa.repository.UserRepository;
import brews.services.BrewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewServiceImpl implements BrewService {

    private final RecipeRepository recipeRepository;
    private final BrewsRepository brewsRepository;
    private final UserRepository userRepository;
    private final BrewMapper brewMapper;

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
        return Optional.of(brewsRepository.getOne(id))
          .orElseThrow(() ->
            new BrewsEntityNotFoundException(String.format("Brew with id %d could not be found to update.", id)));
    }

    @Override
    @Transactional
    public String getNotesForBrew(Long id) {
        return getBrew(id).getNotes();
    }

    @Override
    @Transactional
    public String getTastingNotesForBrew(Long id) {
        return getBrew(id).getTastingNotes();
    }

    @Override
    @Transactional
    public List<Brew> getBrewsForRecipe(Recipe recipe) {
        return brewsRepository.findBrewsByRecipe(recipe);
    }

    @Override
    @Transactional
    public Brew saveBrew(Brew brew, User user) {
        String username = user.getUsername();
        log.debug("Saving brew {} for user {}", brew.getId(), username);
        User actualUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );

        Recipe recipe = recipeRepository.getOne(brew.getRecipe().getId());
        brew.setRecipe(recipe);
        brew.setUser(actualUser);
        brew.setMeasurements(null);
        brew.setVersionId(1L);

        return brewsRepository.save(brew);
    }

    @Override
    @Transactional
    public Brew updateBrew(Long id, Brew updateBrew, User user) {
        String username = user.getUsername();
        log.debug("Updating brew {} for user {}", updateBrew.getId(), username);

        Brew brew = getBrew(id);
        User actualUser = userRepository.findByUsername(username).orElseThrow(
          () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );

        Set<Measurement> measurements = updateBrew.getMeasurements();
        if (measurements != null) {
            for (Measurement measurement : updateBrew.getMeasurements()) {
                measurement.setBrew(updateBrew);
            }
        }

        updateBrew.setId(id);
        updateBrew.setUser(actualUser);
        updateBrew.setRecipe(brew.getRecipe());

        BeanUtils.copyProperties(updateBrew, brew);

        return brewsRepository.save(brew);
    }

    @Override
    @Transactional
    public void deleteBrew(Long id) {
        log.debug("Deleting brew with id {}", id);
        Brew existingBrew = getBrew(id);
        brewsRepository.delete(existingBrew);
    }
}
