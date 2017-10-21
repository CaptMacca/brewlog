package brews.domain;

import javax.persistence.*;

/**
 * Created by Steve on 1/07/2017.
 */
@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="brew_id")
    private BrewDay brewDay;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public BrewDay getBrewDay() { return brewDay; }

    public void setBrewDay(BrewDay brewDay) { this.brewDay = brewDay; }
}
