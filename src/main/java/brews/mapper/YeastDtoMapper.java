package brews.mapper;

import brews.domain.Yeast;
import brews.domain.dto.YeastDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class YeastDtoMapper {

    public YeastDto map(Yeast yeast) {
        YeastDto yeastDto = new YeastDto();
        yeastDto.setAmount(yeast.getAmount());
        yeastDto.setName(yeast.getName());

        return yeastDto;
    }

    public List<YeastDto> map(List<Yeast> yeasts) {
        return yeasts.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }
}
