package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    @JoinColumn(name="species_id")
    private Set<Breed> breeds;


    public Species() {
    }

    public Species(Long id, String name, Set<Breed> breeds) {
        this.id = id;
        this.name = name;
        this.breeds = breeds;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Breed> getBreeds() {
        return breeds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreeds(Set<Breed> breeds) {
        this.breeds = breeds;
    }
}
