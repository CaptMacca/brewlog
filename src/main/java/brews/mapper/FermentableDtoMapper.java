package brews.mapper;

import brews.domain.Fermentable;
import brews.domain.dto.FermentableDto;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FermentableDtoMapper {

    @Synchronized
    public FermentableDto map(Fermentable fermentable) {

        if (fermentable==null) {
            return null;
        }

        FermentableDto fermentableDto = new FermentableDto();
        fermentableDto.setId(fermentable.getId());
        fermentableDto.setName(fermentable.getName());
        fermentableDto.setAmount(fermentable.getAmount());
        fermentableDto.setType(fermentable.getType());

        return fermentableDto;
    }

    @Synchronized
    public List<FermentableDto> map(List<Fermentable> fermentables) {
        return fermentables.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
