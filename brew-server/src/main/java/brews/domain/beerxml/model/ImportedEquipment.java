package brews.domain.beerxml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Deserialised beer xml equipment
 */
@XmlRootElement(name = "EQUIPMENT")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedEquipment {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private int version;
    @XmlElement(name = "BOIL_SIZE")
    private double boilSize;
    @XmlElement(name = "BATCH_SIZE")
    private double batchSize;
    @XmlElement(name = "TUN_VOLUME")
    private double tunVolume;
    @XmlElement(name = "TUN_WEIGHT")
    private double tunWeight;
    @XmlElement(name = "TUN_SPECIFIC_HEAT")
    private double tunSpecificHeat;
    @XmlElement(name = "TOP_UP_WATER")
    private double topUpWater;
    @XmlElement(name = "TRUB_CHILLER_LOSS")
    private double trubChillerLoss;
    @XmlElement(name = "EVAP_RATE")
    private double evaparationRate;
    @XmlElement(name = "BOIL_TIME")
    private int boilTime;
    @XmlElement(name = "CALC_BOIL_VOLUME")
    private String calculatedBoilVolume;
    @XmlElement(name = "LAUTER_DEADSPACE")
    private double lauterDeadspace;
    @XmlElement(name = "TOP_UP_KETTLE")
    private double topUpKettle;
    @XmlElement(name = "HOP_UTILIZATION")
    private double hopUtilization;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "DISPLAY_BOIL_SIZE")
    private String displayBoilSize;
    @XmlElement(name = "DISPLAY_BATCH_SIZE")
    private String displayBatchSize;
    @XmlElement(name = "DISPLAY_TUN_VOLUME")
    private String displayTunVolume;
    @XmlElement(name = "DISPLAY_TUN_WEIGHT")
    private String displayTunWeight;
    @XmlElement(name = "DISPLAY_TOP_UP_WATER")
    private String displayTopUpWater;
    @XmlElement(name = "DISPLAY_TRUB_CHILLER_LOSS")
    private String displayTrubChillerLoss;
    @XmlElement(name = "DISPLAY_LAUTER_DEADSPACE")
    private String displayLauterDeadspace;
    @XmlElement(name = "DISPLAY_TOP_UP_KETTLE")
    private String displayTopUpKettle;

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

    public double getBoilSize() {
        return boilSize;
    }

    public void setBoilSize(double boilSize) {
        this.boilSize = boilSize;
    }

    public double getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(double batchSize) {
        this.batchSize = batchSize;
    }

    public double getTunVolume() {
        return tunVolume;
    }

    public void setTunVolume(double tunVolume) {
        this.tunVolume = tunVolume;
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

    public double getTopUpWater() {
        return topUpWater;
    }

    public void setTopUpWater(double topUpWater) {
        this.topUpWater = topUpWater;
    }

    public double getTrubChillerLoss() {
        return trubChillerLoss;
    }

    public void setTrubChillerLoss(double trubChillerLoss) {
        this.trubChillerLoss = trubChillerLoss;
    }

    public double getEvaparationRate() {
        return evaparationRate;
    }

    public void setEvaparationRate(double evaparationRate) {
        this.evaparationRate = evaparationRate;
    }

    public int getBoilTime() {
        return boilTime;
    }

    public void setBoilTime(int boilTime) {
        this.boilTime = boilTime;
    }

    public String getCalculatedBoilVolume() {
        return calculatedBoilVolume;
    }

    public void setCalculatedBoilVolume(String calculatedBoilVolume) {
        this.calculatedBoilVolume = calculatedBoilVolume;
    }

    public double getLauterDeadspace() {
        return lauterDeadspace;
    }

    public void setLauterDeadspace(double lauterDeadspace) {
        this.lauterDeadspace = lauterDeadspace;
    }

    public double getTopUpKettle() {
        return topUpKettle;
    }

    public void setTopUpKettle(double topUpKettle) {
        this.topUpKettle = topUpKettle;
    }

    public double getHopUtilization() {
        return hopUtilization;
    }

    public void setHopUtilization(double hopUtilization) {
        this.hopUtilization = hopUtilization;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDisplayBoilSize() {
        return displayBoilSize;
    }

    public void setDisplayBoilSize(String displayBoilSize) {
        this.displayBoilSize = displayBoilSize;
    }

    public String getDisplayBatchSize() {
        return displayBatchSize;
    }

    public void setDisplayBatchSize(String displayBatchSize) {
        this.displayBatchSize = displayBatchSize;
    }

    public String getDisplayTunVolume() {
        return displayTunVolume;
    }

    public void setDisplayTunVolume(String displayTunVolume) {
        this.displayTunVolume = displayTunVolume;
    }

    public String getDisplayTunWeight() {
        return displayTunWeight;
    }

    public void setDisplayTunWeight(String displayTunWeight) {
        this.displayTunWeight = displayTunWeight;
    }

    public String getDisplayTopUpWater() {
        return displayTopUpWater;
    }

    public void setDisplayTopUpWater(String displayTopUpWater) {
        this.displayTopUpWater = displayTopUpWater;
    }

    public String getDisplayTrubChillerLoss() {
        return displayTrubChillerLoss;
    }

    public void setDisplayTrubChillerLoss(String displayTrubChillerLoss) {
        this.displayTrubChillerLoss = displayTrubChillerLoss;
    }

    public String getDisplayLauterDeadspace() {
        return displayLauterDeadspace;
    }

    public void setDisplayLauterDeadspace(String displayLauterDeadspace) {
        this.displayLauterDeadspace = displayLauterDeadspace;
    }

    public String getDisplayTopUpKettle() {
        return displayTopUpKettle;
    }

    public void setDisplayTopUpKettle(String displayTopUpKettle) {
        this.displayTopUpKettle = displayTopUpKettle;
    }
}