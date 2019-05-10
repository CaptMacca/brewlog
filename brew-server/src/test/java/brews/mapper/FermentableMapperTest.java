package brews.mapper;

import brews.domain.Fermentable;
import brews.domain.dto.FermentableDto;
import brews.mapper.domain.IngredientMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FermentableMapperTest {

    private IngredientMapper fermentableMapper;

    private static final Long FERMENTABLE_ID_VAL = new Long(1L);
    private static final Double FERMENTABLE_AMOUNT = new Double(1.0d);
    private static final String FERMENTABLE_NAME = "Maris Otter Pale Malt";


    @Before
    public void setUp() throws Exception {
        this.fermentableMapper = Mappers.getMapper(IngredientMapper.class);
    }

    @Test
    public void testFermentableDto() {
        // Given
        FermentableDto fermentableDto = new FermentableDto();
        fermentableDto.setId(FERMENTABLE_ID_VAL);
        fermentableDto.setName(FERMENTABLE_NAME);
        fermentableDto.setAmount(FERMENTABLE_AMOUNT);

        // When
        Fermentable fermentable = fermentableMapper.toFermentable(fermentableDto);

        // Then
        assertThat(fermentable.getId()).isEqualTo(FERMENTABLE_ID_VAL);
        assertThat(fermentable.getName()).isEqualTo(FERMENTABLE_NAME);
        assertThat(fermentable.getAmount()).isEqualTo(FERMENTABLE_AMOUNT);
    }

    @Test
    public void mapListofFermentableDto() {
        // Given
        List<FermentableDto> fermentableDtos = new ArrayList<>();

        FermentableDto fermentableDto = new FermentableDto();
        fermentableDto.setId(FERMENTABLE_ID_VAL);
        fermentableDto.setName(FERMENTABLE_NAME);
        fermentableDto.setAmount(FERMENTABLE_AMOUNT);
        fermentableDtos.add(fermentableDto);

        // When
        List<Fermentable> fermentables = fermentableMapper.toFermentables(fermentableDtos);

        // Then
        assertThat(fermentables).hasSize(1);
        assertThat(fermentables.get(0).getId()).isEqualTo(FERMENTABLE_ID_VAL);
        assertThat(fermentables.get(0).getName()).isEqualTo(FERMENTABLE_NAME);
        assertThat(fermentables.get(0).getAmount()).isEqualTo(FERMENTABLE_AMOUNT);
    }

    @Test
    public void testFermentable() {

        //Given
        Fermentable fermentable = new Fermentable();
        fermentable.setId(FERMENTABLE_ID_VAL);
        fermentable.setName(FERMENTABLE_NAME);
        fermentable.setAmount(FERMENTABLE_AMOUNT);
        fermentable.setAddAfterBoil(Boolean.FALSE);

        //When
        FermentableDto fermentableDto = fermentableMapper.toFermentableDto(fermentable);

        //Then
        assertThat(fermentableDto.getId()).isEqualTo(FERMENTABLE_ID_VAL);
        assertThat(fermentableDto.getName()).isEqualTo(FERMENTABLE_NAME);
        assertThat(fermentableDto.getAmount()).isEqualTo(FERMENTABLE_AMOUNT);
        assertThat(fermentableDto.getAddAfterBoil()).isFalse();
    }

    @Test
    public void testListOfFermentables() {
        //Given
        List<Fermentable> fermentables = new ArrayList<>();
        Fermentable fermentable1 = new Fermentable();
        fermentable1.setId(FERMENTABLE_ID_VAL);
        fermentable1.setName(FERMENTABLE_NAME);
        fermentable1.setAmount(FERMENTABLE_AMOUNT);
        fermentables.add(fermentable1);

        //When
        List<FermentableDto> fermentableDtos = fermentableMapper.toFermentableDtos(fermentables);

        //Then
        assertThat(fermentableDtos).hasSize(1);
        assertThat(fermentableDtos.get(0)).isInstanceOf(FermentableDto.class);
        assertThat(fermentableDtos.get(0).getId()).isEqualTo(FERMENTABLE_ID_VAL);
        assertThat(fermentableDtos.get(0).getName()).isEqualTo(FERMENTABLE_NAME);
        assertThat(fermentableDtos.get(0).getAmount()).isEqualTo(FERMENTABLE_AMOUNT);
    }
}