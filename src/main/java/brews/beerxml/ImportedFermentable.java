package brews.beerxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Steve on 25/06/2017.
 */
@XmlRootElement(name="FERMENTABLE")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedFermentable {

    @XmlElement(name="NAME")
    private String name;
    @XmlElement(name="VERSION")
    private int version;
    @XmlElement(name="TYPE")
    private String type;
    @XmlElement(name="AMOUNT")
    private double amount;
    @XmlElement(name="YIELD")
    private int yield;
    @XmlElement(name="COLOR")
    private double colour;
    @XmlElement(name="ADD_AFTER_BOIL")
    private String addAfterBoil;
    @XmlElement(name="ORIGIN")
    private String origin;
    @XmlElement(name="SUPPLIER")
    private String supplier;
    @XmlElement(name="NOTES")
    private String note;
    @XmlElement(name="COARSE_FINE_DIFF")
    private double coarseFineDiff;
    @XmlElement(name="MOISTURE")
    private int moisture;
    @XmlElement(name="DIASTATIC_POWER")
    private int diastaticPower;
    @XmlElement(name="PROTEIN")
    private double protein;
    @XmlElement(name="MAX_IN_BATCH")
    private int maxInBatch;
    @XmlElement(name="RECOMMEND_MASH")
    private String recommendMash;
    @XmlElement(name="IBU_GAL_PER_LB")
    private int ibuGalPerLB;
    @XmlElement(name="DISPLAY_AMOUNT")
    private String displayAmount;
    @XmlElement(name="INVENTORY")
    private String inventory;
    @XmlElement(name="POTENTIAL")
    private double potential;
    @XmlElement(name="DISPLAY_COLOR")
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

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
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

    public int getMoisture() {
        return moisture;
    }

    public void setMoisture(int moisture) {
        this.moisture = moisture;
    }

    public int getDiastaticPower() {
        return diastaticPower;
    }

    public void setDiastaticPower(int diastaticPower) {
        this.diastaticPower = diastaticPower;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public int getMaxInBatch() {
        return maxInBatch;
    }

    public void setMaxInBatch(int maxInBatch) {
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
