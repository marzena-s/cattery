package pl.com.kocielapki.cattery.cattery;

import java.util.HashMap;
import java.util.Map;

public enum AnimalDataName {
    BREED("rasa"),
    NAME("imię"),
    GENDER("płeć"),
    CATTERY_STATUS("status hodowlany"),
    BIRTH_DATE("data urodzenia"),
    SALE_STATUS("status sprzedaży");

    private String value;

    AnimalDataName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, AnimalDataName> valuesWithEnums;

    static {
        valuesWithEnums = new HashMap<>();
        for (AnimalDataName valueWithEnum : AnimalDataName.values()) {
            valuesWithEnums.put(valueWithEnum.value, valueWithEnum);
        }
    }

    public static AnimalDataName from(String visibilityStatus) {
        return valuesWithEnums.get(visibilityStatus);
    }
}
