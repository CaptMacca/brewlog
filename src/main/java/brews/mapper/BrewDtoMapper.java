package brews.mapper;

import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.dto.BrewDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BrewDtoMapper {

    private final RecipeDtoMapper recipeDtoMapper;

    public BrewDtoMapper(RecipeDtoMapper recipeDtoMapper) {
        this.recipeDtoMapper = recipeDtoMapper;
    }

    public BrewDto map(Brew brew) {
        BrewDto brewDto = new BrewDto();
        brewDto.setId(brew.getId());
        brewDto.setBrewDate(brew.getBrewDate());
        brewDto.setBrewer(brew.getBrewer());

        Recipe recipe = brew.getRecipe();
        if(recipe != null) {
            brewDto.setRecipe(recipeDtoMapper.map(recipe));
        }
        return brewDto;
    }

    public List<BrewDto> map(List<Brew> brews) {
        return brews.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
