package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.AnimalDisease;

public interface AnimalDiseaseRepository extends CrudRepository<AnimalDisease, Long>
{

}
