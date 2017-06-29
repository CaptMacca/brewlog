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
@XmlRootElement(name="EQUIPMENT")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedEquipment {

    @XmlElement(name = "NAME")
    @Getter @Setter
    private String name;
    @XmlElement(name = "VERSION")
    @Getter @Setter
    private int version;
    @XmlElement(name = "BOIL_SIZE")
    @Getter @Setter
    private double boilSize;
    @XmlElement(name = "BATCH_SIZE")
    @Getter @Setter
    private double batchSize;
    @XmlElement(name = "TUN_VOLUME")
    @Getter @Setter
    private double tunVolumne;
    @XmlElement(name = "TUN_WEIGHT")
    @Getter @Setter
    private double tunWeight;
    @XmlElement(name = "TUN_SPECIFIC_HEAT")
    @Getter @Setter
    private double tunSpecificHeat;
    @XmlElement(name = "TOP_UP_WATER")
    @Getter @Setter
    private int topUpWater;
    @XmlElement(name = "TRUB_CHILLER_LOSS")
    @Getter @Setter
    private double trubChillerLoss;
    @XmlElement(name = "EVAP_RATE")
    @Getter @Setter
    private int evaparationRate;
    @XmlElement(name = "BOIL_TIME")
    @Getter @Setter
    private int boilTime;
    @XmlElement(name = "CALC_BOIL_VOLUME")
    @Getter @Setter
    private String calculatedBoilVolume;
    @XmlElement(name = "LAUTER_DEADSPACE")
    @Getter @Setter
    private double lauterDeadspace;
    @XmlElement(name = "TOP_UP_KETTLE")
    @Getter @Setter
    private int topUpKettle;
    @XmlElement(name = "HOP_UTILIZATION")
    @Getter @Setter
    private int hopUtilization;
    @XmlElement(name = "NOTES")
    @Getter @Setter
    private String notes;
    @XmlElement(name = "DISPLAY_BOIL_SIZE")
    @Getter @Setter
    private String displayBoilSize;
    @XmlElement(name = "DISPLAY_BATCH_SIZE")
    @Getter @Setter
    private String displayBatchSize;
    @XmlElement(name = "DISPLAY_TUN_VOLUME")
    @Getter @Setter
    private String displayTunVolume;
    @XmlElement(name = "DISPLAY_TUN_WEIGHT")
    @Getter @Setter
    private String displayTunWeight;
    @XmlElement(name = "DISPLAY_TOP_UP_WATER")
    @Getter @Setter
    private String displayTopUpWater;
    @XmlElement(name = "DISPLAY_TRUB_CHILLER_LOSS")
    @Getter @Setter
    private String displayTrubChillerLoss;
    @XmlElement(name = "DISPLAY_LAUTER_DEADSPACE")
    @Getter @Setter
    private String displayLauterDeadspace;
    @XmlElement(name = "DISPLAY_TOP_UP_KETTLE")
    @Getter @Setter
    private String displayTopUpKettle;

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

    public double getTunVolumne() {
        return tunVolumne;
    }

    public void setTunVolumne(double tunVolumne) {
        this.tunVolumne = tunVolumne;
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

    public int getTopUpWater() {
        return topUpWater;
    }

    public void setTopUpWater(int topUpWater) {
        this.topUpWater = topUpWater;
    }

    public double getTrubChillerLoss() {
        return trubChillerLoss;
    }

    public void setTrubChillerLoss(double trubChillerLoss) {
        this.trubChillerLoss = trubChillerLoss;
    }

    public int getEvaparationRate() {
        return evaparationRate;
    }

    public void setEvaparationRate(int evaparationRate) {
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

    public int getTopUpKettle() {
        return topUpKettle;
    }

    public void setTopUpKettle(int topUpKettle) {
        this.topUpKettle = topUpKettle;
    }

    public int getHopUtilization() {
        return hopUtilization;
    }

    public void setHopUtilization(int hopUtilization) {
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
    }*/
}