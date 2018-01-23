package brews.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 11/06/2017.
 */
@Entity
public class BrewDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date brewDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @OneToMany(
            mappedBy = "brewDay",
            cascade = CascadeType.ALL,
            fetch=FetchType.LAZY
    )
    private List<Measurement> measurements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBrewDate() {
        return brewDate;
    }

    public void setBrewDate(Date brewDate) {
        this.brewDate = brewDate;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Measurement> getMeasurements() { return measurements; }

    public void setMeasurements(List<Measurement> measurements) { this.measurements = measurements; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BrewDay brewDay = (BrewDay) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, brewDay.id)
                .append(brewDate, brewDay.brewDate)
                .append(recipe, brewDay.recipe)
                .append(measurements, brewDay.measurements)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(brewDate)
                .append(recipe)
                .append(measurements)
                .toHashCode();
    }
}
