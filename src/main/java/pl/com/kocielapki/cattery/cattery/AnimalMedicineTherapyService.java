package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.AnimalMedicineTherapy;
import pl.com.kocielapki.cattery.cattery.repo.AnimalMedicineTherapyRepository;

@Service
@Transactional
public class AnimalMedicineTherapyService {
   private AnimalMedicineTherapyRepository animalMedicineTherapyRepository;

    public AnimalMedicineTherapyService(AnimalMedicineTherapyRepository animalMedicineTherapyRepository) {
        this.animalMedicineTherapyRepository = animalMedicineTherapyRepository;
    }

    public AnimalMedicineTherapy get(Long id){
        return animalMedicineTherapyRepository.findById(id).get();
    }

}
