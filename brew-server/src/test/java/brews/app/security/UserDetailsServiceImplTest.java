package brews.app.security;

import brews.domain.User;
import brews.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDetailsService = new UserDetailsServiceImpl();
        userDetailsService.setUserRepository(userRepository);
    }


    @Test
    public void given_user_it_saves() {
        User user = getUser();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void given_validusername_succeeds()  {

        User user = getUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        assertThat(userDetails).isNotNull().hasFieldOrProperty("username");
    }

    @Test
    public void given_invalidusername_throws_UsernameNotFoundException() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            String username = "bad_user";
            when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        });
    }

    private User getUser() {
        String username = "user";

        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setRoles(new HashSet<>());
        user.setFirstName("first name");
        user.setSurname("surname");
        user.setPassword("a password");
        user.setEmail("an email");

        return user;
    }
}
