package pl.com.kocielapki.cattery.cattery.data;

public class BreedFilter {
    private Long speciesId;


    public BreedFilter(Long speciesId) {
        this.speciesId = speciesId;
    }

    public Long getSpeciesId() {
        return speciesId;
    }
}
