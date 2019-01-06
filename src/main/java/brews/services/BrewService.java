package brews.services;

import brews.domain.dto.BrewDto;

import java.util.List;

public interface BrewService {
    List<BrewDto> getAllBrews();

    BrewDto getBrew(Long id);

    List<BrewDto> getBrewsForRecipe(Long recipeId);

    BrewDto saveBrew(BrewDto brewDto);

    BrewDto updateBrew(BrewDto brewDto);

    void deleteBrew(Long id);
}
