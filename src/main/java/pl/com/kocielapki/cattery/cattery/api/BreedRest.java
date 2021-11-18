package pl.com.kocielapki.cattery.cattery.api;


public class BreedRest {
    private Long id;
    private String name;
    private String shortName;

    public BreedRest() {
    }

    public BreedRest(Long id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
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

}
