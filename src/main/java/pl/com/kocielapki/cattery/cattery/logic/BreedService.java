package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.Breed;
import pl.com.kocielapki.cattery.cattery.data.BreedFilter;
import pl.com.kocielapki.cattery.cattery.repo.BreedRepository;

import java.util.List;

@Service
@Transactional
public class BreedService {
   private BreedRepository breedRepository;
   private BreedSearch breedSearch;

    public BreedService(BreedRepository breedRepository, BreedSearch breedSearch) {
        this.breedRepository = breedRepository;
        this.breedSearch = breedSearch;
    }

    public Breed get(Long id){
        return breedRepository.findById(id).get();
    }

    public List<Breed> findBy(BreedFilter filter) {
        return breedSearch.findBy(filter);
    }

}
