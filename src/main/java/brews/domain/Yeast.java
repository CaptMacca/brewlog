package brews.domain;

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
}
