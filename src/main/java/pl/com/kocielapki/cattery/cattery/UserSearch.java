package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Repository;
import pl.com.kocielapki.cattery.cattery.data.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserSearch {

    @PersistenceContext
    private EntityManager entityManager;

    List<User> findBy(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> user = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (login != null) {
            Predicate namePredicate = cb.equal(user.get("login"),  login);
            predicates.add(namePredicate);
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<User> query = entityManager.createQuery(cq);
        return query.getResultList();
    }


}
