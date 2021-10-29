package pl.com.kocielapki.cattery.cattery.data;

import pl.com.kocielapki.cattery.cattery.api.CustomerRest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "postal_code")
    private String postalCode;
    private String country;
    private String city;
    private String street;
    @Column(name = "building_no")
    private String buildingNo;
    @Column(name = "flat_no")
    private String flatNo;
    @Column(name = "e_mail")
    private String email;
    @Column(name = "personal_id_number")
    private String personalIdNumber;
    private String note;
    @Column(name = "delete_datetime")
    private LocalDateTime deleteDateTime;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, String phoneNumber, String postalCode, String country, String city, String street, String buildingNo, String flatNo, String email, String personalIdNumber, String note, LocalDateTime deleteDateTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.buildingNo = buildingNo;
        this.flatNo = flatNo;
        this.email = email;
        this.personalIdNumber = personalIdNumber;
        this.note = note;
        this.deleteDateTime = deleteDateTime;
    }

    public Customer(CustomerRest request) {
        this.id = request.getId();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.phoneNumber = request.getPhoneNumber();
        this.country = request.getCountry();
        this.postalCode = request.getPostalCode();
        this.city = request.getCity();
        this.street = request.getStreet();
        this.buildingNo = request.getBuildingNo();
        this.flatNo = request.getFlatNo();
        this.email = request.getEmail();
        this.personalIdNumber = request.getPersonalIdNumber();
        this.note = request.getNote();
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonalIdNumber(String personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDeleteDateTime() {
        return deleteDateTime;
    }

    public void setDeleteDateTime(LocalDateTime deleteDateTime) {
        this.deleteDateTime = deleteDateTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}