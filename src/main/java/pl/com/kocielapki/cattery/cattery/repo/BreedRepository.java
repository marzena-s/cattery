package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.Breed;

public interface BreedRepository extends CrudRepository<Breed, Long>
{

}
