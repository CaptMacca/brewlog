package brews.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(brewDay, that.brewDay)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(brewDay)
                .toHashCode();
    }
}
