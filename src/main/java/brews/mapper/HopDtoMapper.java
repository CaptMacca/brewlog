package brews.mapper;

import brews.domain.Hop;
import brews.domain.dto.HopDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class HopDtoMapper {

    public HopDto map(Hop hop) {
        HopDto hopDto = new HopDto();
        hopDto.setName(hop.getName());
        hopDto.setAdditionTime(hop.getAdditionTime());
        hopDto.setAlpha(hop.getAlpha());
        hopDto.setHopUsage(hop.getHopUsage());
        hopDto.setAmount(hop.getAmount());

        return hopDto;
    }

    public List<HopDto> map(List<Hop> hops) {
        return hops.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
