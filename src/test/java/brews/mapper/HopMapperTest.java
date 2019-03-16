package brews.mapper;

import brews.domain.Hop;
import brews.domain.Ingredient;
import brews.domain.dto.HopDto;
import brews.mapper.domain.IngredientMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HopMapperTest {

    private IngredientMapper hopDtoMapping;

    @Before
    public void setup() throws Exception {
        this.hopDtoMapping = Mappers.getMapper(IngredientMapper.class);
    }

    @Test
    public void testHopDtoMapper() {
        // Given
        Hop hop = new Hop();
        hop.setId(1L);
        hop.setAlpha(10.0);
        hop.setAmount(10.0);
        hop.setHopUsage("Usage");
        hop.setAdditionTime(12.0);
        hop.setName("Hop roleName");
        hop.setType(Ingredient.INGREDIENT_TYPE_HOP);

        // When
        HopDto hopDto = hopDtoMapping.toHopDto(hop);

        // Then
        assertThat(hopDto.getId()).isEqualTo(1L);
        assertThat(hopDto.getAlpha()).isEqualTo(10.0);
        assertThat(hopDto.getAmount()).isEqualTo(10.0);
        assertThat(hopDto.getHopUsage()).isEqualTo("Usage");
        assertThat(hopDto.getAdditionTime()).isEqualTo(12.0);
        assertThat(hopDto.getName()).isEqualTo("Hop roleName");
    }

    @Test
    public void testHopsDtoMapper() {
        // Given
        Hop hop1 = new Hop();
        Hop hop2 = new Hop();
        hop1.setId(1L);
        hop2.setId(2L);
        List<Hop> hops = new ArrayList<>();
        hops.add(hop1);
        hops.add(hop2);

        // When
        List<HopDto> hopDtos = hopDtoMapping.toHopDtos(hops);

        // Then
        assertThat(hopDtos).hasSize(2);
        assertThat(hopDtos.get(0)).isInstanceOf(HopDto.class);
        assertThat(hopDtos.get(1)).isInstanceOf(HopDto.class);
        assertThat(hopDtos.get(0).getId()).isEqualTo(1L);
        assertThat(hopDtos.get(1).getId()).isEqualTo(2L);
    }
}
