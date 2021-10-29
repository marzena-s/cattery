package pl.com.kocielapki.cattery.domain;


public class DictionaryData {
    private Long id;
    private String code;
    private String value;
    private String language;


    public DictionaryData(Long id, String value, String language) {
        this.id = id;
        this.value = value;
        this.language = language;
    }

    public DictionaryData(String code, String value, String language) {
        this.code = code;
        this.value = value;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getLanguage() {
        return language;
    }

}
