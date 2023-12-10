package brews.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Domain class that represents a particular recipe
 */

@Data
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
    @Column(columnDefinition="TEXT")
    private String notes;
    private Short rating;

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
