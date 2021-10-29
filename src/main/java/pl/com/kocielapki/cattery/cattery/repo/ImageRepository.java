package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.Image;

public interface ImageRepository extends CrudRepository<Image, Long>
{

    Image findByImageFileName(String name);
    void deleteByImageFileName(String name);

}
