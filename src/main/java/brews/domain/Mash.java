package brews.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * Created by Steve on 28/06/2017.
 */
@Entity
public class Mash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String stepTemp;
    private Integer stepTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="recipe_id")
    @JsonIgnore
    private Recipe recipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStepTemp() {
        return stepTemp;
    }

    public void setStepTemp(String stepTemp) {
        this.stepTemp = stepTemp;
    }

    public Integer getStepTime() {
        return stepTime;
    }

    public void setStepTime(Integer stepTime) {
        this.stepTime = stepTime;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Mash mash = (Mash) o;

        return new EqualsBuilder()
                .append(id, mash.id)
                .append(name, mash.name)
                .append(stepTemp, mash.stepTemp)
                .append(stepTime, mash.stepTime)
                .append(recipe, mash.recipe)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(stepTemp)
                .append(stepTime)
                .append(recipe)
                .toHashCode();
    }
}
