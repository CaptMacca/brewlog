package brews.beerxml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Steve on 25/06/2017.
 */
@XmlRootElement(name = "MASH")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedMash {

    @XmlElement(name = "GRAIN_TEMP")
    @Getter @Setter
    private double grainTemp;
    @XmlElement(name = "TUN_TEMP")
    @Getter @Setter
    private double tunTemp;
    @XmlElement(name = "SPARGE_TEMP")
    @Getter @Setter
    private double spargeTemp;
    @XmlElement(name = "PH")
    @Getter @Setter
    private double ph;
    @XmlElement(name = "TUN_WEIGHT")
    @Getter @Setter
    private double tunWeight;
    @XmlElement(name = "TUN_SPECIFIC_HEAT")
    @Getter @Setter
    private double tunSpecificHeat;
    @XmlElement(name = "EQUIP_ADJUST")
    @Getter @Setter
    private String equipmentAdjust;
    @XmlElement(name = "NOTES")
    @Getter @Setter
    private String notes;
    @XmlElement(name = "DISPLAY_GRAIN_TEMP")
    @Getter @Setter
    private String displayGrainTemp;
    @XmlElement(name = "DISPLAY_TUN_TEMP")
    @Getter @Setter
    private int displayTunTemp;
    @XmlElement(name = "DISPLAY_SPARGE_TEMP")
    @Getter @Setter
    private String displaySpargeTemp;
    @XmlElement(name = "DISPLAY_TUN_WEIGHT")
    @Getter @Setter
    private String displayTunWeight;
    @XmlElementWrapper(name = "MASH_STEPS")
    @XmlElement(name = "MASH_STEP")
    @Getter @Setter
    private List<ImportedMashStep> importedMashSteps;

    /*
    public double getGrainTemp() {
        return grainTemp;
    }

    public void setGrainTemp(double grainTemp) {
        this.grainTemp = grainTemp;
    }

    public double getTunTemp() {
        return tunTemp;
    }

    public void setTunTemp(double tunTemp) {
        this.tunTemp = tunTemp;
    }

    public double getSpargeTemp() {
        return spargeTemp;
    }

    public void setSpargeTemp(double spargeTemp) {
        this.spargeTemp = spargeTemp;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getTunWeight() {
        return tunWeight;
    }

    public void setTunWeight(double tunWeight) {
        this.tunWeight = tunWeight;
    }

    public double getTunSpecificHeat() {
        return tunSpecificHeat;
    }

    public void setTunSpecificHeat(double tunSpecificHeat) {
        this.tunSpecificHeat = tunSpecificHeat;
    }

    public String getEquipmentAdjust() {
        return equipmentAdjust;
    }

    public void setEquipmentAdjust(String equipmentAdjust) {
        this.equipmentAdjust = equipmentAdjust;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDisplayGrainTemp() {
        return displayGrainTemp;
    }

    public void setDisplayGrainTemp(String displayGrainTemp) {
        this.displayGrainTemp = displayGrainTemp;
    }

    public int getDisplayTunTemp() {
        return displayTunTemp;
    }

    public void setDisplayTunTemp(int displayTunTemp) {
        this.displayTunTemp = displayTunTemp;
    }

    public String getDisplaySpargeTemp() {
        return displaySpargeTemp;
    }

    public void setDisplaySpargeTemp(String displaySpargeTemp) {
        this.displaySpargeTemp = displaySpargeTemp;
    }

    public String getDisplayTunWeight() {
        return displayTunWeight;
    }

    public void setDisplayTunWeight(String displayTunWeight) {
        this.displayTunWeight = displayTunWeight;
    }

    public List<ImportedMashStep> getImportedMashSteps() {
        return importedMashSteps;
    }

    public void setImportedMashSteps(List<ImportedMashStep> importedMashSteps) {
        this.importedMashSteps = importedMashSteps;
    }*/
}
