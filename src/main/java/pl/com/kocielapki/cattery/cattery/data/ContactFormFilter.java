package pl.com.kocielapki.cattery.cattery.data;

public class ContactFormFilter {
    private String name;
    private Boolean isServed;
    private Long page;
    private Long pageSize;

    public ContactFormFilter(String name, Boolean isServed, Long page, Long pageSize) {
        this.name = name;
        this.isServed = isServed;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public Boolean getServed() {
        return isServed;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }
}
