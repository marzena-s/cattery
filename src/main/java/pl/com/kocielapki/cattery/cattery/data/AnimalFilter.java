package pl.com.kocielapki.cattery.cattery.data;

public class AnimalFilter {
    private String name;
    private String lineageName;
    private String gender;
    private String catteryStatus;
    private String saleStatus;
    private String websiteVisibilityStatus;
    private Long page;
    private Long pageSize;
    private Long birthId;
    private Long transactionId;


    public AnimalFilter(String name, String lineageName, String gender, String catteryStatus, String saleStatus, String websiteVisibilityStatus, Long page, Long pageSize) {
        this.name = name;
        this.lineageName = lineageName;
        this.gender = gender;
        this.catteryStatus = catteryStatus;
        this.saleStatus = saleStatus;
        this.websiteVisibilityStatus = websiteVisibilityStatus;
        this.page = page;
        this.pageSize = pageSize;
    }


    public AnimalFilter(Long birthId) {
        this.birthId = birthId;
    }

    public String getName() {
        return name;
    }

    public String getLineageName() {
        return lineageName;
    }

    public String getGender() {
        return gender;
    }

    public String getCatteryStatus() {
        return catteryStatus;
    }

    public String getWebsiteVisibilityStatus() {
        return websiteVisibilityStatus;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public Long getBirthId() {
        return birthId;
    }

    public Long getTransactionId() {
        return transactionId;
    }
}
