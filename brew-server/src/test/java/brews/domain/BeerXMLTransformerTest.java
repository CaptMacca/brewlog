package brews.domain;

import brews.MockXMLRecipe;
import brews.domain.beerxml.BeerXMLTransformer;
import brews.domain.beerxml.mapping.*;
import brews.services.exceptions.BeerXMLParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class BeerXMLTransformerTest {

    @Autowired
    BeerXMLTransformer beerXMLTransformer;

    BeerXMLRecipeMapper beerXMLRecipeMapper;

    ImportFermentablesMapper importFermentablesMapper;

    ImportHopMapper importHopMapper;

    ImportYeastMapper importYeastMapper;

    ImportMashMapper importMashMapper;

    @TestConfiguration
    static class BeerXMLTestConfiguration {

        @Bean
        ImportFermentablesMapper importFermentablesMapper() {
            return new ImportFermentablesMapper();
        }

        @Bean
        ImportHopMapper importHopMapper() {
            return new ImportHopMapper();
        }

        @Bean
        ImportMashMapper importMashMapper() {
            return new ImportMashMapper();
        }

        @Bean
        ImportYeastMapper importYeastMapper() {
            return new ImportYeastMapper();
        }

        @Bean
        BeerXMLRecipeMapper beerXMLRecipeMapper() {
            return new BeerXMLRecipeMapper(importFermentablesMapper(), importHopMapper(), importYeastMapper(), importMashMapper());
        }

        @Bean
        BeerXMLTransformer beerXMLTransformer() {
            return new BeerXMLTransformer(beerXMLRecipeMapper());
        }
    }

    @Test
    public void given_valid_beerxml_recipe_parse_succeeds() {
        InputStream mockXMLRecipeStream = MockXMLRecipe.getMockedXMLRecipe();
        List<Recipe> recipes = beerXMLTransformer.convertBeerXmlRecipes(mockXMLRecipeStream);
        assertThat(recipes).isNotNull()
          .isNotEmpty()
          .hasSize(1);
    }

    @Test
    public void given_invalid_beerxml_recipe_throws_BeerXMLParserException() {
        Assertions.assertThrows(BeerXMLParserException.class, () -> {
            String rubbish = "sdasda2xcz";

            InputStream mockXMLRecipeStream = new ByteArrayInputStream(rubbish.getBytes());
            List<Recipe> recipes = beerXMLTransformer.convertBeerXmlRecipes(mockXMLRecipeStream);
        });
    }
}
