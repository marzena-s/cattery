package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>
{



//    List<Species> find();
//
//    @Modifying
//    @Transactional
//    @Query(value = "insert into species (name) values (:name)", nativeQuery = true)
//    void create(@Param("name") String name);
//
//    @Modifying
//    @Query("update species set species.name=:name where name.id=:id")
//    void update(@Param("name") String name, @Param("id") Long id);



}
