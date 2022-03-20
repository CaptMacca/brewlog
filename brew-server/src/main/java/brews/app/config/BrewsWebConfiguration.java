package brews.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class BrewsWebConfiguration implements WebMvcConfigurer {

    @Value("brews.app.cors.origins")
    private String validOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(validOrigins)
                .allowedMethods("GET,POST,DELETE,PUT,OPTIONS")
                .allowedHeaders("*");
        }
    }

