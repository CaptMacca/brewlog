package brews.mapper;

import brews.domain.User;
import brews.domain.dto.UserDto;
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
    public void testUserDtoMapping() {
        User user = new User();
        user.setId(1L);
        user.setEmail("joe@brewer.com");
        user.setFirstName("Joe");
        user.setSurname("Brewer");
        user.setUsername("joe");
        user.setPassword("MyPassword");

        UserDto userDto = this.userMapper.toUserDto(user);

        assertThat(userDto.getId()).isEqualTo(1L);
        assertThat(userDto.getEmail()).isEqualTo("joe@brewer.com");
        assertThat(userDto.getFirstName()).isEqualTo("Joe");
        assertThat(userDto.getSurname()).isEqualTo("Brewer");
        assertThat(userDto.getUsername()).isEqualTo("joe");
    }

}
