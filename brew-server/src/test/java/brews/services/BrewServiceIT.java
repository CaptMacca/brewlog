package brews.services;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.domain.Recipe;
import brews.domain.User;
import brews.repository.BrewsRepository;
import brews.repository.RecipeRepository;
import brews.repository.UserRepository;
import brews.services.exceptions.BrewEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("IT")
public class BrewServiceIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    BrewsRepository brewsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrewService brewService;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.0")
      .withDatabaseName("brews")
      .withUsername("brews")
      .withPassword("brews");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterEach
    public void cleanUp() {
        brewsRepository.deleteAllInBatch();
        recipeRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenBrewsGetAllSucceeds() {
        List<Brew> brews = brewService.getAllBrews();
        assertThat(brews.size()).isGreaterThanOrEqualTo(0).isEqualTo(6);
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenKnownUserGetAllForUserSucceeds() {
        String userid = "testuser";
        List<Brew> test = brewService.getAllBrewsForUser(userid);
        assertThat(test.size()).isEqualTo(6);
        assertThat(test.get(0).getId()).isEqualTo(1);
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenKnownBrewGetBrewSucceeds() {
        Long brewId = 1L;
        Brew test = brewService.getBrew(brewId);
        assertThat(test.getId()).isEqualTo(1L);
        assertThat(test.getRecipe().getId()).isEqualTo(1L);
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenBrewsFindTop5BrewsForUsernameSucceeds() throws Exception {
        String userid = "testuser";
        List<Brew> test = brewService.getTop5BrewsForUser(userid);
        // Should only be 5 ie top 5
        assertThat(test.size()).isEqualTo(5);
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenBrewsFindAllBrewsForRecipeSucceeds() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setVersionId(1L);
        List<Brew> brews = brewService.getBrewsForRecipe(recipe);
        assertThat(brews.size()).isEqualTo(4);
    }

    @Test
    @Sql(scripts = "/scripts/recipe-repository-test.sql")
    public void saveBrewSucceeds() {

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setVersionId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setValue(12.0);
        measurement.setVersionId(1L);

        Set<Measurement> measurements = new HashSet<>();
        measurements.add(measurement);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setBrewDate(OffsetDateTime.now());
        brew.setEstimatedBottleVolume("12.0");
        brew.setUser(user);
        brew.setRecipe(recipe);
        brew.setMeasurements(measurements);
        brew.setVersionId(1L);

        Brew test = brewService.saveBrew(brew, user);
        List<Brew> brews = brewService.getBrewsForRecipe(recipe);
        Brew brew1 = brews.get(0);
        assertThat(brews.size()).isEqualTo(1);
        assertThat(brew1.getBrewDate()).isEqualTo(brew.getBrewDate());
        assertThat(brew1.getEstimatedBottleVolume()).isEqualTo(brew.getEstimatedBottleVolume());
    }

    @Test
    public void givenUnknownUserSaveBrewThrowsUserNameNotFoundException() throws Exception {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            // Given
            Recipe recipe = new Recipe();
            recipe.setId(1L);
            recipe.setVersionId(1L);

            User user = new User();
            user.setId(1L);
            user.setUsername("testuser");

            Measurement measurement = new Measurement();
            measurement.setId(1L);
            measurement.setValue(12.0);
            measurement.setVersionId(1L);

            Set<Measurement> measurements = new HashSet<>();
            measurements.add(measurement);

            Brew brew = new Brew();
            brew.setId(1L);
            brew.setUser(user);
            brew.setRecipe(recipe);
            brew.setMeasurements(measurements);
            brew.setVersionId(1L);

            Brew test = brewService.saveBrew(brew, user);
        });
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenKnownBrewUpdateSucceeds() {

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Optional<Brew> brewOpt = brewsRepository.findById(1L);
        brewOpt.ifPresentOrElse(brew -> {
            brew.setEstimatedBottleVolume("12.0");
            brew.setNotes("notes");
            brew.setTastingNotes("tasting notes");
            brewService.updateBrew(1L, brew, user);
        }, () -> {
            throw new BrewEntityNotFoundException();
        });
        Brew brew = brewService.getBrew(1L);
        assertThat(brew.getEstimatedBottleVolume()).isEqualTo("12.0");
        assertThat(brew.getNotes()).isEqualTo("notes");
        assertThat(brew.getTastingNotes()).isEqualTo("tasting notes");
        assertThat(brew.getVersionId()).isEqualTo(2L);
    }

    @Test()
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenUnknownUserUpdateThrowsUserNameFoundException() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            User user = new User();
            user.setId(3L);
            user.setUsername("joe");

            Brew brew = brewsRepository.findById(1L).orElseThrow(BrewEntityNotFoundException::new);
            brewService.updateBrew(1L, brew, user);
        });
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenKnownBrewDeleteSucceeds() {
        Brew brew = brewService.getBrew(1L);
        brewService.deleteBrew(1L);
        List<Brew> brews = brewService.getAllBrews();
        assertThat(brews).doesNotContain(brew);
    }

    @Test()
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenUnknownBrewUpdateThrowsBrewsEntityNotFoundException() throws Exception {
        Assertions.assertThrows(BrewEntityNotFoundException.class, () -> {
            brewService.deleteBrew(21L);
        });
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenBrewGetNotesSucceeds() throws Exception {
        String notes = brewService.getNotesForBrew(1L);
        assertThat(notes).isEqualTo("Test Notes");
   }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void givenBrewGetTastingNotesSucceeds() throws Exception {
        String tastingNotes = brewService.getTastingNotesForBrew(1L);
        assertThat(tastingNotes).isEqualTo("Test Tasting Notes");
    }
}