package brews.mapper;

import brews.domain.Mash;
import brews.domain.dto.MashDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MashDtoMapper {

    public MashDto map(Mash mash) {
        MashDto mashDto = new MashDto();
        mashDto.setName(mash.getName());
        mashDto.setStepTemp(mash.getStepTemp());
        mashDto.setStepTime(mash.getStepTime());

        return mashDto;
    }

    public List<MashDto> map(List<Mash> mashs) {
        return mashs.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());

    }
}
