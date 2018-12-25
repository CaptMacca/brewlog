package brews.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BrewsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // TODO: Temp CORS Setup
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200","http://192.168.99.100:4200")
                        .allowedMethods("GET,POST,DELETE,PUT")
                        .allowedHeaders("*");

            }
        };
    }

}