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
@XmlRootElement(name="HOP")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedHop {

    @XmlElement(name="NAME")
    @Getter @Setter
    private String name;
    @XmlElement(name="VERSION")
    @Getter @Setter
    private int version;
    @XmlElement(name="ORIGIN")
    @Getter @Setter
    private String origin;
    @XmlElement(name="ALPHA")
    @Getter @Setter
    private double alpha;
    @XmlElement(name="AMOUNT")
    @Getter @Setter
    private double amount;
    @XmlElement(name="USE")
    @Getter @Setter
    private String use;
    @XmlElement(name="TIME")
    @Getter @Setter
    private int time;
    @XmlElement(name="NOTES")
    @Getter @Setter
    private String notes;
    @XmlElement(name="TYPE")
    @Getter @Setter
    private String type;
    @XmlElement(name="FORM")
    @Getter @Setter
    private String form;
    @XmlElement(name="BETA")
    @Getter @Setter
    private double beta;
    @XmlElement(name="HSI")
    @Getter @Setter
    private int hsi;
    @XmlElement(name="DISPLAY_AMOUNT")
    @Getter @Setter
    private String displayAmount;
    @XmlElement(name="INVENTORY")
    @Getter @Setter
    private String inventory;
    @XmlElement(name="DISPLAY_TIME")
    @Getter @Setter
    private String displayTime;

    /*public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public int getHsi() {
        return hsi;
    }

    public void setHsi(int hsi) {
        this.hsi = hsi;
    }

    public String getDisplayAmount() {
        return displayAmount;
    }

    public void setDisplayAmount(String displayAmount) {
        this.displayAmount = displayAmount;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }*/
}
