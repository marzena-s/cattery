package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weights")
public class Weight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    @Column(name = "weight_kg")
    private double weight;
    private LocalDateTime date;

    public Weight() {
    }

    public Weight(Long id, Animal animal, double weight, LocalDateTime date) {
        this.id = id;
        this.animal = animal;
        this.weight = weight;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public double getWeight() {
        return weight;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
