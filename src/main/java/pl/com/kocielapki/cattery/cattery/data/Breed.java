package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;

@Entity
@Table(name = "breeds")
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "short_name")
    private String shortName;
//    @ManyToOne
//    @JoinColumn(name = "species_id")
//    private Species species;

    public Breed() {
    }

    public Breed(Long id, String name, String shortName, Species species) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
//        this.species = species;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

//    public Species getSpecies() {
//        return species;
//    }
}
