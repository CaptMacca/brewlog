package brews.mapper;

import brews.domain.Hop;
import brews.domain.dto.HopDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class HopMapper {

    public Hop map(HopDto hopDto) {
        Hop hop = new Hop();
        hop.setName(hopDto.getName());
        hop.setAdditionTime(hopDto.getAdditionTime());
        hop.setAlpha(hopDto.getAlpha());
        hop.setHopUsage(hopDto.getHopUsage());
        hop.setAmount(hopDto.getAmount());

        return hop;
    }

    public List<Hop> map(List<HopDto> hopDtos) {
        return hopDtos.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }

}
