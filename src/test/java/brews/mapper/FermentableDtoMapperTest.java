package brews.mapper;

import brews.domain.Fermentable;
import brews.domain.dto.FermentableDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FermentableDtoMapperTest {

    FermentableDtoMapper fermentableDtoMapper;
    public static final Long FERMENTABLE_ID_VAL = new Long(1L);
    public static final Double FERMENTABLE_AMOUNT = 1.0d;
    public static final String FERMENTABLE_NAME = "Maris Otter Pale Malt";


    @Before
    public void setUp() throws Exception {
        fermentableDtoMapper = new FermentableDtoMapper();
    }

    @Test
    public void testNullFermentableDto() {
        Fermentable fermentable=null;
        assertNull(fermentableDtoMapper.map(fermentable));
    }

    @Test
    public void testFermentable() {

        //Given
        Fermentable fermentable = new Fermentable();
        fermentable.setId(FERMENTABLE_ID_VAL);
        fermentable.setName(FERMENTABLE_NAME);
        fermentable.setAmount(FERMENTABLE_AMOUNT);

        //When
        FermentableDto fermentableDto = fermentableDtoMapper.map(fermentable);

        //Then
        assertNotNull(fermentableDto);
        assertEquals(FERMENTABLE_ID_VAL, fermentableDto.getId());
        assertEquals(FERMENTABLE_NAME,fermentableDto.getName());
        assertEquals(FERMENTABLE_AMOUNT,fermentableDto.getAmount());
        assertEquals(Fermentable.INGREDIENT_TYPE_FERMENTABLE, fermentableDto.getType());
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
        List<FermentableDto> fermentableDtos = fermentableDtoMapper.map(fermentables);

        //Then
        assertNotNull(fermentableDtos);
        assertEquals(1, fermentableDtos.size());
        assertEquals(FERMENTABLE_ID_VAL, fermentableDtos.get(0).getId());
        assertEquals(FERMENTABLE_NAME, fermentableDtos.get(0).getName());
        assertEquals(FERMENTABLE_AMOUNT, fermentableDtos.get(0).getAmount());
    }
}