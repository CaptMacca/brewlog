package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Steve on 1/07/2017.
 */

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
@Entity
public class Measurement implements Serializable {

    public static final String ORIGINAL_GRAVITY_MEASUREMENT_TYPE = "OG";
    public static final String FINAL_GRAVITY_MEASUREMENT_TYPE = "FG";
    public static final String PRE_BOIL_GRAVITY_MEASUREMENT_TYPE = "PRE_OG";
    public static final String FINAL_VOL_MEASUREMENT_TYPE = "VOL";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "brew_id")
    private Brew brew;

    private String type;
    private Double value;

}
