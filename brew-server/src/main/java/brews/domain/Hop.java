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
@DiscriminatorValue("hop")
public class Hop extends Ingredient {

    @EqualsAndHashCode.Include
    @ToString.Include
    private Double alpha;
    @EqualsAndHashCode.Include
    @ToString.Include
    private Double additionTime;
    @EqualsAndHashCode.Include
    @ToString.Include
    private String hopUsage;

    public Hop() {
        super();
        this.setType(INGREDIENT_TYPE_HOP);
    }

}
