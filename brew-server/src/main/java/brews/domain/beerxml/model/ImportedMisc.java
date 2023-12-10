package brews.domain.beerxml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Deserialised beer xml misc
 */
@XmlRootElement(name = "MISC")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedMisc {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private int version;
    @XmlElement(name = "TYPE")
    private String type;
    @XmlElement(name = "USE")
    private String use;
    @XmlElement(name = "AMOUNT")
    private double amount;
    @XmlElement(name = "TIME")
    private int time;
    @XmlElement(name = "AMOUNT_IS_WEIGHT")
    private String amountIsWeight;
    @XmlElement(name = "USE_FOR")
    private String usedFor;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "DISPLAY_AMOUNT")
    private String displayAmount;
    @XmlElement(name = "INVENTORY")
    private String inventory;
    @XmlElement(name = "DISPLAY_TIME")
    private String displayTime;
    @XmlElement(name = "BATCH_SIZE")
    private String batchSize;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getAmountIsWeight() {
        return amountIsWeight;
    }

    public void setAmountIsWeight(String amountIsWeight) {
        this.amountIsWeight = amountIsWeight;
    }

    public String getUsedFor() {
        return usedFor;
    }

    public void setUsedFor(String usedFor) {
        this.usedFor = usedFor;
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

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }
}
