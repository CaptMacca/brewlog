package brews.mapper;

import brews.domain.Mash;
import brews.domain.dto.MashDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MashMapper {

    public Mash map(MashDto mashDto) {
        Mash mash = new Mash();
        mash.setName(mashDto.getName());
        mash.setStepTemp(mashDto.getStepTemp());
        mash.setStepTime(mashDto.getStepTime());

        return mash;
    }

    public List<Mash> map(List<MashDto> mashDtos) {
        return mashDtos.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());

    }
}
