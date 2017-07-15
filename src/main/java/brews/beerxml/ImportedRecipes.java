package brews.beerxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Steve on 24/06/2017.
 */
@XmlRootElement(name="RECIPES")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedRecipes {

    @XmlElement(name="RECIPE")
    private List<ImportedRecipe> importedRecipes;

    public List<ImportedRecipe> getImportedRecipes() {
        return importedRecipes;
    }

    public void setImportedRecipes(List<ImportedRecipe> importedRecipes) {
        this.importedRecipes = importedRecipes;
    }
}
