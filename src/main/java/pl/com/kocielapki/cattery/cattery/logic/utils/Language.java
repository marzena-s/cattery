package pl.com.kocielapki.cattery.cattery.logic.utils;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    ENGLISH("en"),
    POLISH("pl");
    private String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String, Language> codesWithEnums;

    static {
        codesWithEnums = new HashMap<>();
        for (Language codeWithEnum : Language.values()) {
            codesWithEnums.put(codeWithEnum.code, codeWithEnum);
        }
    }

    public static Language from(String code) {
        return codesWithEnums.get(code);
    }
}
