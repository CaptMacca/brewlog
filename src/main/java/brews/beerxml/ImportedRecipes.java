package brews.beerxml;

import lombok.Getter;
import lombok.Setter;

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
    @Getter @Setter
    private List<ImportedRecipe> importedRecipes;


}
