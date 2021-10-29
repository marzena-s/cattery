package pl.com.kocielapki.cattery.cattery.api;

import pl.com.kocielapki.cattery.cattery.data.Customer;

public class CustomerRest {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String postalCode;
    private String country;
    private String city;
    private String street;
    private String buildingNo;
    private String flatNo;
    private String email;
    private String personalIdNumber;
    private String note;
    private String source;


    public CustomerRest() {
    }

    public CustomerRest(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phoneNumber = customer.getPhoneNumber();
        this.country = customer.getCountry();
        this.postalCode = customer.getPostalCode();
        this.city = customer.getCity();
        this.street = customer.getStreet();
        this.buildingNo = customer.getBuildingNo();
        this.flatNo = customer.getFlatNo();
        this.email = customer.getEmail();
        this.personalIdNumber = customer.getPersonalIdNumber();
        this.note = customer.getNote();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    public String getNote() {
        return note;
    }

    public String getSource() {
        return source;
    }

    public String getCountry() {
        return country;
    }
}
