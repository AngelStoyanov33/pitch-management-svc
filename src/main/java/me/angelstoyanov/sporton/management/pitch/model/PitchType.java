package me.angelstoyanov.sporton.management.pitch.model;

public enum PitchType {
    FOOTBALL("soccer", "pitch"),
    VOLLEYBALL("volleyball", "pitch"),
    BASKETBALL("basketball", "pitch"),
    BADMINTON("badminton", "pitch"),
    HORSE_RACING("horse_racing", "track"),
    TABLE_SOCCER("table_soccer", "sports_centre"),
    PING_PONG("table_tennis", "pitch"),
    TENNIS("tennis", "pitch"),
    BASEBALL("baseball", "pitch"),
    BOXING("boxing", "sports_centre"),
    KARTING("karting", "pitch"),
    SWIMMING("swimming", "sports_centre");

    private final String osmName;
    private final String leisureType;

    PitchType(String osmName, String leisureType) {
        this.osmName = osmName.toLowerCase();
        this.leisureType = leisureType.toLowerCase();
    }


    public String getOsmName() {
        return osmName;
    }

    public String getLeisureType() {
        return leisureType;
    }
}
