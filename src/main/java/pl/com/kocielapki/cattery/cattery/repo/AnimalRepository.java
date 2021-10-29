package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.Animal;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    List<Animal> findByDeleteDateTimeIsNull();

    List<Animal> findByDeleteDateTimeIsNullAndCatteryStatusCodeAndGenderCode(String catteryStatusCode, String genderCode);

}
