package brews.domain.beerxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Deserialised beer xml water
 */
@XmlRootElement(name = "WATER")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedWater {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private int version;
    @XmlElement(name = "AMOUNT")
    private double amount;
    @XmlElement(name = "CALCIUM")
    private double calcium;
    @XmlElement(name = "BICARBONATE")
    private double bicarbonate;
    @XmlElement(name = "SULFATE")
    private double sulfate;
    @XmlElement(name = "CHLORIDE")
    private double chloride;
    @XmlElement(name = "SODIUM")
    private double sodium;
    @XmlElement(name = "MAGNESIUM")
    private double magnesium;
    @XmlElement(name = "PH")
    private double ph;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "DISPLAY_AMOUNT")
    private String displayAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getBicarbonate() {
        return bicarbonate;
    }

    public void setBicarbonate(double bicarbonate) {
        this.bicarbonate = bicarbonate;
    }

    public double getSulfate() {
        return sulfate;
    }

    public void setSulfate(double sulfate) {
        this.sulfate = sulfate;
    }

    public double getChloride() {
        return chloride;
    }

    public void setChloride(double chloride) {
        this.chloride = chloride;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(double magnesium) {
        this.magnesium = magnesium;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDisplayAmount() {
        return displayAmount;
    }

    public void setDisplayAmount(String displayAmount) {
        this.displayAmount = displayAmount;
    }
}
