package brews;

import brews.app.config.LocalJwtConfig;
import brews.app.presentation.rest.controllers.*;
import brews.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
@ActiveProfiles("IT")
public class BrewlogApplicationTests {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:15.1"))
      .withDatabaseName("brews")
      .withUsername("brews")
      .withPassword("brews");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    BrewService brewService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    ImportRecipeService importRecipeService;

    @Autowired
    MeasurementService measurementService;

    @Autowired
    UserService userService;

    @Autowired
    AuthController authController;

    @Autowired
    BrewController brewController;

    @Autowired
    MeasurementController measurementController;

    @Autowired
    RecipeController recipeController;

    @Autowired
    UploadRecipeController uploadRecipeController;

    @Autowired
    UserController userController;

    @MockBean
    LocalJwtConfig localJwtConfig;

	@Test
	public void contextLoads() {
        assertThat(brewService).isNotNull();
        assertThat(recipeService).isNotNull();
        assertThat(importRecipeService).isNotNull();
        assertThat(measurementService).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(authController).isNotNull();
        assertThat(brewController).isNotNull();
        assertThat(measurementController).isNotNull();
        assertThat(recipeController).isNotNull();
        assertThat(uploadRecipeController).isNotNull();
        assertThat(userController).isNotNull();
	}

}
