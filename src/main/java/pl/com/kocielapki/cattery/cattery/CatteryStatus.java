package pl.com.kocielapki.cattery.cattery;

import java.util.HashMap;
import java.util.Map;

public enum CatteryStatus {
    CATTERY("C"),
    FOR_SALE("FS"),
    SOLD("S"),
    DIED("D"),
    GIVEN("G");

    private String value;

    CatteryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, CatteryStatus> valuesWithEnums;

    static {
        valuesWithEnums = new HashMap<>();
        for (CatteryStatus valueWithEnum : CatteryStatus.values()) {
            valuesWithEnums.put(valueWithEnum.value, valueWithEnum);
        }
    }

    public static CatteryStatus from(String visibilityStatus) {
        return valuesWithEnums.get(visibilityStatus);
    }
}
