package brews.mapper.beerxml;

import brews.MockImportedRecipe;
import brews.domain.Recipe;
import brews.domain.beerxml.ImportedRecipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BeerXMLRecipeMapperTest {

    private ImportFermentablesMapper importFermentablesMapper;
    private ImportHopMapper importHopMapper;
    private ImportMashMapper importMashMapper;
    private ImportYeastMapper importYeastMapper;
    private BeerXMLRecipeMapper beerXMLRecipeMapper;

    @Before
    public void setUp() throws Exception {

        this.importFermentablesMapper = new ImportFermentablesMapper();
        this.importHopMapper = new ImportHopMapper();
        this.importMashMapper = new ImportMashMapper();
        this.importYeastMapper = new ImportYeastMapper();

        this.beerXMLRecipeMapper = new BeerXMLRecipeMapper(importFermentablesMapper,importHopMapper,importYeastMapper,importMashMapper);
    }

    @Test
    public void testRecipeMapper() {

        Recipe recipe = beerXMLRecipeMapper.map(MockImportedRecipe.getImportedRecipe());
        assertNotNull(recipe);
    }

    @Test
    public void testNullMashes() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedMash(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertNotNull(recipe);
    }

    @Test
    public void testNullYeasts() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedYeasts(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertNotNull(recipe);
    }

    @Test
    public void testNullFermentables() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedFermentables(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertNotNull(recipe);
    }

    @Test
    public void testNullStyle() {
        ImportedRecipe mockImportedRecipe = MockImportedRecipe.getImportedRecipe();
        mockImportedRecipe.setImportedStyle(null);

        Recipe recipe = beerXMLRecipeMapper.map(mockImportedRecipe);
        assertNotNull(recipe);
    }
}