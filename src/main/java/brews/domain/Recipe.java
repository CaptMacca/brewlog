package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Domain class that represents a particular recipe
 */

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Entity
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String style;
    private String ibu;
    private String estimatedABV;
    private String estimatedColour;
    private String batchSize;
    private String originalGravity;
    private String finalGravity;
    private String boilTime;
    @Lob
    private String notes;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Ingredient> ingredients;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Mash> mashes;

}
