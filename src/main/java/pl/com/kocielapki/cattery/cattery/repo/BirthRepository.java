package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.Birth;

import java.time.LocalDateTime;

public interface BirthRepository extends CrudRepository<Birth, Long>
{

    @Modifying
    @Transactional
    @Query(value = "insert into births (birth_date, amount, name, note, website_description, website_detail_description, website_visibility_status) values (:birth_date, :amount, :name, :note, :website_description, :website_detail_description, :website_visibility_status)", nativeQuery = true)
    void create(@Param("birth_date") LocalDateTime birthDate, @Param("amount") int amount, @Param("name") String name, @Param("note") String note, @Param("website_description") String websiteDescription, @Param("website_detail_description") String websiteDetailDescription, @Param("website_visibility_status") String websiteVisibilityStatus );
//
//    //todo zap id not null, rózne parametry
////    @Modifying
////    @Query("update species set species.name=:name where name.id=:id")
////    void update(@Param("name") String name, @Param("id") Long id);



}
