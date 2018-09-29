package brews.mapper;

import brews.domain.Mash;
import brews.domain.dto.MashDto;
import brews.mapper.domain.MashMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class MashMapperTest {

    MashMapper mashDtoMapping;

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
        assertNotNull(mashDto);
        assertThat(1L, equalTo(mashDto.getId().longValue()));
        assertThat(13.0, equalTo(mashDto.getStepTemp()));
        assertThat("My Mash Step", equalTo(mashDto.getName()));
        assertThat(12.0, equalTo(mashDto.getStepTime()));
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
        assertThat(2, equalTo(mashDtos.size()));
        assertThat(mashDtos.get(0).getId(), equalTo(1L));
        assertThat(mashDtos.get(1).getId(), equalTo(2L));

    }
}
