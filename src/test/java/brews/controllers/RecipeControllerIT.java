package brews.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by Steve on 3/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllRecipes() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/brews/recipes", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testGetRecipe() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/brews/recipes/1" , String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

}
