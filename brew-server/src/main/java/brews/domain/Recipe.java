package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Domain class that represents a particular recipe
 */

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Entity
@Table(name="recipe", schema="brews")
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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Ingredient> ingredients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(
      mappedBy = "recipe",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Mash> mashes;

    @OneToMany(
      mappedBy = "recipe",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Brew> brews;

    @Version
    private Long versionId;

}
