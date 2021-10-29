package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "animal_medicines_therapy")
public class AnimalMedicineTherapy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "animal_disease_id")
    private AnimalDisease animalDisease;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "finish_date")
    private LocalDateTime finishDate;
    private String note;

    public AnimalMedicineTherapy() {
    }

    public AnimalMedicineTherapy(Long id, AnimalDisease animalDisease, Medicine medicine, LocalDateTime startDate, LocalDateTime finishDate, String note) {
        this.id = id;
        this.animalDisease = animalDisease;
        this.medicine = medicine;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public AnimalDisease getAnimalDisease() {
        return animalDisease;
    }

    public Medicine getMedicine() {
        return medicine;
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

    public void setAnimalDisease(AnimalDisease animalDisease) {
        this.animalDisease = animalDisease;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
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
