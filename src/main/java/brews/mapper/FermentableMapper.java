package brews.mapper;

import brews.domain.Fermentable;
import brews.domain.dto.FermentableDto;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FermentableMapper {

    @Synchronized
    public Fermentable map(FermentableDto fermentableDto) {

        if (fermentableDto==null) {
            return null;
        }

        Fermentable fermentable = new Fermentable();
        fermentable.setId(fermentableDto.getId());
        fermentable.setName(fermentableDto.getName());
        fermentable.setAmount(fermentableDto.getAmount());
        fermentable.setType(fermentableDto.getType());

        return fermentable;
    }

    @Synchronized
    public List<Fermentable> map(List<FermentableDto> fermentableDtos) {
        return fermentableDtos.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
