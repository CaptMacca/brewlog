package brews.services;

import brews.domain.Role;
import brews.domain.RoleName;
import brews.domain.User;
import brews.repository.RoleRepository;
import brews.repository.UserRepository;
import brews.services.exceptions.UserAlreadyRegisteredException;
import brews.services.exceptions.UserNotAuthenticatedException;
import brews.services.exceptions.UserPasswordMismatchException;
import brews.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml","brews.app.jwtExpiration=86400"})
@ActiveProfiles("IT")
@Sql(scripts = "/scripts/user-repository-test.sql")
@Testcontainers
public class UserServiceIT {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    UserService userService;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.0")
      .withDatabaseName("brews")
      .withUsername("brews")
      .withPassword("brews");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @Test
    @WithMockUser(username ="testuser", password="password1234")
    public void given_authenticated_user_getcurrentuserdetails_succeeds() {
        User authenticateUser = userService.getCurrentUserDetails();
        assertThat(authenticateUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void given_unauthenticated_user_getcurrentuserdetails_throws_UserNotAuthenticated() {
        Assertions.assertThrows(UserNotAuthenticatedException.class,() -> {
            User authenticateUser = userService.getCurrentUserDetails();
        });
    }

    @Test
    @WithMockUser(username ="joe@company.com", password="password1234")
    public void givenUserUpdateDetailsSucceeds() {
        userRepository.findByUsername("testuser").ifPresent(user -> {
            user.setFirstName("joe");
            User updatedUser = userService.updateUserDetails("testuser", user);
            assertThat(updatedUser.getFirstName()).isEqualTo("joe");
        });
    }

    @Test
    @WithUserDetails("testuser")
    public void givenUserWithMatchingPasswordUpdatePasswordSucceeds() {

        String oldPassword = "password1234";
        String newPassword = "password12345";

        User user = new User();
        boolean updated = userService.updatePassword(oldPassword, newPassword);
        assertThat(updated).isTrue();
    }

    @Test
    @WithUserDetails("testuser")
    public void givenUserAndWrongPasswordUpdatePasswordFails() {

        Assertions.assertThrows(UserPasswordMismatchException.class, () -> {
            String oldPassword = "password14";
            String newPassword = "password12345";

            boolean updated = userService.updatePassword(oldPassword, newPassword);
        });
    }

    @Test
    public void givenUnauthenticatedUserUpdatePasswordFails() {

        Assertions.assertThrows(UserNotAuthenticatedException.class, () -> {
            String oldPassword = "password1234";
            String newPassword = "password12345";
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

        User registeredUser = userService.registerUser(userCandidate, roles);
        assertThat(registeredUser.getFirstName()).isEqualTo(userCandidate.getFirstName());
        assertThat(registeredUser.getSurname()).isEqualTo(userCandidate.getSurname());
        assertThat(registeredUser.getEmail()).isEqualTo(userCandidate.getEmail());
        assertThat(registeredUser.getUsername()).isEqualTo(userCandidate.getUsername());
    }

    @Test
    @Sql(scripts = "/scripts/user-repository-test.sql")
    public void givenExistingUsernameRegistrationFails() {
        Assertions.assertThrows(UserAlreadyRegisteredException.class, () -> {
            Set<String> roles = new HashSet<>();
            roles.add(RoleName.ROLE_USER.name());

            User userCandidate = new User();
            userCandidate.setFirstName("test");
            userCandidate.setSurname("user");
            userCandidate.setPassword("password1234");
            userCandidate.setEmail("test_user@brews.com");
            userCandidate.setUsername("testuser");

            User registeredUser = userService.registerUser(userCandidate, roles);
        });
    }

    @Test
    @Sql(scripts = "/scripts/user-repository-test.sql")
    public void givenExistingUserEmailAddressRegistrationFails() {
        Assertions.assertThrows(UserAlreadyRegisteredException.class, () -> {
            Set<String> roles = new HashSet<>();
            roles.add(RoleName.ROLE_USER.name());

            User userCandidate = new User();
            userCandidate.setFirstName("joe");
            userCandidate.setSurname("surname");
            userCandidate.setPassword("password");
            userCandidate.setEmail("test_user@brews.com");
            userCandidate.setUsername("user@somewhere.com");

            User registeredUser = userService.registerUser(userCandidate, roles);
        });
    }

}
