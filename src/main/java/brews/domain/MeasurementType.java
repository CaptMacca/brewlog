package brews.domain;

public enum MeasurementType {

    PBG("Pre Boil Gravity"),
    OG("Original Gravity"),
    FG("Final Gravity"),
    VF("Volume in Fermenter"),
    FV("Volume in Keg");

    private final String displayName;

    MeasurementType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
