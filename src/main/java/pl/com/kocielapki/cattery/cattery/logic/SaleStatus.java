package pl.com.kocielapki.cattery.cattery.logic;

import java.util.HashMap;
import java.util.Map;

public enum SaleStatus {
    FREE("F"),
    RESERVED("R"),
    SOLD("S"),
    NONE("N");

    private String value;

    SaleStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, SaleStatus> valuesWithEnums;

    static {
        valuesWithEnums = new HashMap<>();
        for (SaleStatus valueWithEnum : SaleStatus.values()) {
            valuesWithEnums.put(valueWithEnum.value, valueWithEnum);
        }
    }

    public static SaleStatus from(String visibilityStatus) {
        return valuesWithEnums.get(visibilityStatus);
    }
}
