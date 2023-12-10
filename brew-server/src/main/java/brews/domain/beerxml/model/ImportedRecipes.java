package brews.domain.beerxml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Root of a beerxml export file, basically the file will contain a list of recipes.
 */
@XmlRootElement(name = "RECIPES")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedRecipes {

    @XmlElement(name = "RECIPE")
    private List<ImportedRecipe> importedRecipes;

    public List<ImportedRecipe> getImportedRecipes() {
        return importedRecipes;
    }

    public void setImportedRecipes(List<ImportedRecipe> importedRecipes) {
        this.importedRecipes = importedRecipes;
    }
}
