package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "vaccinations_and_medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "type_code")
    private String type;
    private String purpose;
    private String note;


    public Medicine() {
    }

    public Medicine(Long id, String name, String type, String purpose, String note) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.purpose = purpose;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getNote() {
        return note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
