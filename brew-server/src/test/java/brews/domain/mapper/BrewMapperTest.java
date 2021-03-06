package brews.domain.mapper;

import brews.app.presentation.dto.brew.BrewDto;
import brews.domain.*;
import brews.domain.mapper.BrewMapper;
import brews.domain.mapper.MeasurementMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class BrewMapperTest {

    @Autowired
    BrewMapper brewMapper;

    @Configuration
    @ComponentScan(basePackageClasses = {
      brews.domain.mapper.BrewMapper.class,
    })
    public static class SpringTestConfig {
    }

    @Test
    public void mapBrewDtoTest() {

        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1l);
        recipe.setName("a recipe");

        Hop hop = new Hop();
        hop.setId(1L);
        hop.setName("My Hop");
        hop.setAdditionTime(60.0);
        hop.setHopUsage("Boil");
        hop.setAmount(12.9);
        hop.setAlpha(12.0);

        Fermentable fermentable = new Fermentable();
        fermentable.setName("A Fermentable");
        fermentable.setId(1L);
        fermentable.setAddAfterBoil(Boolean.FALSE);
        fermentable.setAmount(12.0);

        Yeast yeast = new Yeast();
        yeast.setId(1L);
        yeast.setAmount(12.0);
        yeast.setName("My Yeast");

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(hop);
        ingredients.add(fermentable);
        ingredients.add(yeast);

        recipe.setIngredients(ingredients);

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setMeasurementDate(OffsetDateTime.now());
        Set<Measurement> measurements = new HashSet<>();
        measurements.add(measurement);

        User user = new User();
        user.setId(1L);
        user.setFirstName("A");
        user.setSurname("Brewer");
        user.setEmail("brewer@brewer.org");

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setBrewDate(OffsetDateTime.now());
        brew.setUser(user);
        brew.setRecipe(recipe);

        measurement.setBrew(brew);

        brew.setMeasurements(measurements);

        // When
        BrewDto brewDto = brewMapper.toBrewDto(brew);

        // Then
        assertThat(brewDto).isInstanceOf(BrewDto.class);
        assertThat(brewDto.getId()).isEqualTo(brew.getId());
        assertThat(brewDto.getRecipeId()).isNotNull();
        assertThat(brewDto.getId()).isEqualTo(1L);
//        assertThat(brewDto.getRecipe().getIngredients()).extracting("class").contains(HopDto.class,FermentableDto.class, YeastDto.class);
        assertThat(brewDto.getMeasurements()).hasSize(1);

    }
}