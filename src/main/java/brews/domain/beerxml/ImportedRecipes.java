package brews.domain.beerxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
