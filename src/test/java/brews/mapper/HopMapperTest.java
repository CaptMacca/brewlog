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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HopMapperTest {

    IngredientMapper hopDtoMapping;

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
        hop.setName("Hop name");
        hop.setType(Ingredient.INGREDIENT_TYPE_HOP);

        // When
        HopDto hopDto = hopDtoMapping.toHopDto(hop);

        // Then
        assertNotNull(hopDto);
        assertEquals(1L, hopDto.getId().longValue());
        assertThat(10.0, equalTo(hopDto.getAlpha().doubleValue()));
        assertThat(10.0, equalTo(hopDto.getAmount().doubleValue()));
        assertThat("Usage", equalTo(hopDto.getHopUsage()));
        assertThat(12.0, equalTo(hopDto.getAdditionTime()));
        assertThat("Hop name", equalTo(hopDto.getName()));
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
        assertNotNull(hopDtos);
        assertThat(2, equalTo(hopDtos.size()));
        assertThat(hopDtos.get(0), instanceOf(HopDto.class));
        assertThat(hopDtos.get(1), instanceOf(HopDto.class));
        assertThat(1L, equalTo(hopDtos.get(0).getId()));
        assertThat(2L, equalTo(hopDtos.get(1).getId()));
    }
}
