package brews.services;

import brews.domain.beerxml.ImportedRecipes;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface BeerXMLReaderService {

    ImportedRecipes readBeerXML(InputStream contents);
}
