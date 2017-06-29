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
@XmlRootElement(name = "MISC")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedMisc {

    @XmlElement(name = "NAME")
    @Getter @Setter
    private String name;
    @XmlElement(name = "VERSION")
    @Getter @Setter
    private int version;
    @XmlElement(name = "TYPE")
    @Getter @Setter
    private String type;
    @XmlElement(name = "USE")
    @Getter @Setter
    private String use;
    @XmlElement(name = "AMOUNT")
    @Getter @Setter
    private double amount;
    @XmlElement(name = "TIME")
    @Getter @Setter
    private int time;
    @XmlElement(name = "AMOUNT_IS_WEIGHT")
    @Getter @Setter
    private String amountIsWeight;
    @XmlElement(name = "USE_FOR")
    @Getter @Setter
    private String usedFor;
    @XmlElement(name = "NOTES")
    @Getter @Setter
    private String notes;
    @XmlElement(name = "DISPLAY_AMOUNT")
    @Getter @Setter
    private String displayAmount;
    @XmlElement(name = "INVENTORY")
    @Getter @Setter
    private String inventory;
    @XmlElement(name = "DISPLAY_TIME")
    @Getter @Setter
    private String displayTime;
    @XmlElement(name = "BATCH_SIZE")
    @Getter @Setter
    private String batchSize;

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
    }*/
}
