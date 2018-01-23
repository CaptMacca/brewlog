package brews.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public String getType() { return "Hop"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Hop hop = (Hop) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(alpha, hop.alpha)
                .append(additionTime, hop.additionTime)
                .append(hopUsage, hop.hopUsage)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(alpha)
                .append(additionTime)
                .append(hopUsage)
                .toHashCode();
    }
}
