package pl.com.kocielapki.cattery.cattery;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.User;
import pl.com.kocielapki.cattery.cattery.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private UserSearch userSearch;

    public UserService(UserRepository userRepository, UserSearch userSearch) {
        this.userRepository = userRepository;
        this.userSearch = userSearch;
    }

    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        User userToUpdate = userRepository.findById(user.getId()).get();

        userRepository.save(userToUpdate);
    }

    public void delete(Long id) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setDeleteDateTime(LocalDateTime.now());
        userRepository.save(userToUpdate);
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<User> list = userSearch.findBy(authentication.getName());
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
