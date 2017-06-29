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
@XmlRootElement(name="FERMENTABLE")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedFermentable {

    @XmlElement(name="NAME")
    @Getter @Setter
    private String name;
    @XmlElement(name="VERSION")
    @Getter @Setter
    private int version;
    @XmlElement(name="TYPE")
    @Getter @Setter
    private String type;
    @XmlElement(name="AMOUNT")
    @Getter @Setter
    private double amount;
    @XmlElement(name="YIELD")
    @Getter @Setter
    private int yield;
    @XmlElement(name="COLOR")
    @Getter @Setter
    private double colour;
    @XmlElement(name="ADD_AFTER_BOIL")
    @Getter @Setter
    private String addAfterBoil;
    @XmlElement(name="ORIGIN")
    @Getter @Setter
    private String origin;
    @XmlElement(name="SUPPLIER")
    @Getter @Setter
    private String supplier;
    @XmlElement(name="NOTES")
    @Getter @Setter
    private String note;
    @XmlElement(name="COARSE_FINE_DIFF")
    @Getter @Setter
    private double coarseFineDiff;
    @XmlElement(name="MOISTURE")
    @Getter @Setter
    private int moisture;
    @XmlElement(name="DIASTATIC_POWER")
    @Getter @Setter
    private int diastaticPower;
    @XmlElement(name="PROTEIN")
    @Getter @Setter
    private double protein;
    @XmlElement(name="MAX_IN_BATCH")
    @Getter @Setter
    private int maxInBatch;
    @XmlElement(name="RECOMMEND_MASH")
    @Getter @Setter
    private String recommendMash;
    @XmlElement(name="IBU_GAL_PER_LB")
    @Getter @Setter
    private int ibuGalPerLB;
    @XmlElement(name="DISPLAY_AMOUNT")
    @Getter @Setter
    private String displayAmount;
    @XmlElement(name="INVENTORY")
    @Getter @Setter
    private String inventory;
    @XmlElement(name="POTENTIAL")
    @Getter @Setter
    private double potential;
    @XmlElement(name="DISPLAY_COLOR")
    @Getter @Setter
    private String displayColour;

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
    }*/
}
