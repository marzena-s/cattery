package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.AnimalDisease;
import pl.com.kocielapki.cattery.cattery.repo.AnimalDiseaseRepository;

@Service
@Transactional
public class AnimalDiseaseService {
   private AnimalDiseaseRepository animalDiseaseRepository;

    public AnimalDiseaseService(AnimalDiseaseRepository animalDiseaseRepository) {
        this.animalDiseaseRepository = animalDiseaseRepository;
    }

    public AnimalDisease get(Long id){
        return animalDiseaseRepository.findById(id).get();
    }

}
