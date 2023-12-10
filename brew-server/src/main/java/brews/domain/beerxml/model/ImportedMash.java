package brews.domain.beerxml.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

/**
 * Deserialised beer xml mash
 */
@XmlRootElement(name = "MASH")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedMash {

    @XmlElement(name="NAME")
    private String name;
    @XmlElement(name = "GRAIN_TEMP")
    private double grainTemp;
    @XmlElement(name = "TUN_TEMP")
    private double tunTemp;
    @XmlElement(name = "SPARGE_TEMP")
    private double spargeTemp;
    @XmlElement(name = "PH")
    private double ph;
    @XmlElement(name = "TUN_WEIGHT")
    private double tunWeight;
    @XmlElement(name = "TUN_SPECIFIC_HEAT")
    private double tunSpecificHeat;
    @XmlElement(name = "EQUIP_ADJUST")
    private String equipmentAdjust;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "DISPLAY_GRAIN_TEMP")
    private String displayGrainTemp;
    @XmlElement(name = "DISPLAY_TUN_TEMP")
    private int displayTunTemp;
    @XmlElement(name = "DISPLAY_SPARGE_TEMP")
    private String displaySpargeTemp;
    @XmlElement(name = "DISPLAY_TUN_WEIGHT")
    private String displayTunWeight;
    @XmlElementWrapper(name = "MASH_STEPS")
    @XmlElement(name = "MASH_STEP")
    private List<ImportedMashStep> importedMashSteps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    }
}
