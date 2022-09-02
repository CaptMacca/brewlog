package brews.services;

import brews.SecurityTestConfiguration;
import brews.domain.Role;
import brews.domain.RoleName;
import brews.domain.User;
import brews.repository.RoleRepository;
import brews.repository.UserRepository;
import brews.services.exceptions.UserNotAuthenticatedException;
import brews.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Import(SecurityTestConfiguration.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    UserService userService;

    static final String userPassword = new BCryptPasswordEncoder().encode("password1234");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    @WithMockUser(username ="joe@company.com", password="password1234")
    public void given_authenticated_user_getcurrentuserdetails_succeeds() {
        // Given
        Role role = new Role(RoleName.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        brews.domain.User user = new User();
        user.setUsername("joe@company.com");
        user.setFirstName("joe");
        user.setId(1l);
        user.setEmail("joe@company.com");
        user.setRoles(roles);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // When
        brews.domain.User authenticateUser = userService.getCurrentUserDetails();

        //
        assertThat(authenticateUser.getUsername()).isEqualTo("joe@company.com");
        assertThat(authenticateUser.getId()).isEqualTo(1l);
    }

    @Test
    public void given_unauthenticated_user_getcurrentuserdetails_throws_UserNotAuthenticated() {
        Assertions.assertThrows(UserNotAuthenticatedException.class,() -> {
            // When
            brews.domain.User authenticateUser = userService.getCurrentUserDetails();
        });
    }

    @Test
    @WithMockUser(username ="joe@company.com", password="password1234")
    public void given_user_update_details_succeeds() {
        // Given
        Role role = new Role(RoleName.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        brews.domain.User user = new User();
        user.setUsername("joe@company.com");
        user.setFirstName("joe");
        user.setId(1l);
        user.setEmail("joe@company.com");
        user.setRoles(roles);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUserDetails("joe@company.com", user);

        assertThat(updatedUser.getUsername()).isEqualTo("joe@company.com");
    }

    @Test
    @WithMockUser(username ="joe@company.com", password = "password1234")
    public void given_user_update_password_succeeds() {
        Role role = new Role(RoleName.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        brews.domain.User user = new User();
        user.setUsername("joe@company.com");
        user.setFirstName("joe");
        user.setId(1l);
        user.setEmail("joe@company.com");
        user.setRoles(roles);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        String encodedPassword = passwordEncoder.encode("password1234");
        user.setPassword(encodedPassword);

        boolean updated = userService.updatePassword(encodedPassword, "newPassword1234");

    }
}
