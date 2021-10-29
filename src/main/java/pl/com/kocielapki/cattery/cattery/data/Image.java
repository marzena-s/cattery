package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_file_name")
    private String imageFileName;

    public Image() {
    }

    public Image(Long id, String imageFileName) {
        this.id = id;
        this.imageFileName = imageFileName;
    }

    public Image(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Long getId() {
        return id;
    }

    public String getImageFileName() {
        return imageFileName;
    }
}
