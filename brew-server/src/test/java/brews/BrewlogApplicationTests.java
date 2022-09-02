package brews;

import brews.app.presentation.rest.controllers.*;
import brews.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class BrewlogApplicationTests {

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
