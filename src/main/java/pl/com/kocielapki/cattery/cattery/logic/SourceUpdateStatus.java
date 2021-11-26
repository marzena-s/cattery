package pl.com.kocielapki.cattery.cattery.logic;

public enum SourceUpdateStatus {
    DELETE("delete"),
    UPDATE("update"),
    CANCEL("cancel"),
    DELETE_BIRTH_IMAGE("delete_birth_image");

    private String value;

    SourceUpdateStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
