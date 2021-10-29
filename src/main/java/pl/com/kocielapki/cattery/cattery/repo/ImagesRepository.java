package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.Image;

public interface ImagesRepository extends CrudRepository<Image, Long>
{
    Image findByImageFileName(String name);
}
