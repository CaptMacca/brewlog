package brews.domain;

import javax.persistence.*;

/**
 * Created by Steve on 27/06/2017.
 */
@Entity
@DiscriminatorValue("fermentable")
public class Fermentable extends Ingredient {

    private Boolean addAfterBoil;

    @Override
    public String getType() { return "Fermentable"; }

    public Boolean getAddAfterBoil() {
        return addAfterBoil;
    }

    public void setAddAfterBoil(Boolean addAfterBoil) {
        this.addAfterBoil = addAfterBoil;
    }
}
