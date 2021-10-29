package pl.com.kocielapki.cattery.cattery.data;

public class CustomerFilter {
    private String firstName;
    private String lastName;
    private String city;
    private Long page;
    private Long pageSize;


    public CustomerFilter(String firstName, String lastName, String city, Long page, Long pageSize) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }
}
