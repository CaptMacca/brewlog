package brews.repository;

import brews.domain.Brew;
import brews.domain.User;
import brews.services.exceptions.BrewEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("IT")
public class BrewRepositoryIT {

    @Autowired
    private BrewsRepository brewsRepository;

    @Autowired
    private UserRepository userRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.1"))
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
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void testFindById() {
        Optional<Brew> foundBrew = brewsRepository.findById(1L);
        assertThat(foundBrew).isPresent();
        assertThat(foundBrew.get().getBrewDate()).isEqualTo(OffsetDateTime.parse("2020-01-01T00:00:00Z"));
    }

    @Test
    @Sql(scripts = "/scripts/user-repository-test.sql")
    public void testSave() {

        Optional<User> user = userRepository.findByUsername("testuser");
        user.ifPresentOrElse(u -> {
            Brew newBrew = new Brew();
            newBrew.setBrewDate(OffsetDateTime.now());
            newBrew.setEstimatedFermentVolume("20.0");
            newBrew.setEstimatedBottleVolume("18.0");
            newBrew.setUser(u);
            Brew savedBrew = brewsRepository.save(newBrew);
            assertThat(savedBrew).isNotNull();
            assertThat(savedBrew.getBrewDate()).isEqualTo(newBrew.getBrewDate());
            assertThat(savedBrew.getEstimatedFermentVolume()).isEqualTo(newBrew.getEstimatedFermentVolume());
            assertThat(savedBrew.getEstimatedBottleVolume()).isEqualTo(newBrew.getEstimatedBottleVolume());
          }, () -> {
            throw new BrewEntityNotFoundException("User not found");
          });
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void testDelete() {
        Optional<Brew> foundBrew = brewsRepository.findById(1L);
        foundBrew.ifPresentOrElse(b -> {
            brewsRepository.delete(b);
          }, () -> {
            throw new BrewEntityNotFoundException("Brew not found");
          });
        Optional<Brew> deletedBrew = brewsRepository.findById(1L);
        assertThat(deletedBrew).isNotPresent();
    }

    @Test
    @Sql(scripts = "/scripts/brews-repository-test.sql")
    public void testFindAll() {
        Iterable<Brew> brews = brewsRepository.findAll();
        assertThat(brews).isNotEmpty();
    }
}
