package brews.domain.beerxml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Deserialised beer xml hop
 */
@XmlRootElement(name = "HOP")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedHop {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private int version;
    @XmlElement(name = "ORIGIN")
    private String origin;
    @XmlElement(name = "ALPHA")
    private double alpha;
    @XmlElement(name = "AMOUNT")
    private double amount;
    @XmlElement(name = "USE")
    private String use;
    @XmlElement(name = "TIME")
    private double time;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "TYPE")
    private String type;
    @XmlElement(name = "FORM")
    private String form;
    @XmlElement(name = "BETA")
    private double beta;
    @XmlElement(name = "HSI")
    private double hsi;
    @XmlElement(name = "DISPLAY_AMOUNT")
    private String displayAmount;
    @XmlElement(name = "INVENTORY")
    private String inventory;
    @XmlElement(name = "DISPLAY_TIME")
    private String displayTime;

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

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
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

    public double getHsi() {
        return hsi;
    }

    public void setHsi(double hsi) {
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

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }
}
