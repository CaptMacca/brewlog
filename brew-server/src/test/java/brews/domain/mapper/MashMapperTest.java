package brews.domain.mapper;

import brews.domain.Mash;
import brews.app.presentation.dto.recipe.MashDto;
import brews.domain.mapper.MashMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MashMapperTest {

    private MashMapper mashDtoMapping;

    @Before
    public void setup() throws Exception {
        this.mashDtoMapping = Mappers.getMapper(MashMapper.class);
    }

    @Test
    public void testMashDtoMapping() throws Exception {
        // Given
        Mash mash = new Mash();
        mash.setId(1L);
        mash.setStepTemp(13.0);
        mash.setName("My Mash Step");
        mash.setStepTime(12.0);

        // When
        MashDto mashDto = mashDtoMapping.toMashDto(mash);

        // Then
        assertThat(mashDto.getId()).isEqualTo(1L);
        assertThat(mashDto.getStepTemp()).isEqualTo(13.0);
        assertThat(mashDto.getName()).isEqualTo("My Mash Step");
        assertThat(mashDto.getStepTime()).isEqualTo(12.0);
    }

    @Test
    public void testListofMashes() throws Exception {
        //Given
        Mash mash1 = new Mash();
        Mash mash2 = new Mash();
        mash1.setId(1L);
        mash2.setId(2L);
        List<Mash> mashes = new ArrayList<>();
        mashes.add(mash1);
        mashes.add(mash2);

        //When
        List<MashDto> mashDtos = mashDtoMapping.toMashDtos(mashes);

        //Then
        assertThat(mashDtos).hasSize(2);
        assertThat(mashDtos.get(0).getId()).isEqualTo(1L);
        assertThat(mashDtos.get(1).getId()).isEqualTo(2L);
    }
}
