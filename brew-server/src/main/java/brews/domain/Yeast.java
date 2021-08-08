package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Domain class representing a particular yeast strain for a recipe.
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@DiscriminatorValue("yeast")
public final class Yeast extends Ingredient {

    private String productId;
    private String laboratory;

    public Yeast() {
        super();
        this.setType(INGREDIENT_TYPE_YEAST);
    }

}
