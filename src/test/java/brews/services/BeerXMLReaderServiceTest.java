package brews.services;

import brews.MockXMLRecipe;
import brews.domain.beerxml.ImportedRecipes;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class BeerXMLReaderServiceTest {

    BeerXMLReaderService beerXMLReaderService;

    @Before
    public void setUp() {
        beerXMLReaderService = new BeerXMLReaderServiceImpl();
    }


    @Test
    public void readBeerXML() {

        // Given
        InputStream xmlRecipe = MockXMLRecipe.getMockedXMLRecipe();

        // When
        ImportedRecipes importedRecipes = beerXMLReaderService.readBeerXML(xmlRecipe);

        // Then
        assertNotNull(importedRecipes);
    }
}