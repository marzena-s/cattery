package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;

//@Entity
public class SpeciesDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String details;

    //jeżeli z tej ppozycji chę mieć powiązanie do Species, nieobowiązkowe
    @OneToOne(mappedBy = "speciesDetails")
    private Species species;


    public SpeciesDetails() {
    }

    public SpeciesDetails(Long id, String details) {
        this.id = id;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }
}
