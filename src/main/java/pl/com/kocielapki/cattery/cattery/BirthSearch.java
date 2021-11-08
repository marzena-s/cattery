package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Repository;
import pl.com.kocielapki.cattery.cattery.data.Birth;
import pl.com.kocielapki.cattery.cattery.data.BirthFilter;

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
public class BirthSearch {

    @PersistenceContext
    private EntityManager entityManager;

    List<Birth> findBy(BirthFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Birth> cq = cb.createQuery(Birth.class);

        Root<Birth> birth = cq.from(Birth.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getMotherId() != null) {
            Predicate namePredicate = cb.equal(birth.get("mother").get("id"),  filter.getMotherId());
            predicates.add(namePredicate);
        }
        if (filter.getFatherId() != null) {
            Predicate namePredicate = cb.equal(birth.get("father").get("id"),  filter.getFatherId());
            predicates.add(namePredicate);
        }
        if (filter.getBirthName() != null) {
            Predicate namePredicate = cb.like(birth.get("name"),  "%" + filter.getBirthName() + "%");
            predicates.add(namePredicate);
        }

        if (filter.getWebsiteVisibilityStatus() != null) {
            Predicate namePredicate = cb.equal(birth.get("websiteVisibilityStatus"),  filter.getWebsiteVisibilityStatus());
            predicates.add(namePredicate);
        }

        Predicate deleteDatetimePredicate = cb.isNull(birth.get("deleteDateTime"));
        predicates.add(deleteDatetimePredicate);
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Birth> query = entityManager.createQuery(cq);
        return query.setFirstResult(getFirstResult(filter))
                .setMaxResults(filter.getPageSize().intValue())
                .getResultList();
    }

    private int getFirstResult(BirthFilter filter) {
        return filter.getPageSize().intValue() * (filter.getPage().intValue() - 1);
    }
}
