package brews.domain.mapper;

import brews.domain.Ingredient;
import brews.domain.Yeast;
import brews.app.presentation.dto.recipe.YeastDto;
import brews.domain.mapper.IngredientMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class YeastMapperTest {

    private IngredientMapper yeastDtoMapping;

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
        assertThat(yeastDto).isNotNull();
        assertThat(yeastDto.getId()).isEqualTo(1L);
        assertThat(yeastDto.getName()).isEqualTo("My Yeast");
        assertThat(yeastDto.getLaboratory()).isEqualTo("My Lab");
        assertThat(yeastDto.getProductId()).isEqualTo("My Product");
        assertThat(yeastDto.getAmount()).isEqualTo(10.00);
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
        assertThat(yeastDtos).isNotEmpty();
        assertThat(yeastDtos).hasSize(2);
        assertThat(yeastDtos.get(0).getId()).isEqualTo(1L);
        assertThat(yeastDtos.get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void testYeastDtoMapping() {
        // Given
        YeastDto yeastDto = new YeastDto();
        yeastDto.setId(1L);
        yeastDto.setName("My yeast");
        yeastDto.setAmount(10.0);
        yeastDto.setProductId("Product Id");
        yeastDto.setLaboratory("Lab roleName");

        // When
        Yeast yeast = yeastDtoMapping.toYeast(yeastDto);

        // Then
        assertThat(yeast.getId()).isEqualTo(1L);
        assertThat(yeast.getName()).isEqualTo("My yeast");
        assertThat(yeast.getAmount()).isEqualTo(10.0);
        assertThat(yeast.getProductId()).isEqualTo("Product Id");
        assertThat(yeast.getLaboratory()).isEqualTo("Lab roleName");
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
        assertThat(yeasts).hasSize(2);
        assertThat(yeasts.get(0)).isInstanceOf(Yeast.class);
        assertThat(yeasts.get(1)).isInstanceOf(Yeast.class);
        assertThat(yeasts.get(0).getId()).isEqualTo(1L);
        assertThat(yeasts.get(1).getId()).isEqualTo(2L);
    }
}
