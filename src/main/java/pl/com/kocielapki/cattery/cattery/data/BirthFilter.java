package pl.com.kocielapki.cattery.cattery.data;

public class BirthFilter {
    private Long motherId;
    private Long fatherId;
    private String birthName;
    private String websiteVisibilityStatus;
    private Long page;
    private Long pageSize;


    public BirthFilter(Long motherId, Long fatherId, String birthName, String websiteVisibilityStatus, Long page, Long pageSize) {
        this.motherId = motherId;
        this.fatherId = fatherId;
        this.birthName = birthName;
        this.websiteVisibilityStatus = websiteVisibilityStatus;
        this.page = page;
        this.pageSize = pageSize;
    }

    public Long getMotherId() {
        return motherId;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public String getBirthName() {
        return birthName;
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
}
