package brews.services;

import brews.domain.dto.BrewDto;

import java.util.List;

public interface BrewService {
    List<BrewDto> getAllBrews();

    List<BrewDto> getAllBrewsForUser(String username);

    BrewDto getBrew(Long id);

    List<BrewDto> getBrewsForRecipe(Long recipeId);

    BrewDto saveBrew(BrewDto brewDto, String username);

    BrewDto updateBrew(BrewDto brewDto);

    void deleteBrew(Long id);
}
