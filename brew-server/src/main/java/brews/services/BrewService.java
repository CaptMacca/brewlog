package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;

import java.util.List;

public interface BrewService {
    List<Brew> getAllBrews();

    List<Brew> getAllBrewsForUser(String username);

    Brew getBrew(Long id);

    List<Brew> getBrewsForRecipe(Recipe recipe);

    List<Brew> getTop5BrewsForUser(String username);

    Brew saveBrew(Brew brew, String username);

    Brew updateBrew(Brew updateBrewD);

    void deleteBrew(Long id);
}
