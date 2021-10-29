package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLogin(String login);

}
