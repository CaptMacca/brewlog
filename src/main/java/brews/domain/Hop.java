package brews.domain;

import javax.persistence.*;

/**
 * Created by Steve on 27/06/2017.
 */
@Entity
@DiscriminatorValue("hop")
public class Hop extends Ingredient {


    private double alpha;
    private String additionTime;
    private String hopUsage;

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public String getAdditionTime() {
        return additionTime;
    }

    public void setAdditionTime(String additionTime) {
        this.additionTime = additionTime;
    }

    public String getHopUsage() {
        return hopUsage;
    }

    public void setHopUsage(String use) {
        this.hopUsage = use;
    }
}
