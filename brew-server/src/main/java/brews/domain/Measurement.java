package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Created by Steve on 1/07/2017.
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Measurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Brew brew;

    private OffsetDateTime measurementDate;
    private Double value;

    @Version
    private Long versionId;

}
