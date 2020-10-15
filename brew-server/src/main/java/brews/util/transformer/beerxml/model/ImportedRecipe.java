package brews.util.transformer.beerxml.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Deserialised beer xml recipe
 */
@XmlRootElement(name = "RECIPE")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportedRecipe {

    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "VERSION")
    private int version;
    @XmlElement(name = "TYPE")
    private String type;
    @XmlElement(name = "BREWER")
    private String brewer;
    @XmlElement(name = "ASST_BREWER")
    private String assistantBrewer;
    @XmlElement(name = "BATCH_SIZE")
    private double batchSize;
    @XmlElement(name = "BOIL_SIZE")
    private double boilSize;
    @XmlElement(name = "BOIL_TIME")
    private double boilTime;
    @XmlElement(name = "EFFICIENCY")
    private double efficency;
    @XmlElement(name = "NOTES")
    private String notes;
    @XmlElement(name = "TASTE_NOTES")
    private String tasteNotes;
    @XmlElement(name = "TASTE_RATING")
    private double tasteRating;
    @XmlElement(name = "OG")
    private double originalGravity;
    @XmlElement(name = "FG")
    private double finalGravity;
    @XmlElement(name = "CARBONATION")
    private double carbonation;
    @XmlElement(name = "FERMENTATION_STAGES")
    private int fermentationStages;
    @XmlElement(name = "PRIMARY_AGE")
    private int primaryAge;
    @XmlElement(name = "PRIMARY_TEMP")
    private double primaryTemp;
    @XmlElement(name = "SECONDARY_AGE")
    private int secondaryAge;
    @XmlElement(name = "SECONDARY_TEMP")
    private double secondaryTemp;
    @XmlElement(name = "TERTIARY_AGE")
    private int tertiaryAge;
    @XmlElement(name = "AGE")
    private int age;
    @XmlElement(name = "AGE_TEMP")
    private double ageTemp;
    @XmlElement(name = "CARBONATION_USED")
    private String carbonationUsed;
    @XmlElement(name = "DATE")
    private String date;
    @XmlElement(name = "EST_OG")
    private String estimatedOriginalGravity;
    @XmlElement(name = "EST_FG")
    private String estimatedFinalGravity;
    @XmlElement(name = "EST_COLOR")
    private String estimatedColour;
    @XmlElement(name = "IBU")
    private String ibu;
    @XmlElement(name = "IBU_METHOD")
    private String ibuMethod;
    @XmlElement(name = "EST_ABV")
    private String estimatedAbv;
    @XmlElement(name = "ABV")
    private String abv;
    @XmlElement(name = "ACTUAL_EFFICENCY")
    private String actualEfficency;
    @XmlElement(name = "CALORIES")
    private String calories;
    @XmlElement(name = "DISPLAY_BATCH_SIZE")
    private String displayBatchSize;
    @XmlElement(name = "DISPLAY_BOIL_SIZE")
    private String displayBoilSize;
    @XmlElement(name = "DISPLAY_OG")
    private String displayOriginalGravity;
    @XmlElement(name = "DISPLAY_FG")
    private String displayFinalGravity;
    @XmlElement(name = "DISPLAY_PRIMARY_TEMP")
    private String displayPrimaryTemp;
    @XmlElement(name = "DISPLAY_SECONDARY_TEMP")
    private String displaySecondaryTemp;
    @XmlElement(name = "DISPLAY_TERTIARY_TEMP")
    private String displayTertiaryTemp;
    @XmlElement(name = "DISPLAY_AGE_TEMP")
    private String displayAgeTemp;
    @XmlElementWrapper(name = "HOPS")
    @XmlElement(name = "HOP")
    private List<ImportedHop> importedHops;
    @XmlElementWrapper(name = "MISCS")
    @XmlElement(name = "MISC")
    private List<ImportedMisc> importedMiscs;
    @XmlElementWrapper(name = "FERMENTABLES")
    @XmlElement(name = "FERMENTABLE")
    private List<ImportedFermentable> importedFermentables;
    @XmlElementWrapper(name = "YEASTS")
    @XmlElement(name = "YEAST")
    private List<ImportedYeast> importedYeasts;
    @XmlElementWrapper(name = "WATERS")
    @XmlElement(name = "WATER")
    private List<ImportedWater> importedWaters;
    @XmlElement(name = "STYLE")
    private ImportedStyle importedStyle;
    @XmlElement(name = "EQUIPMENT")
    private ImportedEquipment importedEquipment;
    @XmlElement(name = "MASH")
    private ImportedMash importedMash;

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

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public String getAssistantBrewer() {
        return assistantBrewer;
    }

    public void setAssistantBrewer(String assistantBrewer) {
        this.assistantBrewer = assistantBrewer;
    }

    public double getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(double batchSize) {
        this.batchSize = batchSize;
    }

    public double getBoilSize() {
        return boilSize;
    }

    public void setBoilSize(double boilSize) {
        this.boilSize = boilSize;
    }

    public double getBoilTime() {
        return boilTime;
    }

    public void setBoilTime(double boilTime) {
        this.boilTime = boilTime;
    }

    public double getEfficency() {
        return efficency;
    }

    public void setEfficency(double efficency) {
        this.efficency = efficency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTasteNotes() {
        return tasteNotes;
    }

    public void setTasteNotes(String tasteNotes) {
        this.tasteNotes = tasteNotes;
    }

    public double getTasteRating() {
        return tasteRating;
    }

    public void setTasteRating(double tasteRating) {
        this.tasteRating = tasteRating;
    }

    public double getOriginalGravity() {
        return originalGravity;
    }

    public void setOriginalGravity(double originalGravity) {
        this.originalGravity = originalGravity;
    }

    public double getFinalGravity() {
        return finalGravity;
    }

    public void setFinalGravity(double finalGravity) {
        this.finalGravity = finalGravity;
    }

    public double getCarbonation() {
        return carbonation;
    }

    public void setCarbonation(double carbonation) {
        this.carbonation = carbonation;
    }

    public int getFermentationStages() {
        return fermentationStages;
    }

    public void setFermentationStages(int fermentationStages) {
        this.fermentationStages = fermentationStages;
    }

    public int getPrimaryAge() {
        return primaryAge;
    }

    public void setPrimaryAge(int primaryAge) {
        this.primaryAge = primaryAge;
    }

    public double getPrimaryTemp() {
        return primaryTemp;
    }

    public void setPrimaryTemp(double primaryTemp) {
        this.primaryTemp = primaryTemp;
    }

    public int getSecondaryAge() {
        return secondaryAge;
    }

    public void setSecondaryAge(int secondaryAge) {
        this.secondaryAge = secondaryAge;
    }

    public double getSecondaryTemp() {
        return secondaryTemp;
    }

    public void setSecondaryTemp(double secondaryTemp) {
        this.secondaryTemp = secondaryTemp;
    }

    public int getTertiaryAge() {
        return tertiaryAge;
    }

    public void setTertiaryAge(int tertiaryAge) {
        this.tertiaryAge = tertiaryAge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAgeTemp() {
        return ageTemp;
    }

    public void setAgeTemp(double ageTemp) {
        this.ageTemp = ageTemp;
    }

    public String getCarbonationUsed() {
        return carbonationUsed;
    }

    public void setCarbonationUsed(String carbonationUsed) {
        this.carbonationUsed = carbonationUsed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEstimatedOriginalGravity() {
        return estimatedOriginalGravity;
    }

    public void setEstimatedOriginalGravity(String estimatedOriginalGravity) {
        this.estimatedOriginalGravity = estimatedOriginalGravity;
    }

    public String getEstimatedFinalGravity() {
        return estimatedFinalGravity;
    }

    public void setEstimatedFinalGravity(String estimatedFinalGravity) {
        this.estimatedFinalGravity = estimatedFinalGravity;
    }

    public String getEstimatedColour() {
        return estimatedColour;
    }

    public void setEstimatedColour(String estimatedColour) {
        this.estimatedColour = estimatedColour;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public String getIbuMethod() {
        return ibuMethod;
    }

    public void setIbuMethod(String ibuMethod) {
        this.ibuMethod = ibuMethod;
    }

    public String getEstimatedAbv() {
        return estimatedAbv;
    }

    public void setEstimatedAbv(String estimatedAbv) {
        this.estimatedAbv = estimatedAbv;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getActualEfficency() {
        return actualEfficency;
    }

    public void setActualEfficency(String actualEfficency) {
        this.actualEfficency = actualEfficency;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDisplayBatchSize() {
        return displayBatchSize;
    }

    public void setDisplayBatchSize(String displayBatchSize) {
        this.displayBatchSize = displayBatchSize;
    }

    public String getDisplayBoilSize() {
        return displayBoilSize;
    }

    public void setDisplayBoilSize(String displayBoilSize) {
        this.displayBoilSize = displayBoilSize;
    }

    public String getDisplayOriginalGravity() {
        return displayOriginalGravity;
    }

    public void setDisplayOriginalGravity(String displayOriginalGravity) {
        this.displayOriginalGravity = displayOriginalGravity;
    }

    public String getDisplayFinalGravity() {
        return displayFinalGravity;
    }

    public void setDisplayFinalGravity(String displayFinalGravity) {
        this.displayFinalGravity = displayFinalGravity;
    }

    public String getDisplayPrimaryTemp() {
        return displayPrimaryTemp;
    }

    public void setDisplayPrimaryTemp(String displayPrimaryTemp) {
        this.displayPrimaryTemp = displayPrimaryTemp;
    }

    public String getDisplaySecondaryTemp() {
        return displaySecondaryTemp;
    }

    public void setDisplaySecondaryTemp(String displaySecondaryTemp) {
        this.displaySecondaryTemp = displaySecondaryTemp;
    }

    public String getDisplayTertiaryTemp() {
        return displayTertiaryTemp;
    }

    public void setDisplayTertiaryTemp(String displayTertiaryTemp) {
        this.displayTertiaryTemp = displayTertiaryTemp;
    }

    public String getDisplayAgeTemp() {
        return displayAgeTemp;
    }

    public void setDisplayAgeTemp(String displayAgeTemp) {
        this.displayAgeTemp = displayAgeTemp;
    }

    public List<ImportedHop> getImportedHops() {
        return importedHops;
    }

    public void setImportedHops(List<ImportedHop> importedHops) {
        this.importedHops = importedHops;
    }

    public List<ImportedMisc> getImportedMiscs() {
        return importedMiscs;
    }

    public void setImportedMiscs(List<ImportedMisc> importedMiscs) {
        this.importedMiscs = importedMiscs;
    }

    public List<ImportedFermentable> getImportedFermentables() {
        return importedFermentables;
    }

    public void setImportedFermentables(List<ImportedFermentable> importedFermentables) {
        this.importedFermentables = importedFermentables;
    }

    public List<ImportedYeast> getImportedYeasts() {
        return importedYeasts;
    }

    public void setImportedYeasts(List<ImportedYeast> importedYeasts) {
        this.importedYeasts = importedYeasts;
    }

    public List<ImportedWater> getImportedWaters() {
        return importedWaters;
    }

    public void setImportedWaters(List<ImportedWater> importedWaters) {
        this.importedWaters = importedWaters;
    }

    public ImportedStyle getImportedStyle() {
        return importedStyle;
    }

    public void setImportedStyle(ImportedStyle importedStyle) {
        this.importedStyle = importedStyle;
    }

    public ImportedEquipment getImportedEquipment() {
        return importedEquipment;
    }

    public void setImportedEquipment(ImportedEquipment importedEquipment) {
        this.importedEquipment = importedEquipment;
    }

    public ImportedMash getImportedMash() {
        return importedMash;
    }

    public void setImportedMash(ImportedMash importedMash) {
        this.importedMash = importedMash;
    }
}
