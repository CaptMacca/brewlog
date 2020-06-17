package brews.mapper;

import brews.domain.*;
import brews.domain.dto.FermentableDto;
import brews.domain.dto.HopDto;
import brews.domain.dto.RecipeDto;
import brews.domain.dto.YeastDto;
import brews.mapper.domain.RecipeMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeMapperTest {

    @Autowired
    private RecipeMapper recipeMapper;

    @Configuration
    @ComponentScan(basePackages = "brews.mapper")
    public static class SpringTestConfig {
    }

    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testRecipeDtoMapping() throws Exception {
        // Given
        Yeast yeast = new Yeast();
        yeast.setId(1L);

        Hop hop = new Hop();
        hop.setId(1L);
        hop.setAdditionTime(20.0);

        Fermentable fermentable = new Fermentable();
        fermentable.setId(1L);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(yeast);
        ingredients.add(hop);
        ingredients.add(fermentable);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("My Recipe");
        recipe.setBatchSize("21L");
        recipe.setBoilTime("60 min");
        recipe.setIbu("30 IBU");
        recipe.setIngredients(ingredients);

        // When
        RecipeDto recipeDto = recipeMapper.toRecipeDto(recipe);

        // Then
        assertThat(recipeDto.getId()).isEqualTo(1L);
        assertThat(recipeDto.getIngredients()).hasSize(3);
        assertThat(recipeDto.getIngredients()).extracting("class").contains(YeastDto.class,HopDto.class,FermentableDto.class);
    }
}
