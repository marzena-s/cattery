package pl.com.kocielapki.cattery.cattery.logic;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("U");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, Gender> valuesWithEnums;

    static {
        valuesWithEnums = new HashMap<>();
        for (Gender valueWithEnum : Gender.values()) {
            valuesWithEnums.put(valueWithEnum.value, valueWithEnum);
        }
    }

    public static Gender from(String genderCode) {
        return valuesWithEnums.get(genderCode);
    }
}
