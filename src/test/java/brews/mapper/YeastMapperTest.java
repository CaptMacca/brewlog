package brews.mapper;

import brews.domain.Ingredient;
import brews.domain.Yeast;
import brews.domain.dto.YeastDto;
import brews.mapper.domain.IngredientMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class YeastMapperTest {

    IngredientMapper yeastDtoMapping;

    @Before
    public void setup() throws Exception {
        yeastDtoMapping = Mappers.getMapper(IngredientMapper.class);
    }

    @Test
    public void testYeastMapping() {
        // Given
        Yeast yeast = new Yeast();
        yeast.setId(1L);
        yeast.setName("My Yeast");
        yeast.setAmount(10.00);
        yeast.setLaboratory("My Lab");
        yeast.setProductId("My Product");
        yeast.setType(Ingredient.INGREDIENT_TYPE_YEAST);

        // When
        YeastDto yeastDto = yeastDtoMapping.toYeastDto(yeast);

        // Then
        assertNotNull(yeastDto);
        assertThat(1L, equalTo(yeastDto.getId().longValue()));
        assertThat("My Yeast", equalTo(yeastDto.getName()));
        assertThat("My Lab", equalTo(yeastDto.getLaboratory()));
        assertThat("My Product", equalTo(yeastDto.getProductId()));
        assertThat(10.00, equalTo(yeastDto.getAmount().doubleValue()));
    }

    @Test
    public void testListofYeastsMapping() throws Exception {
        // Given
        Yeast yeast1 = new Yeast();
        Yeast yeast2 = new Yeast();
        yeast1.setId(1L);
        yeast2.setId(2L);
        List<Yeast> yeasts = new ArrayList<>();
        yeasts.add(yeast1);
        yeasts.add(yeast2);

        // When
        List<YeastDto> yeastDtos = yeastDtoMapping.toYeastDtos(yeasts);

        // Then
        assertNotNull(yeastDtos);
        assertThat(yeastDtos.size(), equalTo(2));
        assertThat(yeastDtos.get(0).getId(), equalTo(1L));
        assertThat(yeastDtos.get(1).getId(), equalTo(2L));
    }

    @Test
    public void testYeastDtoMapping() {
        // Given
        YeastDto yeastDto = new YeastDto();
        yeastDto.setId(1L);
        yeastDto.setName("My yeast");
        yeastDto.setAmount(10.0);
        yeastDto.setProductId("Product Id");
        yeastDto.setLaboratory("Lab name");

        // When
        Yeast yeast = yeastDtoMapping.toYeast(yeastDto);

        // Then
        assertNotNull(yeast);
        assertThat(1L, equalTo(yeast.getId()));
        assertThat("My yeast", equalTo(yeast.getName()));
        assertThat(10.0, equalTo(yeast.getAmount()));
        assertThat("Product Id", equalTo(yeast.getProductId()));
        assertThat("Lab name", equalTo(yeast.getLaboratory()));
    }

    @Test
    public void testListofYeastDtoMapping() {
        // Given
        YeastDto yeastDto1 = new YeastDto();
        YeastDto yeastDto2 = new YeastDto();
        yeastDto1.setId(1L);
        yeastDto2.setId(2L);
        List<YeastDto> yeastDtos = new ArrayList<>();
        yeastDtos.add(yeastDto1);
        yeastDtos.add(yeastDto2);

        // When
        List<Yeast> yeasts = yeastDtoMapping.toYeasts(yeastDtos);

        // Then
        assertNotNull(yeasts);
        assertThat(2, equalTo(yeasts.size()));
        assertThat(yeasts.get(0), instanceOf(Yeast.class));
        assertThat(yeasts.get(1), instanceOf(Yeast.class));
        assertThat(1L, equalTo(yeasts.get(0).getId()));
        assertThat(2L, equalTo(yeasts.get(1).getId()));
    }
}
