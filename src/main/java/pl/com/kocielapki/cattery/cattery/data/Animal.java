package pl.com.kocielapki.cattery.cattery.data;

import pl.com.kocielapki.cattery.cattery.api.AnimalRest;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;
    private String name;
    @Column(name = "lineage_name")
    private String lineageName;
    @Column(name = "gender_code")
    private String genderCode;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String color;
    @Column(name = "chip_number")
    private String chipNumber;
    @Column(name = "lineage_number")
    private String lineageNumber;
    @Column(name = "weight_kg")
    private BigDecimal weight;
    @Column(name = "cattery_status_code")
    private String catteryStatusCode;
    @OneToOne
    @JoinColumn(name = "mother_animal_id")
    private Animal mother;
    @OneToOne
    @JoinColumn(name = "father_animal_id")
    private Animal father;
    @OneToOne
    @JoinColumn(name = "birth_id")
    private Birth birth;
    private BigDecimal price;
    @Column(name = "sale_status_code")
    private String saleStatusCode;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
    private String note;
    @Column(name = "website_description")
    private String websiteDescription;
    @Column(name = "website_visibility_status")
    private String websiteVisibilityStatus;
    @Column(name = "delete_datetime")
    private LocalDateTime deleteDateTime;

    public Animal() {
    }

    public Animal(Animal animal, LocalDateTime deleteDateTime) {
        this.id = id;
        this.name = animal.getName();
        this.lineageName = animal.getLineageName();
        this.genderCode = animal.getGenderCode();
        this.breed = animal.getBreed();
        this.birthDate = animal.getBirthDate();
        this.color = animal.getColor();
        this.chipNumber = animal.getChipNumber();
        this.lineageNumber = animal.getLineageNumber();
        this.weight = animal.getWeight();
        this.catteryStatusCode = animal.getCatteryStatusCode();
        this.mother = animal.getMother();
        this.father = animal.getFather();
        this.price = animal.getPrice();
        this.saleStatusCode = animal.getSaleStatusCode();
        this.note = animal.getNote();
        this.websiteDescription = animal.getWebsiteDescription();
        this.websiteVisibilityStatus = animal.getWebsiteVisibilityStatus();
        this.deleteDateTime = deleteDateTime;
    }

    public Animal(Long id, Animal animal) {
        this.id = id;
        this.name = animal.getName();
        this.lineageName = animal.getLineageName();
        this.genderCode = animal.getGenderCode();
        this.breed = animal.getBreed();
        this.birthDate = animal.getBirthDate();
        this.color = animal.getColor();
        this.chipNumber = animal.getChipNumber();
        this.lineageNumber = animal.getLineageNumber();
        this.weight = animal.getWeight();
        this.catteryStatusCode = animal.getCatteryStatusCode();
        this.mother = animal.getMother();
        this.father = animal.getFather();
        this.price = animal.getPrice();
        this.saleStatusCode = animal.getSaleStatusCode();
        this.note = animal.getNote();
        this.websiteDescription = animal.getWebsiteDescription();
        this.websiteVisibilityStatus = animal.getWebsiteVisibilityStatus();
    }

    public Animal(AnimalRest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.lineageName = request.getLineageName();
        this.genderCode = request.getGenderCode();
        this.breed = request.getBreed();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
        if (!request.getBirthDate().isEmpty() && request.getBirthDate() != null) {
            this.birthDate = LocalDate.parse(request.getBirthDate(), formatter);
        }
        this.color = request.getColor();
        this.chipNumber = request.getChipNumber();
        this.lineageNumber = request.getLineageNumber();
        this.weight = request.getWeight();
        this.catteryStatusCode = request.getCatteryStatusCode();
        this.mother = request.getMother();
        this.father = request.getFather();
        this.price = request.getPrice();
        this.saleStatusCode = request.getSaleStatusCode();
        this.note = request.getNote();
        this.websiteDescription = request.getWebsiteDescription();
        this.websiteVisibilityStatus = request.getWebsiteVisibilityStatus();
//        DateTimeFormatter deleteFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
        if (request.getDeleteDateTime() != null) {
            this.deleteDateTime = LocalDateTime.parse(request.getDeleteDateTime());
        }
    }

    public Animal(AnimalRest request, Animal mother, Animal father, Breed breed) {
        this.name = request.getName();
        this.lineageName = request.getLineageName();
        this.genderCode = request.getGenderCode();
        this.breed = breed;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthDate = LocalDate.parse(request.getBirthDate(), formatter);
        this.color = request.getColor();
        this.chipNumber = request.getChipNumber();
        this.lineageNumber = request.getLineageNumber();
        this.weight = request.getWeight();
        this.catteryStatusCode = request.getCatteryStatusCode();
        this.mother = mother;
        this.father = father;
        this.price = request.getPrice();
        this.saleStatusCode = request.getSaleStatusCode();
        this.note = request.getNote();
        this.websiteDescription = request.getWebsiteDescription();
        this.websiteVisibilityStatus = request.getWebsiteVisibilityStatus();
    }

    public Animal(Long id, String name, String lineageName, String genderCode, LocalDate birthDate, String color, String chipNumber, String lineageNumber, BigDecimal weight, String catteryStatusCode, Animal mother, Animal father, BigDecimal price, String saleStatusCode, String note, String websiteDescription, String websiteVisibilityStatus) {
        this.id = id;
        this.name = name;
        this.lineageName = lineageName;
        this.genderCode = genderCode;
        this.birthDate = birthDate;
        this.color = color;
        this.chipNumber = chipNumber;
        this.lineageNumber = lineageNumber;
        this.weight = weight;
        this.catteryStatusCode = catteryStatusCode;
        this.mother = mother;
        this.father = father;
        this.price = price;
        this.saleStatusCode = saleStatusCode;
        this.note = note;
        this.websiteDescription = websiteDescription;
        this.websiteVisibilityStatus = websiteVisibilityStatus;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLineageName() {
        return lineageName;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getColor() {
        return color;
    }

    public String getChipNumber() {
        return chipNumber;
    }

    public String getLineageNumber() {
        return lineageNumber;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public String getCatteryStatusCode() {
        return catteryStatusCode;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public String getSaleStatusCode() {
        return saleStatusCode;
    }

    public String getNote() {
        return note;
    }

    public String getWebsiteDescription() {
        return websiteDescription;
    }

    public String getWebsiteVisibilityStatus() {
        return websiteVisibilityStatus;
    }

    public Breed getBreed() {
        return breed;
    }

    public Animal getMother() {
        return mother;
    }

    public Animal getFather() {
        return father;
    }

    public Image getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLineageName(String lineageName) {
        this.lineageName = lineageName;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
    }

    public void setLineageNumber(String lineageNumber) {
        this.lineageNumber = lineageNumber;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setCatteryStatusCode(String catteryStatusCode) {
        this.catteryStatusCode = catteryStatusCode;
    }

    public void setMother(Animal mother) {
        this.mother = mother;
    }

    public void setFather(Animal father) {
        this.father = father;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSaleStatusCode(String saleStatusCode) {
        this.saleStatusCode = saleStatusCode;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setWebsiteDescription(String websiteDescription) {
        this.websiteDescription = websiteDescription;
    }

    public void setWebsiteVisibilityStatus(String websiteVisibilityStatus) {
        this.websiteVisibilityStatus = websiteVisibilityStatus;
    }

    public LocalDateTime getDeleteDateTime() {
        return deleteDateTime;
    }

    public void setDeleteDateTime(LocalDateTime localDateTime) {
        this.deleteDateTime = localDateTime;
    }

    public Birth getBirth() {
        return birth;
    }

    public void setBirth(Birth birth) {
        this.birth = birth;
    }
}
