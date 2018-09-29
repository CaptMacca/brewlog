package brews.mapper;

import brews.domain.Fermentable;
import brews.domain.dto.FermentableDto;
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

public class FermentableMapperTest {

    private IngredientMapper fermentableMapper;

    public static final Long FERMENTABLE_ID_VAL = new Long(1L);
    public static final Double FERMENTABLE_AMOUNT = new Double(1.0d);
    public static final String FERMENTABLE_NAME = "Maris Otter Pale Malt";


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
        assertNotNull(fermentable);
        assertThat(FERMENTABLE_ID_VAL, equalTo(fermentable.getId()));
        assertThat(FERMENTABLE_NAME, equalTo(fermentable.getName()));
        assertThat(FERMENTABLE_AMOUNT, equalTo(fermentable.getAmount()));
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
        assertNotNull(fermentables);
        assertThat(1, equalTo(fermentables.size()));
        assertThat(FERMENTABLE_ID_VAL, equalTo(fermentables.get(0).getId()));
        assertThat(FERMENTABLE_NAME, equalTo(fermentables.get(0).getName()));
        assertThat(FERMENTABLE_AMOUNT, equalTo(fermentables.get(0).getAmount()));
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
        assertNotNull(fermentableDto);
        assertThat(FERMENTABLE_ID_VAL, equalTo(fermentableDto.getId()));
        assertThat(FERMENTABLE_NAME, equalTo(fermentableDto.getName()));
        assertThat(FERMENTABLE_AMOUNT,  equalTo(fermentableDto.getAmount()));
        assertThat(Boolean.FALSE, equalTo(fermentableDto.getAddAfterBoil()));
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
        assertNotNull(fermentableDtos);
        assertThat(1, equalTo(fermentableDtos.size()));
        assertThat(fermentableDtos.get(0), instanceOf(FermentableDto.class));
        assertThat(FERMENTABLE_ID_VAL, equalTo(fermentableDtos.get(0).getId()));
        assertThat(FERMENTABLE_NAME, equalTo(fermentableDtos.get(0).getName()));
        assertThat(FERMENTABLE_AMOUNT, equalTo(fermentableDtos.get(0).getAmount()));
    }
}