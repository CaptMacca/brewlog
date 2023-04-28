package brews.app.presentation.rest.controllers;

import brews.SecurityTestConfig;
import brews.app.config.LocalJwtConfig;
import brews.app.security.jwt.JwtProvider;
import brews.app.security.jwt.request.LoginForm;
import brews.domain.User;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityConfig.class, AuthController.class, AuthenticationManager.class, JwtProvider.class, LocalJwtConfig.class})
@Import(SecurityTestConfig.class)
@WebMvcTest(controllers = {AuthController.class})
@WebAppConfiguration
@ActiveProfiles("Test")
@Slf4j
class AuthControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    AuthController authController;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDetailsService userDetailsService;

    ObjectMapper objectMapper = new ObjectMapper();

    @TestConfiguration
    public class AuthControllerTestConfiguration  {

        @Bean(autowireCandidate = true)
        public AuthenticationManager authenticationManager() throws Exception{
            return webApplicationContext.getBean(AuthenticationConfiguration.class)
                                        .getAuthenticationManager();
        }
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.authController = new AuthController(authenticationManager, jwtProvider);

        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(webApplicationContext)
          .apply(springSecurity())
          .defaultRequest(post("/api/auth/signin").secure(false))
          .build();
    }

    @Test
    @WithMockUser(username ="joe@company.com", password="password1234")
    public void givenValidUserLoginSucceeds() throws Exception {

        String mockToken = "this is my token";
        User user = new User();
        user.setUsername("joe@company.com");
        user.setPassword("password1234");

        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(user.getUsername());
        loginForm.setPassword(user.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(authentication);
        given(jwtProvider.generateJwtToken(any(Authentication.class))).willReturn(mockToken);

        String content = objectMapper.writeValueAsString(loginForm);

        mockMvc.perform(post("/api/auth/signin").with(csrf().asHeader()).contentType("application/json")
          .content(content))
          .andExpect(status().isOk())
          .andDo(MockMvcResultHandlers.print())
          .andExpect(jsonPath("$.username").isNotEmpty())
          .andExpect(jsonPath("$.authorities").isNotEmpty())
          .andExpect(jsonPath("$.tokenType").isNotEmpty())
          .andExpect(jsonPath("$.accessToken").isNotEmpty())
          .andExpect(jsonPath("$.accessToken").value(mockToken));
    }

    public void givenInvalidUserWillFailLogin() throws Exception {

    }
}