package brews.services;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;

import java.util.List;

public interface BrewService {
    List<Brew> getAllBrews();

    List<Brew> getAllBrewsForUser(String username);

    Brew getBrew(Long id);

    String getNotesForBrew(Long id);

    String getTastingNotesForBrew(Long id);

    List<Brew> getBrewsForRecipe(Recipe recipe);

    List<Brew> getTop5BrewsForUser(String username);

    Brew saveBrew(Brew brew, User User);

    Brew updateBrew(Long id, Brew updateBrew, User user);

    void deleteBrew(Long id);
}
