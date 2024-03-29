package brews.domain.beerxml.mapping;

import brews.MockImportedRecipe;
import brews.domain.*;
import brews.domain.beerxml.mapping.*;
import brews.domain.beerxml.model.ImportedRecipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerXMLRecipeMapperTest {

    private BeerXMLRecipeMapper beerXMLRecipeMapper;

    @BeforeEach
    public void setUp() throws Exception {

        ImportFermentablesMapper importFermentablesMapper = new ImportFermentablesMapper();
        ImportHopMapper importHopMapper = new ImportHopMapper();
        ImportMashMapper importMashMapper = new ImportMashMapper();
        ImportYeastMapper importYeastMapper = new ImportYeastMapper();

        this.beerXMLRecipeMapper = new BeerXMLRecipeMapper(importFermentablesMapper,importHopMapper,importYeastMapper,importMashMapper);
    }

    @Test
    public void testRecipeMapper() {

        Recipe recipe = beerXMLRecipeMapper.map(MockImportedRecipe.getImportedRecipe());
        assertThat(recipe).isNotNull();
        assertThat(recipe.getMashes()).isNotEmpty();
        assertThat(recipe.getIngredients()).isNotEmpty();
        assertThat(recipe.getMashes()).hasAtLeastOneElementOfType(Mash.class);
        assertThat(recipe.getIngredients()).hasAtLeastOneElementOfType(Fermentable.class);
        assertThat(recipe.getIngredients()).hasAtLeastOneElementOfType(Hop.class);
        assertThat(recipe.getIngredients()).hasAtLeastOneElementOfType(Yeast.class);
    }

    @Test
    public void testNullMashes() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedMash(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertThat(recipe).isNotNull();
        assertThat(recipe.getMashes()).isEmpty();
    }

    @Test
    public void testNullYeasts() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedYeasts(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertThat(recipe).isNotNull();
        assertThat(recipe.getIngredients()).isNotEmpty();
        assertThat(recipe.getIngredients()
                .stream()
                .filter(i -> i instanceof Yeast)).isEmpty();
    }

    @Test
    public void testNullHops() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedHops(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertThat(recipe).isNotNull();
        assertThat(recipe.getIngredients()).isNotEmpty();
        assertThat(recipe.getIngredients()
                .stream()
                .filter(i -> i instanceof Hop)).isEmpty();
    }

    @Test
    public void testNullFermentables() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedFermentables(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertThat(recipe).isNotNull();
        assertThat(recipe.getIngredients()).isNotEmpty();
        assertThat(recipe.getIngredients()
                         .stream()
                         .filter(i -> i instanceof Fermentable)).isEmpty();
    }

    @Test
    public void testNullStyle() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedStyle(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertThat(recipe).isNotNull();
    }
}