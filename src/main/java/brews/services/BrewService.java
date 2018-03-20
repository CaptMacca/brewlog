package brews.services;

import brews.domain.dto.BrewDto;

import java.util.List;

public interface BrewService {
    List<BrewDto> getAllBrews();

    BrewDto getBrew(Long id);

    BrewDto newBrew(BrewDto brewDto);

    BrewDto saveBrew(BrewDto brewDto);

    void deleteBrew(Long id);
}
