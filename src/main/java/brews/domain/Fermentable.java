package brews.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Fermentable that = (Fermentable) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(addAfterBoil, that.addAfterBoil)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(addAfterBoil)
                .toHashCode();
    }
}
