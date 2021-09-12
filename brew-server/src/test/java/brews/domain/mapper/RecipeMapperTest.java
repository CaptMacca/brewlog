package brews.domain.mapper;

import brews.app.presentation.dto.recipe.FermentableDto;
import brews.app.presentation.dto.recipe.HopDto;
import brews.app.presentation.dto.recipe.RecipeDto;
import brews.app.presentation.dto.recipe.YeastDto;
import brews.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class RecipeMapperTest {

    @Autowired
    private RecipeMapper recipeMapper;

    @Configuration
    @ComponentScan(basePackageClasses = {
      brews.domain.mapper.RecipeMapper.class,
      brews.domain.mapper.IngredientMapper.class,
    })
    public static class SpringTestConfig {
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
