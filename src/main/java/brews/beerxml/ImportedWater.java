package brews.beerxml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Steve on 25/06/2017.
 */
@XmlRootElement(name = "WATER")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedWater {

    @XmlElement(name = "NAME")
    @Getter @Setter
    private String name;
    @XmlElement(name = "VERSION")
    @Getter @Setter
    private int version;
    @XmlElement(name = "AMOUNT")
    @Getter @Setter
    private double amount;
    @XmlElement(name = "CALCIUM")
    @Getter @Setter
    private int calcium;
    @XmlElement(name = "BICARBONATE")
    @Getter @Setter
    private int bicarbonate;
    @XmlElement(name = "SULFATE")
    @Getter @Setter
    private int sulfate;
    @XmlElement(name = "CHLORIDE")
    @Getter @Setter
    private int chloride;
    @XmlElement(name = "SODIUM")
    @Getter @Setter
    private int sodium;
    @XmlElement(name = "MAGNESIUM")
    @Getter @Setter
    private int magnesium;
    @XmlElement(name = "PH")
    @Getter @Setter
    private int ph;
    @XmlElement(name = "NOTES")
    @Getter @Setter
    private String notes;
    @XmlElement(name = "DISPLAY_AMOUNT")
    @Getter @Setter
    private String displayAmount;

/*
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

    public int getCalcium() {
        return calcium;
    }

    public void setCalcium(int calcium) {
        this.calcium = calcium;
    }

    public int getBicarbonate() {
        return bicarbonate;
    }

    public void setBicarbonate(int bicarbonate) {
        this.bicarbonate = bicarbonate;
    }

    public int getSulfate() {
        return sulfate;
    }

    public void setSulfate(int sulfate) {
        this.sulfate = sulfate;
    }

    public int getChloride() {
        return chloride;
    }

    public void setChloride(int chloride) {
        this.chloride = chloride;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(int magnesium) {
        this.magnesium = magnesium;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
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
*/
}
