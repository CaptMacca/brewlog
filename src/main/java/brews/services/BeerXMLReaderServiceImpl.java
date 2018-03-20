package brews.services;

import brews.domain.beerxml.ImportedRecipes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

@Service
@Slf4j
public class BeerXMLReaderServiceImpl implements BeerXMLReaderService {

    @Override
    public ImportedRecipes readBeerXML(InputStream contents) {

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
            log.error("Exception unmarshalling beerxml");
        }

        return importedRecipes;
    }
}
