package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Created by Steve on 27/06/2017.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@DiscriminatorValue("fermentable")
public class Fermentable extends Ingredient {

    @EqualsAndHashCode.Include
    @ToString.Include
    private Boolean addAfterBoil;

    public Fermentable() {
        super();
        this.setType(INGREDIENT_TYPE_FERMENTABLE);
    }

}
