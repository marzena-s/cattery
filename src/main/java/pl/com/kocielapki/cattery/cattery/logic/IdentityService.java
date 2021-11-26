package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.AnimalFilter;
import pl.com.kocielapki.cattery.cattery.data.Birth;

import java.util.List;

@Service
@Transactional
public class IdentityService {
    private AnimalService animalService;
    private BirthService birthService;



    public IdentityService(AnimalService animalService, BirthService birthService) {
        this.animalService = animalService;
        this.birthService = birthService;
    }

    public Animal getAnimal(Long id) {
        return animalService.get(id);
    }

    public List<Animal> findAnimalBy(AnimalFilter filter) {
        return animalService.findBy(filter);
    }

    public Birth getBirth(Long id){
        return birthService.get(id);
    }

}
