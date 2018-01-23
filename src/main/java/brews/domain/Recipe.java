package brews.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Steve on 8/06/2017.
 */
@Entity
public class Recipe {

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
    private String notes;

    @OneToMany(
        mappedBy = "recipe",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<Ingredient> ingredients;

    @OneToMany(
        mappedBy = "recipe",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<Mash> mashes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() { return style; }

    public String getIbu() { return ibu; }

    public void setIbu(String ibu) { this.ibu = ibu; }

    public void setStyle(String style) { this.style = style; }

    public String getEstimatedABV() {
        return estimatedABV;
    }

    public void setEstimatedABV(String estimatedABV) {
        this.estimatedABV = estimatedABV;
    }

    public String getEstimatedColour() {
        return estimatedColour;
    }

    public void setEstimatedColour(String estimatedColour) {
        this.estimatedColour = estimatedColour;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    public String getOriginalGravity() {
        return originalGravity;
    }

    public void setOriginalGravity(String originalGravity) {
        this.originalGravity = originalGravity;
    }

    public String getFinalGravity() {
        return finalGravity;
    }

    public void setFinalGravity(String finalGravity) {
        this.finalGravity = finalGravity;
    }

    public String getBoilTime() {
        return boilTime;
    }

    public void setBoilTime(String boilTime) {
        this.boilTime = boilTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public List<Mash> getMashes() { return mashes; }

    public void setMashes(List<Mash> mashes) { this.mashes = mashes; }

    public String getNotes() { return this.notes; }

    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        return new EqualsBuilder()
                .append(id, recipe.id)
                .append(name, recipe.name)
                .append(type, recipe.type)
                .append(style, recipe.style)
                .append(ibu, recipe.ibu)
                .append(estimatedABV, recipe.estimatedABV)
                .append(estimatedColour, recipe.estimatedColour)
                .append(batchSize, recipe.batchSize)
                .append(originalGravity, recipe.originalGravity)
                .append(finalGravity, recipe.finalGravity)
                .append(boilTime, recipe.boilTime)
                .append(notes, recipe.notes)
                .append(ingredients, recipe.ingredients)
                .append(mashes, recipe.mashes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(type)
                .append(style)
                .append(ibu)
                .append(estimatedABV)
                .append(estimatedColour)
                .append(batchSize)
                .append(originalGravity)
                .append(finalGravity)
                .append(boilTime)
                .append(notes)
                .append(ingredients)
                .append(mashes)
                .toHashCode();
    }
}
