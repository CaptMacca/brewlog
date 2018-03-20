package brews.mapper;

import brews.domain.Brew;
import brews.domain.dto.BrewDto;
import brews.domain.dto.RecipeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BrewMapper {

    private final RecipeMapper recipeMapper;

    public BrewMapper(RecipeMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }

    public Brew map(BrewDto brewDto) {
        Brew brew = new Brew();
        brew.setId(brewDto.getId());
        brew.setBrewDate(brewDto.getBrewDate());
        brew.setBrewer(brewDto.getBrewer());

        RecipeDto recipeDto = brewDto.getRecipe();
        if (recipeDto != null) {
            brew.setRecipe(recipeMapper.map(recipeDto));
        }
        return brew;
    }

    public List<Brew> map(List<BrewDto> brews) {
        return brews.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
