package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Domain class representing a Brew of a particular recipe.
 */

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Entity
public class Brew implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date brewDate;
    private String brewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToMany(
            mappedBy = "brew",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Measurement> measurements;

    public Brew addMeasurement(Measurement measurement) {
        measurement.setBrew(this);
        this.measurements.add(measurement);
        return this;
    }

}
