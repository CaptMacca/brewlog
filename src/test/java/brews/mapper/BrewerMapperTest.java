package brews.mapper;

import brews.domain.Brewer;
import brews.domain.dto.BrewerDto;
import brews.mapper.domain.BrewerMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class BrewerMapperTest {

    @Autowired
    BrewerMapper brewerMapper;

    @Configuration
    @ComponentScan(basePackages = "brews.mapper")
    public static class SpringTestConfig {
    }

    @Before
    public void setup() {
    }

    @Test
    public void testBrewerDto() {

        OffsetDateTime now = OffsetDateTime.now();
        Brewer brewer = new Brewer();
        brewer.setId(1L);
        brewer.setGivenName("John");
        brewer.setSurname("Doe");
        brewer.setEmail("john.doe@brewer.com");
        brewer.setEnabled(true);
        brewer.setCreatedOn(now);

        BrewerDto brewerDto = brewerMapper.toBrewerDto(brewer);

        assertThat(brewerDto.getId()).isEqualTo(1L);
        assertThat(brewerDto.getGivenName()).isEqualTo("John");
        assertThat(brewerDto.getSurname()).isEqualTo("Doe");
        assertThat(brewerDto.getEmail()).isEqualTo("john.doe@brewer.com");
        assertThat(brewerDto.getEnabled()).isTrue();
        assertThat(brewerDto.getCreatedOn()).isEqualTo(now);
    }

}
