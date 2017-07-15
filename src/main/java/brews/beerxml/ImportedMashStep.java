package brews.beerxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Steve on 25/06/2017.
 */
@XmlRootElement(name = "MASH_STEP")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedMashStep {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private String version;
    @XmlElement(name = "TYPE")
    private String type;
    @XmlElement(name = "INFUSE_AMOUNT")
    private double infuseAmount;
    @XmlElement(name = "STEP_TIME")
    private int stepTime;
    @XmlElement(name = "STEP_TEMP")
    private int stepTemp;
    @XmlElement(name = "RAMP_TIME")
    private int rampTime;
    @XmlElement(name = "END_TEMP")
    private int endTemp;
    @XmlElement(name = "DESCRIPTION")
    private String description;
    @XmlElement(name = "WATER_GRAIN_RATIO")
    private double waterGrainRatio;
    @XmlElement(name = "DECOCTION_AMT")
    private String decoctionAmount;
    @XmlElement(name = "INFUSE_TEMP")
    private String infuseTemp;
    @XmlElement(name = "DISPLAY_STEP_TEMP")
    private String displayStepTemp;
    @XmlElement(name = "DISPLAY_INFUSE_AMT")
    private String displayInfuseAmount;

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

    public double getInfuseAmount() {
        return infuseAmount;
    }

    public void setInfuseAmount(double infuseAmount) {
        this.infuseAmount = infuseAmount;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public int getStepTemp() {
        return stepTemp;
    }

    public void setStepTemp(int stepTemp) {
        this.stepTemp = stepTemp;
    }

    public int getRampTime() {
        return rampTime;
    }

    public void setRampTime(int rampTime) {
        this.rampTime = rampTime;
    }

    public int getEndTemp() {
        return endTemp;
    }

    public void setEndTemp(int endTemp) {
        this.endTemp = endTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWaterGrainRatio() {
        return waterGrainRatio;
    }

    public void setWaterGrainRatio(double waterGrainRatio) {
        this.waterGrainRatio = waterGrainRatio;
    }

    public String getDecoctionAmount() {
        return decoctionAmount;
    }

    public void setDecoctionAmount(String decoctionAmount) {
        this.decoctionAmount = decoctionAmount;
    }

    public String getInfuseTemp() {
        return infuseTemp;
    }

    public void setInfuseTemp(String infuseTemp) {
        this.infuseTemp = infuseTemp;
    }

    public String getDisplayStepTemp() {
        return displayStepTemp;
    }

    public void setDisplayStepTemp(String displayStepTemp) {
        this.displayStepTemp = displayStepTemp;
    }

    public String getDisplayInfuseAmount() {
        return displayInfuseAmount;
    }

    public void setDisplayInfuseAmount(String displayInfuseAmount) {
        this.displayInfuseAmount = displayInfuseAmount;
    }
}
