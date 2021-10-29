package pl.com.kocielapki.cattery.cattery.data;

import pl.com.kocielapki.cattery.cattery.api.BirthRest;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "births")
public class Birth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mother_id", referencedColumnName = "id")
    private Animal mother;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_image_id", referencedColumnName = "id")
    private Image image;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "father_id", referencedColumnName = "id")
    private Animal father;
    private Long amount;
    private String name;
    private String note;
    @Column(name = "website_description")
    private String websiteDescription;
    @Column(name = "website_detail_description")
    private String websiteDetailDescription;
    @Column(name = "website_visibility_status")
    private String websiteVisibilityStatus;
    @ManyToMany
    @JoinTable(name = "images_to_births",
    joinColumns = @JoinColumn(name = "birth_id"),
    inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> birthsImages;
    @Column(name = "delete_datetime")
    private LocalDateTime deleteDateTime;

    public Birth() {
    }

    public Birth(Long id, LocalDate birthDate, Animal mother, Animal father, Long amount, String name, String note, String websiteDescription, String websiteDetailDescription, String websiteVisibilityStatus, LocalDateTime deleteDateTime) {
        this.id = id;
        this.birthDate = birthDate;
        this.mother = mother;
        this.father = father;
        this.amount = amount;
        this.name = name;
        this.note = note;
        this.websiteDescription = websiteDescription;
        this.websiteDetailDescription = websiteDetailDescription;
        this.websiteVisibilityStatus = websiteVisibilityStatus;
        this.deleteDateTime = deleteDateTime;
    }

    public Birth (BirthRest request, Animal mother, Animal father){
        this.name = request.getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthDate =LocalDate.parse(request.getBirthDate(), formatter);
        this.amount = request.getAmount();
        this.websiteDetailDescription = request.getWebsiteDetailsDescription();
        this.mother = mother;
        this.father = father;
        this.note = request.getNote();
        this.websiteDescription = request.getWebsiteDescription();
        this.websiteVisibilityStatus = request.getWebsiteVisibilityStatus();
    }

    public Birth(Long id) {
        this.id = id;
    }

    public Birth (BirthRest request){
        this.id= request.getId();
        this.name = request.getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthDate =LocalDate.parse(request.getBirthDate(), formatter);
        this.amount = request.getAmount();
        this.websiteDetailDescription = request.getWebsiteDetailsDescription();
        this.note = request.getNote();
        this.websiteDescription = request.getWebsiteDescription();
        this.websiteVisibilityStatus = request.getWebsiteVisibilityStatus();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Animal getMother() {
        return mother;
    }

    public void setMother(Animal mother) {
        this.mother = mother;
    }

    public Animal getFather() {
        return father;
    }

    public void setFather(Animal father) {
        this.father = father;
    }

    public Long getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public String getWebsiteDescription() {
        return websiteDescription;
    }

    public String getWebsiteDetailDescription() {
        return websiteDetailDescription;
    }

    public String getWebsiteVisibilityStatus() {
        return websiteVisibilityStatus;
    }

    public Set<Image> getBirthsImages() {
        return birthsImages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setWebsiteDescription(String websiteDescription) {
        this.websiteDescription = websiteDescription;
    }

    public void setWebsiteDetailDescription(String websiteDetailDescription) {
        this.websiteDetailDescription = websiteDetailDescription;
    }

    public void setWebsiteVisibilityStatus(String websiteVisibilityStatus) {
        this.websiteVisibilityStatus = websiteVisibilityStatus;
    }

    public void setBirthsImages(Set<Image> birthsImages) {
        this.birthsImages = birthsImages;
    }

    public LocalDateTime getDeleteDateTime() {
        return deleteDateTime;
    }

    public void setDeleteDateTime(LocalDateTime deleteDateTime) {
        this.deleteDateTime = deleteDateTime;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
