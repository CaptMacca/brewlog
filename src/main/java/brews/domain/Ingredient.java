package brews.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Ingredient implements Serializable {

    public static final String INGREDIENT_TYPE_FERMENTABLE = "Fermentable";
    public static final String INGREDIENT_TYPE_YEAST = "Yeast";
    public static final String INGREDIENT_TYPE_HOP = "Hop";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double amount;

    private transient String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    private Recipe recipe;

}
