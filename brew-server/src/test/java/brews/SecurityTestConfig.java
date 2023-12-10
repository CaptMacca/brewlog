package brews;

import brews.app.security.UserPrinciple;
import brews.app.security.jwt.JwtAuthTokenFilter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@TestConfiguration
public class SecurityTestConfig {

    private static final String[] AUTH_WHITELIST = {
      "/api/auth/**",
      "/api/user/signup",
      "/actuator/**",
      "/v3/api-docs/**",
      "/configuration/ui",
      "/swagger-resources/**",
      "/configuration/security",
      "/swagger-ui/**",
      "/swagger-config/**",
      "/webjars/**"
    };

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider
          .setUserDetailsService(mockUserDetailsService());
        authenticationProvider
          .setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable()
            .authorizeRequests()
            .requestMatchers(AUTH_WHITELIST)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(authenticationProvider())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().build();
    }

}
