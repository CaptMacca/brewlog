package brews.util.transformer.beerxml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Deserialised beer xml fermentable
 */
@XmlRootElement(name = "FERMENTABLE")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedFermentable {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private int version;
    @XmlElement(name = "TYPE")
    private String type;
    @XmlElement(name = "AMOUNT")
    private double amount;
    @XmlElement(name = "YIELD")
    private double yield;
    @XmlElement(name = "COLOR")
    private double colour;
    @XmlElement(name = "ADD_AFTER_BOIL")
    private String addAfterBoil;
    @XmlElement(name = "ORIGIN")
    private String origin;
    @XmlElement(name = "SUPPLIER")
    private String supplier;
    @XmlElement(name = "NOTES")
    private String note;
    @XmlElement(name = "COARSE_FINE_DIFF")
    private double coarseFineDiff;
    @XmlElement(name = "MOISTURE")
    private double moisture;
    @XmlElement(name = "DIASTATIC_POWER")
    private double diastaticPower;
    @XmlElement(name = "PROTEIN")
    private double protein;
    @XmlElement(name = "MAX_IN_BATCH")
    private double maxInBatch;
    @XmlElement(name = "RECOMMEND_MASH")
    private String recommendMash;
    @XmlElement(name = "IBU_GAL_PER_LB")
    private int ibuGalPerLB;
    @XmlElement(name = "DISPLAY_AMOUNT")
    private String displayAmount;
    @XmlElement(name = "INVENTORY")
    private String inventory;
    @XmlElement(name = "POTENTIAL")
    private double potential;
    @XmlElement(name = "DISPLAY_COLOR")
    private String displayColour;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public double getColour() {
        return colour;
    }

    public void setColour(double colour) {
        this.colour = colour;
    }

    public String getAddAfterBoil() {
        return addAfterBoil;
    }

    public void setAddAfterBoil(String addAfterBoil) {
        this.addAfterBoil = addAfterBoil;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getCoarseFineDiff() {
        return coarseFineDiff;
    }

    public void setCoarseFineDiff(double coarseFineDiff) {
        this.coarseFineDiff = coarseFineDiff;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public double getDiastaticPower() {
        return diastaticPower;
    }

    public void setDiastaticPower(double diastaticPower) {
        this.diastaticPower = diastaticPower;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getMaxInBatch() {
        return maxInBatch;
    }

    public void setMaxInBatch(double maxInBatch) {
        this.maxInBatch = maxInBatch;
    }

    public String getRecommendMash() {
        return recommendMash;
    }

    public void setRecommendMash(String recommendMash) {
        this.recommendMash = recommendMash;
    }

    public int getIbuGalPerLB() {
        return ibuGalPerLB;
    }

    public void setIbuGalPerLB(int ibuGalPerLB) {
        this.ibuGalPerLB = ibuGalPerLB;
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

    public double getPotential() {
        return potential;
    }

    public void setPotential(double potential) {
        this.potential = potential;
    }

    public String getDisplayColour() {
        return displayColour;
    }

    public void setDisplayColour(String displayColour) {
        this.displayColour = displayColour;
    }
}
