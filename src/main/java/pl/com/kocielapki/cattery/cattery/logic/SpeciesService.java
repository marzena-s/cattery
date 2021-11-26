package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.Species;
import pl.com.kocielapki.cattery.cattery.repo.SpeciesRepository;

@Service
@Transactional
public class SpeciesService {
   private SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public Species get(Long id){
        return speciesRepository.findById(id).get();
    }

}
