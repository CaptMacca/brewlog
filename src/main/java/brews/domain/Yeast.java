package brews.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Steve on 27/06/2017.
 */
@Entity
@DiscriminatorValue("yeast")
public class Yeast extends Ingredient {

    private String productId;
    private String laboratory;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    @Override
    public String getType() { return "Yeast"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Yeast yeast = (Yeast) o;

        return new EqualsBuilder()
                .append(productId, yeast.productId)
                .append(laboratory, yeast.laboratory)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(productId)
                .append(laboratory)
                .toHashCode();
    }
}
