package brews.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(title = "Brews Api", version = "v1"),
  servers = @Server(
    url = "http://localhost:8080"
  ) )
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  scheme = "bearer"
)
public class OpenApi3Config {

//    @Bean
//    public OpenAPI brewsOpenAPI() {
//        return new OpenAPI()
//          .info(new io.swagger.v3.oas.models.info.Info().title("SpringShop API")
//            .description("Spring shop sample application")
//            .version("v0.0.1")
//            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//          .externalDocs(new ExternalDocumentation()
//            .description("SpringShop Wiki Documentation")
//            .url("https://springshop.wiki.github.org/docs"));
//    }
}
