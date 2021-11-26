package pl.com.kocielapki.cattery.cattery.logic;

import java.util.HashMap;
import java.util.Map;

public enum WebsiteVisibilityStatus {
    VISIBLE("V"),
    INVISIBLE("I"),
    NONE("N");

    private String value;

    WebsiteVisibilityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, WebsiteVisibilityStatus> valuesWithEnums;

    static {
        valuesWithEnums = new HashMap<>();
        for (WebsiteVisibilityStatus valueWithEnum : WebsiteVisibilityStatus.values()) {
            valuesWithEnums.put(valueWithEnum.value, valueWithEnum);
        }
    }

    public static WebsiteVisibilityStatus from(String visibilityStatus) {
        return valuesWithEnums.get(visibilityStatus);
    }
}
