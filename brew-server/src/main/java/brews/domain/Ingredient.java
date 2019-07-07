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
@EqualsAndHashCode
@ToString
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name="ingredient", schema="brews")
public class Ingredient implements Serializable {

    public static final String INGREDIENT_TYPE_FERMENTABLE = "Fermentable";
    public static final String INGREDIENT_TYPE_YEAST = "Yeast";
    public static final String INGREDIENT_TYPE_HOP = "Hop";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String name;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Double amount;

    private transient String type;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Recipe recipe;

    @Version
    private Long versionId;
}
