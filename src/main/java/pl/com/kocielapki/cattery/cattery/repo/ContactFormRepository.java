package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.ContactForm;

import java.util.List;

public interface ContactFormRepository extends CrudRepository<ContactForm, Long> {

    List<ContactForm> findByDeleteDateTimeIsNull();
}
