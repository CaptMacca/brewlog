package brews.domain.mapper;

import brews.app.presentation.dto.user.UserDetailsDto;
import brews.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Configuration
    @ComponentScan(basePackageClasses = {
      brews.domain.mapper.UserMapper.class,
    })
    public static class SpringTestConfig {
    }


    @Test
    public void testUserDetailsMapping() {
        User user = new User();
        user.setEmail("joe@brewer.com");
        user.setFirstName("Joe");
        user.setSurname("Brewer");
        user.setPassword("MyPassword");

        UserDetailsDto userDetailsResponse = this.userMapper.toUserDetailsResponse(user);

        assertThat(userDetailsResponse.getEmail()).isEqualTo("joe@brewer.com");
        assertThat(userDetailsResponse.getFirstName()).isEqualTo("Joe");
        assertThat(userDetailsResponse.getSurname()).isEqualTo("Brewer");
    }

}
