package brews.services;

import brews.domain.Recipe;
import brews.domain.dto.BrewDto;
import brews.domain.dto.UpdateBrewDto;

import java.util.List;

public interface BrewService {
    List<BrewDto> getAllBrews();

    List<BrewDto> getAllBrewsForUser(String username);

    BrewDto getBrew(Long id);

    List<BrewDto> getBrewsForRecipe(Recipe recipe);

    List<BrewDto> getTop5BrewsForUser(String username);

    BrewDto saveBrew(BrewDto brewDto, String username);

    BrewDto updateBrew(UpdateBrewDto updateBrewDto);

    void deleteBrew(Long id);
}
