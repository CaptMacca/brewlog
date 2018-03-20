package brews.domain.beerxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Steve on 25/06/2017.
 */
@XmlRootElement(name = "STYLE")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedStyle {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private int version;
    @XmlElement(name = "CATEGORY")
    private String category;
    @XmlElement(name = "CATEGORY_NUMBER")
    private int categoryNumber;
    @XmlElement(name = "STYLE_LETTER")
    private String styleLetter;
    @XmlElement(name = "STYLE_GUIDE")
    private String styleGuide;
    @XmlElement(name = "TYPE")
    private String type;
    @XmlElement(name = "OG_MIN")
    private double originalGravityMin;
    @XmlElement(name = "OG_MAX")
    private double originalGravityMax;
    @XmlElement(name = "FG_MIN")
    private double finalGravityMin;
    @XmlElement(name = "FG_MAX")
    private double finalGravityMax;
    @XmlElement(name = "IBU_MIN")
    private double ibuMin;
    @XmlElement(name = "IBU_MAX")
    private double ibuMax;
    @XmlElement(name = "COLOR_MIN")
    private double colourMin;
    @XmlElement(name = "COLOR_MAX")
    private double colourMax;
    @XmlElement(name = "CARB_MIN")
    private double carbonationMin;
    @XmlElement(name = "CARB_MAX")
    private double carbonationMax;
    @XmlElement(name = "ABV_MAX")
    private double abvMax;
    @XmlElement(name = "ABV_MIN")
    private double abvMin;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "PROFILE")
    private String profile;
    @XmlElement(name = "INGREDIENTS")
    private String ingredients;
    @XmlElement(name = "EXAMPLES")
    private String examples;
    @XmlElement(name = "DISPLAY_OG_MIN")
    private String displayOriginalGravityMin;
    @XmlElement(name = "DISPLAY_OG_MAX")
    private String displayOriginalGraviryMax;
    @XmlElement(name = "DISPLAY_FG_MIN")
    private String displayFinalGravityMin;
    @XmlElement(name = "DISPLAY_FG_MAX")
    private String displayFinalGravityMax;
    @XmlElement(name = "DISPLAY_COLOR_MIN")
    private String displayColourMin;
    @XmlElement(name = "DISPLAY_COLOR_MAX")
    private String displayColourMax;
    @XmlElement(name = "OG_RANGE")
    private String originalGravityRange;
    @XmlElement(name = "FG_RANGE")
    private String finalGravityRange;
    @XmlElement(name = "IBU_RANGE")
    private String ibuRange;
    @XmlElement(name = "CARB_RANGE")
    private String carbonationRange;
    @XmlElement(name = "COLOR_RANGE")
    private String colourRange;
    @XmlElement(name = "ABV_RANGE")
    private String abvRange;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getStyleLetter() {
        return styleLetter;
    }

    public void setStyleLetter(String styleLetter) {
        this.styleLetter = styleLetter;
    }

    public String getStyleGuide() {
        return styleGuide;
    }

    public void setStyleGuide(String styleGuide) {
        this.styleGuide = styleGuide;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getOriginalGravityMin() {
        return originalGravityMin;
    }

    public void setOriginalGravityMin(double originalGravityMin) {
        this.originalGravityMin = originalGravityMin;
    }

    public double getOriginalGravityMax() {
        return originalGravityMax;
    }

    public void setOriginalGravityMax(double originalGravityMax) {
        this.originalGravityMax = originalGravityMax;
    }

    public double getFinalGravityMin() {
        return finalGravityMin;
    }

    public void setFinalGravityMin(double finalGravityMin) {
        this.finalGravityMin = finalGravityMin;
    }

    public double getFinalGravityMax() {
        return finalGravityMax;
    }

    public void setFinalGravityMax(double finalGravityMax) {
        this.finalGravityMax = finalGravityMax;
    }

    public double getIbuMin() {
        return ibuMin;
    }

    public void setIbuMin(double ibuMin) {
        this.ibuMin = ibuMin;
    }

    public double getIbuMax() {
        return ibuMax;
    }

    public void setIbuMax(double ibuMax) {
        this.ibuMax = ibuMax;
    }

    public double getColourMin() {
        return colourMin;
    }

    public void setColourMin(double colourMin) {
        this.colourMin = colourMin;
    }

    public double getColourMax() {
        return colourMax;
    }

    public void setColourMax(double colourMax) {
        this.colourMax = colourMax;
    }

    public double getCarbonationMin() {
        return carbonationMin;
    }

    public void setCarbonationMin(double carbonationMin) {
        this.carbonationMin = carbonationMin;
    }

    public double getCarbonationMax() {
        return carbonationMax;
    }

    public void setCarbonationMax(double carbonationMax) {
        this.carbonationMax = carbonationMax;
    }

    public double getAbvMax() {
        return abvMax;
    }

    public void setAbvMax(double abvMax) {
        this.abvMax = abvMax;
    }

    public double getAbvMin() {
        return abvMin;
    }

    public void setAbvMin(double abvMin) {
        this.abvMin = abvMin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getDisplayOriginalGravityMin() {
        return displayOriginalGravityMin;
    }

    public void setDisplayOriginalGravityMin(String displayOriginalGravityMin) {
        this.displayOriginalGravityMin = displayOriginalGravityMin;
    }

    public String getDisplayOriginalGraviryMax() {
        return displayOriginalGraviryMax;
    }

    public void setDisplayOriginalGraviryMax(String displayOriginalGraviryMax) {
        this.displayOriginalGraviryMax = displayOriginalGraviryMax;
    }

    public String getDisplayFinalGravityMin() {
        return displayFinalGravityMin;
    }

    public void setDisplayFinalGravityMin(String displayFinalGravityMin) {
        this.displayFinalGravityMin = displayFinalGravityMin;
    }

    public String getDisplayFinalGravityMax() {
        return displayFinalGravityMax;
    }

    public void setDisplayFinalGravityMax(String displayFinalGravityMax) {
        this.displayFinalGravityMax = displayFinalGravityMax;
    }

    public String getDisplayColourMin() {
        return displayColourMin;
    }

    public void setDisplayColourMin(String displayColourMin) {
        this.displayColourMin = displayColourMin;
    }

    public String getDisplayColourMax() {
        return displayColourMax;
    }

    public void setDisplayColourMax(String displayColourMax) {
        this.displayColourMax = displayColourMax;
    }

    public String getOriginalGravityRange() {
        return originalGravityRange;
    }

    public void setOriginalGravityRange(String originalGravityRange) {
        this.originalGravityRange = originalGravityRange;
    }

    public String getFinalGravityRange() {
        return finalGravityRange;
    }

    public void setFinalGravityRange(String finalGravityRange) {
        this.finalGravityRange = finalGravityRange;
    }

    public String getIbuRange() {
        return ibuRange;
    }

    public void setIbuRange(String ibuRange) {
        this.ibuRange = ibuRange;
    }

    public String getCarbonationRange() {
        return carbonationRange;
    }

    public void setCarbonationRange(String carbonationRange) {
        this.carbonationRange = carbonationRange;
    }

    public String getColourRange() {
        return colourRange;
    }

    public void setColourRange(String colourRange) {
        this.colourRange = colourRange;
    }

    public String getAbvRange() {
        return abvRange;
    }

    public void setAbvRange(String abvRange) {
        this.abvRange = abvRange;
    }
}
