package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.AnimalDisease;

public interface AnimalDiseaseRepository extends CrudRepository<AnimalDisease, Long>
{

//    List<Species> find();
//
//    @Modifying
//    @Transactional
//    @Query(value = "insert into breeds (name, short_name, species_id) values (:name, :shortName, :speciesId)", nativeQuery = true)
//    void create(@Param("name") String name, @Param("shortName") String shortName, @Param("speciesId") Long speciesId);
//
//    //todo zap id not null, rózne parametry
////    @Modifying
////    @Query("update species set species.name=:name where name.id=:id")
////    void update(@Param("name") String name, @Param("id") Long id);



}
