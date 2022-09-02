package brews;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SecurityTestConfiguration {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        User basicActiveUser = new User("joe@company.com", "password1234", Arrays.asList(
          new SimpleGrantedAuthority("ROLE_USER")
        ));

        User managerActiveUser = new User("admin@company.com", "password1234", Arrays.asList(
          new SimpleGrantedAuthority("ROLE_MANAGER")
        ));

        return new InMemoryUserDetailsManager(Arrays.asList(
          basicActiveUser, managerActiveUser
        ));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
