package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Steve on 1/07/2017.
 */

@Getter
@Setter
@EqualsAndHashCode()
@ToString(exclude = "brew")
@Entity
public class Measurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_id")
    private Brew brew;

    private Date measurementDate;
    private MeasurementType type;
    private Double value;

}
