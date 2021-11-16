package pl.com.kocielapki.cattery.cattery.api;

import org.springframework.web.multipart.MultipartFile;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.Birth;
import pl.com.kocielapki.cattery.cattery.data.Image;

import java.time.format.DateTimeFormatter;
import java.util.Set;

public class BirthRest {
    private Long id;
    private String name;
    private String birthDate;
    private Animal mother;
    private Animal father;
    private Long motherId;
    private Long fatherId;
    private Long amount;
    private Image image;
    private String note;
    private Long imageToDeleteId;
    private String websiteDescription;
    private String websiteDetailsDescription;
    private String websiteVisibilityStatus;
    private String source;
    private MultipartFile file;
    private Set<Image> birthsImages;




    public BirthRest() {
    }


    public BirthRest(Birth birth) {
        this.id = birth.getId();
        this.name = birth.getName();
        this.websiteDetailsDescription = birth.getWebsiteDetailDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthDate = birth.getBirthDate().format(formatter);
        this.mother = birth.getMother();
        this.father = birth.getFather();
        if(birth.getMother() != null){
            this.motherId = birth.getMother().getId();
        }
        if(birth.getFather() != null) {
            this.fatherId = birth.getFather().getId();
        }
        this.note = birth.getNote();
        this.amount = birth.getAmount();
        this.websiteDescription = birth.getWebsiteDescription();
        this.websiteVisibilityStatus = birth.getWebsiteVisibilityStatus();
        if(birth.getImage() != null){
            this.image = birth.getImage();
        }
        if(birth.getBirthsImages() != null){
            this.birthsImages = birth.getBirthsImages();
        }


    }

    public BirthRest(String name, String birthDate, Long motherId, Long fatherId, String note, String websiteDescription, String websiteDetailsDescription, String websiteVisibilityStatus, MultipartFile file) {
        this.name = name;
        this.birthDate = birthDate;
        this.motherId = motherId;
        this.fatherId = fatherId;
        this.note = note;
        this.websiteDescription = websiteDescription;
        this.websiteDetailsDescription = websiteDetailsDescription;
        this.websiteVisibilityStatus = websiteVisibilityStatus;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public String getBirthDate() {
        return birthDate;
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

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }

    public String getWebsiteDetailsDescription() {
        return websiteDetailsDescription;
    }

    public String getSource() {
        return source;
    }

    public MultipartFile getFile() {
        return file;
    }

    public Set<Image> getBirthsImages() {
        return birthsImages;
    }

    public Long getImageToDeleteId() {
        return imageToDeleteId;
    }
}
