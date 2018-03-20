package brews.mapper;

import brews.domain.Yeast;
import brews.domain.dto.YeastDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class YeastMapper {

    public Yeast map(YeastDto yeastDto) {
        Yeast yeast = new Yeast();
        yeast.setAmount(yeastDto.getAmount());
        yeast.setName(yeastDto.getName());

        return yeast;
    }

    public List<Yeast> map(List<YeastDto> yeasts) {
        return yeasts.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }
}
