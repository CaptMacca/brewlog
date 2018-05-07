package brews.mapper;

import brews.domain.Fermentable;
import brews.domain.dto.FermentableDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FermentableMapperTest {

    private FermentableMapper fermentableMapper;

    public static final Long FERMENTABLE_ID_VAL = new Long(1L);
    public static final Double FERMENTABLE_AMOUNT = 1.0d;
    public static final String FERMENTABLE_NAME = "Maris Otter Pale Malt";


    @Before
    public void setUp() throws Exception {
        this.fermentableMapper = new FermentableMapper();
    }

    @Test
    public void testFermentableDto() {
        // Given
        FermentableDto fermentableDto = new FermentableDto();
        fermentableDto.setId(FERMENTABLE_ID_VAL);
        fermentableDto.setName(FERMENTABLE_NAME);
        fermentableDto.setAmount(FERMENTABLE_AMOUNT);

        // When
        Fermentable fermentable = fermentableMapper.map(fermentableDto);

        // Then
        assertNotNull(fermentable);
        assertEquals(FERMENTABLE_ID_VAL, fermentable.getId());
        assertEquals(FERMENTABLE_NAME, fermentable.getName());
        assertEquals(FERMENTABLE_AMOUNT, fermentable.getAmount());
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
        List<Fermentable> fermentables = fermentableMapper.map(fermentableDtos);

        // Then
        assertNotNull(fermentables);
        assertEquals(1, fermentables.size());
        assertEquals(FERMENTABLE_ID_VAL, fermentables.get(0).getId());
        assertEquals(FERMENTABLE_NAME, fermentables.get(0).getName());
        assertEquals(FERMENTABLE_AMOUNT, fermentables.get(0).getAmount());
    }
}