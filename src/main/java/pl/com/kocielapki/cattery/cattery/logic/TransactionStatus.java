package pl.com.kocielapki.cattery.cattery.logic;

import java.util.HashMap;
import java.util.Map;

public enum TransactionStatus {
    CANCELLED("C"),
    FINISHED("F"),
    IN_PROGRESS("IP");

    private String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, TransactionStatus> valuesWithEnums;

    static {
        valuesWithEnums = new HashMap<>();
        for (TransactionStatus valueWithEnum : TransactionStatus.values()) {
            valuesWithEnums.put(valueWithEnum.value, valueWithEnum);
        }
    }

    public static TransactionStatus from(String visibilityStatus) {
        return valuesWithEnums.get(visibilityStatus);
    }
}
