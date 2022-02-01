package me.angelstoyanov.sporton.management.pitch.model;

public enum PitchType {
    FOOTBALL("soccer"),
    VOLLEYBALL("volleyball"),
    BASKETBALL("basketball"),
    BADMINTON("badminton"),
    HORSE_RACING("horse_racing"),
    TABLE_SOCCER("table_soccer"),
    PING_PONG("table_tennis"),
    TENNIS("tennis");

    private final String osmName;

    PitchType(String osmName) {
        this.osmName = osmName.toLowerCase();
    }

    public String getOsmName() {
        return osmName;
    }
}
