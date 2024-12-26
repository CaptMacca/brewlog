package brews.services;

import brews.domain.Brew;
import brews.domain.Measurement;
import brews.repository.BrewsRepository;
import brews.repository.MeasurementRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("IT")
@Slf4j
public class MeasurementServiceIT {

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    BrewsRepository brewsRepository;

    @Autowired
    private MeasurementService measurementService;

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
        measurementRepository.deleteAllInBatch();
    }

    @Test
    @Sql(scripts = "/scripts/measurement-repository-test.sql")
    public void givenMeasurementGetMeasurementSucceeds() {
        Measurement test = measurementService.getMeasurement(1L);
        assertThat(test.getId()).isEqualTo(1L);
    }

    @Test
    @Sql(scripts = "/scripts/measurement-repository-test.sql")
    public void givenMeasurmentAndBrewGetMeasurementsForBrewSucceeds() {
        List<Measurement> test = measurementService.getMeasurementsForBrew(1L);
        assertThat(test).hasSize(1);
    }

    @Test
    @Sql(scripts = "/scripts/measurement-repository-test.sql")
    public void givenValidMeasurementCreateMeasurementSucceeds() {
        Brew brew = brewsRepository.findBrewsByUserUsername("testuser").get(0);

        Measurement measurement = new Measurement();
        measurement.setBrew(brew);
        measurement.setMeasurementDate(OffsetDateTime.now());
        measurement.setValue(1044.0);
        measurement.setVersionId(1L);

        Measurement fixture = measurementService.createMeasurement(1L, measurement);
        assertThat(fixture.getId()).isEqualTo(2L);
        assertThat(fixture.getBrew().getId()).isEqualTo(1L);
        assertThat(fixture.getValue()).isEqualTo(1044.0);
        assertThat(fixture.getMeasurementDate()).isEqualTo(measurement.getMeasurementDate());
    }

    @Test
    @Sql(scripts = "/scripts/measurement-repository-test.sql")
    public void givenMeasurmentUpdateMeasurementSucceeds() {
        Measurement measurement = measurementService.getMeasurementsForBrew(1L).get(0);
        measurement.setMeasurementDate(OffsetDateTime.now());
        measurement.setValue(1043.0);
        Measurement fixture = measurementService.updateMeasurement(measurement);

        assertThat(fixture.getMeasurementDate()).isEqualTo(measurement.getMeasurementDate());
        assertThat(fixture.getValue()).isEqualTo(1043.0);
    }

    @Test
    @Sql(scripts = "/scripts/measurement-repository-test.sql")
    public void givenMeasurementDeleteMeasurementSucceeds() {
        Measurement measurement = measurementService.getMeasurementsForBrew(1L).get(0);
        measurementService.deleteMeasurement(measurement.getId());
        assertThat(measurementRepository.findMeasurementsByBrewId(1L)).isEmpty();
    }
}