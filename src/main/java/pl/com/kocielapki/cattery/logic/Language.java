package pl.com.kocielapki.cattery.logic;

public enum Language {
    PL("pl_PL"),
    US("us_US"),
    EN("en_EN");
    private String code;
    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
