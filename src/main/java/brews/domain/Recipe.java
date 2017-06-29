package brews.domain;

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
    private String estimatedABV;
    private String estimatedColour;
    private String batchSize;
    private String originalGravity;
    private String finalGravity;
    private String boilTime;

    @OneToMany
    private List<Ingredient> ingredients;

    @OneToMany
    private List<MashStep> mashSteps;

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

    public List<MashStep> getMashSteps() { return mashSteps; }

    public void setMashSteps(List<MashStep> mashSteps) { this.mashSteps = mashSteps; }

}
