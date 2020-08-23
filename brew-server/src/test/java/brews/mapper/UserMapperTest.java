package brews.mapper;

import brews.domain.User;
import brews.domain.dto.UpdateUserRequest;
import brews.domain.dto.UserDetailsResponse;
import brews.mapper.domain.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    UserMapper userMapper;

    @Before
    public void setup() throws Exception {
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testUserDetailsMapping() {
        User user = new User();
        user.setEmail("joe@brewer.com");
        user.setFirstName("Joe");
        user.setSurname("Brewer");
        user.setPassword("MyPassword");

        UserDetailsResponse userDetailsResponse = this.userMapper.toUserDetailsResponse(user);

        assertThat(userDetailsResponse.getEmail()).isEqualTo("joe@brewer.com");
        assertThat(userDetailsResponse.getFirstName()).isEqualTo("Joe");
        assertThat(userDetailsResponse.getSurname()).isEqualTo("Brewer");
    }

}
