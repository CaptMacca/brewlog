package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Steve on 27/06/2017.
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@DiscriminatorValue("hop")
public class Hop extends Ingredient {

    private Double alpha;
    private Double additionTime;
    private String hopUsage;

    public Hop() {
        super();
        this.setType(INGREDIENT_TYPE_HOP);
    }

}
