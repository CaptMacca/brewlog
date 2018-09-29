package brews.mapper;

import brews.domain.*;
import brews.domain.dto.BrewDto;
import brews.domain.dto.FermentableDto;
import brews.domain.dto.HopDto;
import brews.domain.dto.YeastDto;
import brews.mapper.domain.BrewMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class BrewMapperTest {

    @Autowired
    BrewMapper brewMapper;

    @Configuration
    @ComponentScan(basePackages = "brews.mapper")
    public static class SpringTestConfig {
    }

    @Before
    public void setup() {
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

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(hop);
        ingredients.add(fermentable);
        ingredients.add(yeast);

        recipe.setIngredients(ingredients);

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setMeasurementDate(new Date());
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement);

        Brew brew = new Brew();
        brew.setId(1L);
        brew.setBrewDate(new Date());
        brew.setBrewer("brewer");
        brew.setRecipe(recipe);

        measurement.setBrew(brew);

        brew.setMeasurements(measurements);

        // When
        BrewDto brewDto = brewMapper.toBrewDto(brew);

        // Then
        assertNotNull(brewDto);
        assertThat(brewDto, instanceOf(BrewDto.class));
        assertThat(brewDto.getId(), equalTo(brew.getId()));
        assertNotNull(brewDto.getRecipe());
        assertThat(brewDto.getId(), equalTo(1L));
        assertThat(brewDto.getBrewer(), equalTo("brewer"));
        assertThat(brewDto.getRecipe().getIngredients().size(), equalTo(3));

        String usage = ((HopDto)brewDto.getRecipe().getIngredients().get(0)).getHopUsage();

        assertThat(brewDto.getRecipe().getIngredients().get(0).getClass(), equalTo(HopDto.class));
        assertThat(brewDto.getRecipe().getIngredients().get(1).getClass(), equalTo(FermentableDto.class));
        assertThat(brewDto.getRecipe().getIngredients().get(2).getClass(), equalTo(YeastDto.class));
        assertThat(brewDto.getMeasurements().size(), equalTo(1));
        assertThat(brewDto.getMeasurements().get(0).getId(), equalTo(1L));

    }

//    @Test
//    public void mapListofBrewDtoTest() {
//    }
}