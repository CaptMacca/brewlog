package brews.services;

import brews.SecurityTestConfig;
import brews.app.config.JwtConfig;
import brews.app.config.LocalJwtConfig;
import brews.app.security.UserPrinciple;
import brews.app.security.jwt.JwtProvider;
import brews.domain.Role;
import brews.domain.RoleName;
import brews.domain.User;
import brews.repository.RoleRepository;
import brews.repository.UserRepository;
import brews.services.exceptions.UserAlreadyRegisteredException;
import brews.services.exceptions.UserNotAuthenticatedException;
import brews.services.exceptions.UserPasswordMismatchException;
import brews.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml","brews.app.jwtExpiration=86400"})
@ActiveProfiles("test")
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    UserService userService;

    @TestConfiguration
    static class UserServiceTestConfiguration {

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        JwtConfig jwtConfig() {
            return new LocalJwtConfig();
        }

        @Bean
        public JwtProvider jwtProvider() {
            return new JwtProvider(jwtConfig());
        }

        @Bean
        @Primary
        public UserDetailsService mockUserDetailsService() {

            PasswordEncoder encoder =new BCryptPasswordEncoder();

            UserPrinciple basicActiveUser =
              new UserPrinciple(1L, "joe", "brewer","joe@company.com", "joe@company.com",
                encoder.encode("password1234"),
                Arrays.asList(
                  new SimpleGrantedAuthority("ROLE_USER")
                )
              );

            UserPrinciple managerActiveUser =
              new UserPrinciple(2L, "admin", "", "admin@company.com", "admin@company.com",
                encoder.encode("password1234"),
                Arrays.asList(
                  new SimpleGrantedAuthority("ROLE_USER,"),
                  new SimpleGrantedAuthority("ROLE_MANAGER")
                )
              );

            return new InMemoryUserDetailsManager(Arrays.asList(
              basicActiveUser, managerActiveUser
            ));
        }

    }


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
    public void givenUserUpdateDetailsSucceeds() {
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
    @WithUserDetails("joe@company.com")
    public void givenUserWithMatchingPasswordUpdatePasswordSucceeds() {

        String oldPassword = "password1234";
        String newPassword = "password12345";

        User user = new User();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        boolean updated = userService.updatePassword(oldPassword, newPassword);
        assertTrue(updated);
    }

    @Test
    @WithUserDetails("joe@company.com")
    public void givenUserAndWrongPasswordUpdatePasswordFails() {

        Assertions.assertThrows(UserPasswordMismatchException.class, () -> {
            String oldPassword = "password14";
            String newPassword = "password12345";

            User user = new User();
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
            when(userRepository.save(user)).thenReturn(user);

            boolean updated = userService.updatePassword(oldPassword, newPassword);
            assertFalse(updated);
        });
    }

    @Test
    public void givenUnauthenticatedUserUpdatePasswordFails() {

        Assertions.assertThrows(UserNotAuthenticatedException.class, () -> {
            String oldPassword = "password1234";
            String newPassword = "password12345";

            User user = new User();
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
            when(userRepository.save(user)).thenReturn(user);

            userService.updatePassword(oldPassword, newPassword);
        });
    }

    @Test
    public void givenUserRegistrationSucceeds() {
        Set<String> roles = new HashSet<>();
        roles.add(RoleName.ROLE_USER.name());
        roles.add(RoleName.ROLE_ADMIN.name());

        User userCandidate = new User();
        userCandidate.setFirstName("name");
        userCandidate.setSurname("surname");
        userCandidate.setPassword("password");
        userCandidate.setEmail("user@somewhere.com");
        userCandidate.setUsername("user@somewhere.com");

        when(roleRepository.findByRoleName(RoleName.ROLE_USER)).thenReturn(Optional.of(mockUserRole()));
        when(roleRepository.findByRoleName(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(mockAdminRole()));
        when(userRepository.save(any(User.class))).thenReturn(mockUser());

        User registeredUser = userService.registerUser(userCandidate, roles);
        assertNotNull(registeredUser);
        verify(roleRepository, times(2)).findByRoleName(any(RoleName.class));
        verify(userRepository).existsByEmail(userCandidate.getEmail());
        verify(userRepository).existsByUsername(userCandidate.getUsername());
        verify(userRepository).save(userCandidate);
    }

    @Test
    public void givenExistingUsernameRegistrationFails() {
        Assertions.assertThrows(UserAlreadyRegisteredException.class, () -> {
            Set<String> roles = new HashSet<>();
            roles.add(RoleName.ROLE_USER.name());

            User userCandidate = new User();
            userCandidate.setFirstName("name");
            userCandidate.setSurname("surname");
            userCandidate.setPassword("password");
            userCandidate.setEmail("user@somewhere.com");
            userCandidate.setUsername("user@somewhere.com");

            when(userRepository.existsByUsername(userCandidate.getUsername())).thenReturn(true);

            User registeredUser = userService.registerUser(userCandidate, roles);
            verify(userRepository).existsByUsername(userCandidate.getUsername());
        });
    }

    @Test
    public void givenExistingUserEmailAddressRegistrationFails() {
        Assertions.assertThrows(UserAlreadyRegisteredException.class, () -> {
            Set<String> roles = new HashSet<>();
            roles.add(RoleName.ROLE_USER.name());

            User userCandidate = new User();
            userCandidate.setFirstName("name");
            userCandidate.setSurname("surname");
            userCandidate.setPassword("password");
            userCandidate.setEmail("user@somewhere.com");
            userCandidate.setUsername("user@somewhere.com");

            when(userRepository.existsByUsername(userCandidate.getUsername())).thenReturn(false);
            when(userRepository.existsByEmail(userCandidate.getEmail())).thenReturn(true);

            User registeredUser = userService.registerUser(userCandidate, roles);
            verify(userRepository).existsByUsername(userCandidate.getUsername());
            verify(userRepository).existsByEmail(userCandidate.getEmail());
        });
    }

    private Role mockUserRole() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName(RoleName.ROLE_USER);
        return role;
    }

    private Role mockAdminRole() {
        Role role = new Role();
        role.setId(2L);
        role.setRoleName(RoleName.ROLE_ADMIN);
        return role;
    }

    private User mockUser() {
        User user = new User();
        user.setId(1L);
        return user;
    }


}
