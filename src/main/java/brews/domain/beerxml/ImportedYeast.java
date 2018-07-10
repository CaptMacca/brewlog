package brews.domain.beerxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Deserialised beer xml yeast
 */
@XmlRootElement(name = "YEAST")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedYeast {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private String version;
    @XmlElement(name = "TYPE")
    private String type;
    @XmlElement(name = "FORM")
    private String form;
    @XmlElement(name = "AMOUNT")
    private double amount;
    @XmlElement(name = "AMOUNT_IS_WEIGHT")
    private String amountIsWeight;
    @XmlElement(name = "LABORATORY")
    private String laboratory;
    @XmlElement(name = "PRODUCT_ID")
    private String productId;
    @XmlElement(name = "MIN_TEMPERATURE")
    private double minTemperature;
    @XmlElement(name = "MAX_TEMPERATURE")
    private double maxTemperature;
    @XmlElement(name = "FLOCCULATION")
    private String flocculation;
    @XmlElement(name = "ATTENUATION")
    private double attenuation;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "BEST_FOR")
    private String bestFor;
    @XmlElement(name = "MAX_REUSE")
    private int maxReuse;
    @XmlElement(name = "TIMES_CULTURED")
    private int timesCultures;
    @XmlElement(name = "ADD_TO_SECONDARY")
    private String addToSecondary;
    @XmlElement(name = "DISPLAY_AMOUNT")
    private String displayAmount;
    @XmlElement(name = "DISP_MIN_TEMP")
    private String displayMinTemp;
    @XmlElement(name = "DISP_MAX_TEMP")
    private String displayMaxTemp;
    @XmlElement(name = "INVENTORY")
    private String inventory;
    @XmlElement(name = "CULTURE_DATE")
    private String cultureDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountIsWeight() {
        return amountIsWeight;
    }

    public void setAmountIsWeight(String amountIsWeight) {
        this.amountIsWeight = amountIsWeight;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getFlocculation() {
        return flocculation;
    }

    public void setFlocculation(String flocculation) {
        this.flocculation = flocculation;
    }

    public double getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBestFor() {
        return bestFor;
    }

    public void setBestFor(String bestFor) {
        this.bestFor = bestFor;
    }

    public int getMaxReuse() {
        return maxReuse;
    }

    public void setMaxReuse(int maxReuse) {
        this.maxReuse = maxReuse;
    }

    public int getTimesCultures() {
        return timesCultures;
    }

    public void setTimesCultures(int timesCultures) {
        this.timesCultures = timesCultures;
    }

    public String getAddToSecondary() {
        return addToSecondary;
    }

    public void setAddToSecondary(String addToSecondary) {
        this.addToSecondary = addToSecondary;
    }

    public String getDisplayAmount() {
        return displayAmount;
    }

    public void setDisplayAmount(String displayAmount) {
        this.displayAmount = displayAmount;
    }

    public String getDisplayMinTemp() {
        return displayMinTemp;
    }

    public void setDisplayMinTemp(String displayMinTemp) {
        this.displayMinTemp = displayMinTemp;
    }

    public String getDisplayMaxTemp() {
        return displayMaxTemp;
    }

    public void setDisplayMaxTemp(String displayMaxTemp) {
        this.displayMaxTemp = displayMaxTemp;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getCultureDate() {
        return cultureDate;
    }

    public void setCultureDate(String cultureDate) {
        this.cultureDate = cultureDate;
    }
}
