package pl.com.kocielapki.cattery.cattery.api;

import org.springframework.web.multipart.MultipartFile;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.Birth;
import pl.com.kocielapki.cattery.cattery.data.Breed;
import pl.com.kocielapki.cattery.cattery.data.Image;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class AnimalRest {
    private Long id;
    private String name;
    private Breed breed;
    private Long breedId;
    private String lineageName;
    private String genderCode;
    private String birthDate;
    private String color;
    private String chipNumber;
    private String lineageNumber;
    private BigDecimal weight;
    private String catteryStatusCode;
    private Animal mother;
    private Animal father;
    private Long motherId;
    private Long fatherId;
    private BigDecimal price;
    private String saleStatusCode;
    private Image image;
    private String note;
    private String websiteDescription;
    private String websiteVisibilityStatus;
    private Birth birth;
    private Long birthId;
    private String deleteDateTime;
    private String source;
    private MultipartFile file;



    public AnimalRest() {
    }

    public AnimalRest(String name, Long breedId, String lineageName, String genderCode, String birthDate, String color, String chipNumber, String lineageNumber, BigDecimal weight, String catteryStatusCode, Long motherId, Long fatherId, BigDecimal price, String saleStatusCode, String note, String websiteDescription, String websiteVisibilityStatus, Long birthId, MultipartFile file) {
//    public AnimalRest(String name, Long breedId, String lineageName, String genderCode, String birthDate, String color, String chipNumber, String lineageNumber, BigDecimal weight, String catteryStatusCode, Long motherId, Long fatherId, BigDecimal price, String saleStatusCode, String note, String websiteDescription, String websiteVisibilityStatus, MultipartFile file) {
        this.name = name;
        this.breedId = breedId;
        this.lineageName = lineageName;
        this.genderCode = genderCode;
        this.birthDate = birthDate;
        this.color = color;
        this.chipNumber = chipNumber;
        this.lineageNumber = lineageNumber;
        this.weight = weight;
        this.catteryStatusCode = catteryStatusCode;
        this.motherId = motherId;
        this.fatherId = fatherId;
        this.price = price;
        this.saleStatusCode = saleStatusCode;
        this.note = note;
        this.websiteDescription = websiteDescription;
        this.websiteVisibilityStatus = websiteVisibilityStatus;
        this.birthId = birthId;
        this.file = file;
    }

    public AnimalRest(AnimalRest request) {
        this.name = request.getName();
        this.breed = request.getBreed();
        this.breedId = request.getBreed().getId();
        this.lineageName = request.getLineageName();
        this.genderCode = request.getGenderCode();
        this.birthDate = request.getBirthDate();
        this.color = request.getColor();
        this.chipNumber = request.getChipNumber();
        this.lineageNumber = request.getLineageNumber();
        this.weight = request.getWeight();
        this.catteryStatusCode = request.getCatteryStatusCode();
        this.mother = request.getMother();
        this.father = request.getFather();
        this.source = request.getSource();
        if(request.getMother() != null){
            this.motherId = request.getMother().getId();
        }
        if(request.getFather() != null) {
            this.fatherId = request.getFather().getId();
        }
        this.price = request.getPrice();
        this.saleStatusCode = request.getSaleStatusCode();
        if(request.getImage() != null){
            this.image = request.getImage();
        }
        this.note = request.getNote();
        this.websiteDescription = request.getWebsiteDescription();
        this.websiteVisibilityStatus = request.getWebsiteVisibilityStatus();
        this.birth = request.getBirth();
//        this.deleteDateTime = LocalDateTime.now();
    }


    public AnimalRest(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.breed = animal.getBreed();
        this.breedId = animal.getBreed().getId();
        this.lineageName = animal.getLineageName();
        this.genderCode = animal.getGenderCode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(animal.getBirthDate() != null) {
            this.birthDate = animal.getBirthDate().format(formatter);
        }
        this.color = animal.getColor();
        this.chipNumber = animal.getChipNumber();
        this.lineageNumber = animal.getLineageNumber();
        this.weight = animal.getWeight();
        this.catteryStatusCode = animal.getCatteryStatusCode();
        this.mother = animal.getMother();
        this.father = animal.getFather();
        if(animal.getMother() != null){
            this.motherId = animal.getMother().getId();
        }
        if(animal.getFather() != null) {
            this.fatherId = animal.getFather().getId();
        }
        this.price = animal.getPrice();
        this.saleStatusCode = animal.getSaleStatusCode();
        if(animal.getImage() != null){
            this.image = animal.getImage();
        }
        this.note = animal.getNote();
        this.websiteDescription = animal.getWebsiteDescription();
        this.websiteVisibilityStatus = animal.getWebsiteVisibilityStatus();
        this.birth = animal.getBirth();
        if(animal.getDeleteDateTime() != null) {
            this.deleteDateTime = animal.getDeleteDateTime().toString();
        }
    }

    public Long getId() {
        return id;
    }

    public Breed getBreed() {
        return breed;
    }

    public Long getBreedId() {
        return breedId;
    }

    public String getLineageName() {
        return lineageName;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public String getBirthDate() {
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

    public Animal getMother() {
        return mother;
    }

    public Animal getFather() {
        return father;
    }


    public Long getMotherId() {
        return motherId;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSaleStatusCode() {
        return saleStatusCode;
    }

    public Image getImage() {
        return image;
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

    public Birth getBirth() {
        return birth;
    }

    public String getName() {
        return name;
    }

    public String getDeleteDateTime() {
        return deleteDateTime;
    }

    public String getSource() {
        return source;
    }

    public MultipartFile getFile() {
        return file;
    }

    public Long getBirthId() {
        return birthId;
    }
}
