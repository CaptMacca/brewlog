package brews.domain.beerxml;

import brews.domain.Recipe;
import brews.domain.beerxml.model.ImportedRecipe;
import brews.services.exceptions.BeerXMLParserException;
import brews.domain.beerxml.mapping.BeerXMLRecipeMapper;
import brews.domain.beerxml.model.ImportedRecipes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles the transformation of beerxml v1 format into pojos
 */
@Slf4j
@Service
public class BeerXMLTransformer {

    private final BeerXMLRecipeMapper beerXMLRecipeMapper;

    public BeerXMLTransformer(BeerXMLRecipeMapper beerXMLRecipeMapper)  {
        this.beerXMLRecipeMapper = beerXMLRecipeMapper;
    }

    public List<Recipe> convertBeerXmlRecipes(InputStream contents) {

        log.debug("Importing beerxml file contents");
        ImportedRecipes importedRecipes = readBeerXML(contents);

        new ArrayList<>();

        log.debug("Transforming beerxml into recipes");
        List<ImportedRecipe> candidateRecipes = importedRecipes.getImportedRecipes();
        List<Recipe> recipes = candidateRecipes.stream().map(beerXMLRecipeMapper::map).collect(Collectors.toList());
        return recipes;
    }


    private ImportedRecipes readBeerXML(InputStream contents) {

        ImportedRecipes importedRecipes = null;

        try {
            log.debug("Unmarshalling the beerxml file");
            JAXBContext jaxbContext = JAXBContext.newInstance(ImportedRecipes.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            importedRecipes = (ImportedRecipes) jaxbUnmarshaller.unmarshal(contents);

            log.debug("Beerxml has been unmarshalled");
            if ((importedRecipes != null) && (importedRecipes.getImportedRecipes() != null)) {
                log.debug("Found " + importedRecipes.getImportedRecipes().size() + " recipes in the beer xml file.");
            }
        } catch (JAXBException e) {
            log.error("Exception unmarshalling the beerxml file");
            throw new BeerXMLParserException("Cannot transform beerxml file", e);
        }

        return importedRecipes;
    }

}
