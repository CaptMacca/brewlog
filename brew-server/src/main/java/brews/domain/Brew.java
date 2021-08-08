package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Domain class representing a Brew of a particular recipe.
 */

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Entity
@Table(name="brew", schema="brews")
public class Brew implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime brewDate;
    private Integer score;
    private String spargeVol;
    private String totalWater;
    private String fermenterVol;
    private String estimatedOriginalGravity;
    private String measuredOriginalGravity;
    private String estimatedPreboilGravity;
    private String measuredPreboilGravity;
    private String estimatedFinalGravity;
    private String measuredFinalGravity;
    private String estimatedFermentVolume;
    private String measuredFermentVolume;
    private String estimatedBottleVolume;
    private String measuredBottleVolume;

    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(columnDefinition = "TEXT")
    private String tastingNotes;

    @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(
      fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Recipe recipe;

    @OneToMany(
      mappedBy = "brew",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Measurement> measurements;

    @Version
    private Long versionId;
}
