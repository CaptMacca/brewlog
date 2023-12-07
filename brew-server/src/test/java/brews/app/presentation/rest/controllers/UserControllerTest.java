package brews.app.presentation.rest.controllers;

import brews.SecurityTestConfig;
import brews.app.config.JwtConfig;
import brews.app.config.LocalJwtConfig;
import brews.app.security.jwt.JwtProvider;
import brews.app.security.jwt.request.LoginForm;
import brews.app.security.jwt.response.JwtResponse;
import brews.domain.User;
import brews.domain.mapper.UserMapper;
import brews.domain.mapper.UserMapperImpl;
import brews.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityConfig.class, UserController.class, AuthController.class, AuthenticationManager.class, JwtProvider.class, LocalJwtConfig.class, SecurityTestConfig.class, UserMapper.class, UserMapperImpl.class})
@Import({SecurityTestConfig.class, JwtProvider.class})
@WebMvcTest(controllers = {UserController.class, AuthController.class})
@ActiveProfiles("test")
@WebAppConfiguration
@Slf4j
class UserControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    MockMvc mockMvc;

    UserController userController;
    AuthController authController;

    private static final String username = "joe@company.com";
    private static final String password = "password1234";
    private static final String firstName = "Joe";
    private static final String lastName = "Brewer";
    private static final String email = "joe@company.com";

    @TestConfiguration
    public class UserControllerConfiguration {
        @Bean
        UserMapper userMapper() {
            return new UserMapperImpl();
        }


        @Bean
        JwtProvider jwtProvider() {
            return new JwtProvider(new LocalJwtConfig());
        }

        @Bean
        JwtConfig jwtConfig() {
            return new LocalJwtConfig();
        }

        @Bean(autowireCandidate = true)
        public AuthenticationManager authenticationManager() throws Exception{
            return webApplicationContext.getBean(AuthenticationConfiguration.class)
              .getAuthenticationManager();
        }
    }


    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);

        userController = new UserController(userService, userMapper);
        authController = new AuthController(authenticationManager, jwtProvider);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
          .apply(springSecurity())
           .build();
    }

    @Test
    void givenValidUserGetCurrentUserDetailsSucceeds() throws Exception {

        when(userService.getCurrentUserDetails()).thenReturn(new User(firstName, lastName, username, email, password));

        String token = getAuthToken();
        mockMvc.perform(get("/api/user")
          .header("Authorization", "Bearer " + token))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.firstName", is(firstName)))
          .andExpect(jsonPath("$.surname", is(lastName)))
          .andExpect(jsonPath("$.email", is(email)));

        verify(userService).getCurrentUserDetails();
    }

    @Test
    void testUserUpdate() throws Exception {
        String token = getAuthToken();
        mockMvc.perform(get("/api/user")
          .header("Authorization", "Bearer " + token))
          .andExpect(status().isOk());
    }

    @Test
    void givenUserDetailsUpdateUserSucceeds() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void registerUser() {
    }

    private String getAuthToken() throws Exception {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("joe@company.com");
        loginForm.setPassword("password1234");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(GrantedAuthority.class);

        //Login using loginform to auth controller and obtain token
        MvcResult result = mockMvc.perform(post("/api/auth/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginForm)))
          .andExpect(status().isOk())
          .andReturn();

        JwtResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), JwtResponse.class);

        return response.getAccessToken();
    }
}