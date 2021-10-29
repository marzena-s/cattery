package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "animal_diseases")
public class AnimalDisease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "finish_date")
    private LocalDateTime finishDate;
    private String note;

    public AnimalDisease() {
    }

    public AnimalDisease(Long id, Disease disease, Animal animal, LocalDateTime startDate, LocalDateTime finishDate, String note) {
        this.id = id;
        this.disease = disease;
        this.animal = animal;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public Disease getDisease() {
        return disease;
    }

    public Animal getAnimal() {
        return animal;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public String getNote() {
        return note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
