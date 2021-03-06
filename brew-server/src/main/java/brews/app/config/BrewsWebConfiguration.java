package brews.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BrewsWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200/*","http://192.168.99.100:4200")
                .allowedMethods("GET,POST,DELETE,PUT,OPTIONS")
                .allowedHeaders("*");
        }
    }

