package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Repository;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.AnimalFilter;

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
public class AnimalSearch {

    @PersistenceContext
    private EntityManager entityManager;

    List<Animal> findBy(AnimalFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Animal> cq = cb.createQuery(Animal.class);

        Root<Animal> animal = cq.from(Animal.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null) {
            Predicate namePredicate = cb.like(animal.get("name"), "%" + filter.getName() + "%");
            predicates.add(namePredicate);
        }
        if (filter.getLineageName() != null) {
            Predicate predicate = cb.like(animal.get("lineageName"), "%" + filter.getLineageName() + "%");
            predicates.add(predicate);
        }
        if (filter.getGender() != null) {
            Predicate predicate = cb.like(animal.get("genderCode"), filter.getGender());
            predicates.add(predicate);
        }
        if (filter.getCatteryStatus() != null) {
            Predicate predicate = cb.like(animal.get("catteryStatusCode"), filter.getCatteryStatus());
            predicates.add(predicate);
        }
        if (filter.getSaleStatus() != null) {
            Predicate predicate = cb.like(animal.get("saleStatusCode"), filter.getSaleStatus());
            predicates.add(predicate);
        }
        if (filter.getWebsiteVisibilityStatus() != null) {
            Predicate predicate = cb.like(animal.get("websiteVisibilityStatus"), filter.getWebsiteVisibilityStatus());
            predicates.add(predicate);
        }
        if (filter.getBirthId() != null) {
            Predicate predicate = cb.equal(animal.get("birth").get("id"), filter.getBirthId());
            predicates.add(predicate);
        }

        Predicate deleteDatetimePredicate = cb.isNull(animal.get("deleteDateTime"));
        predicates.add(deleteDatetimePredicate);
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Animal> query = entityManager.createQuery(cq);
        if (filter.getPageSize() != null) {
            return query.setFirstResult(getFirstResult(filter))
                    .setMaxResults(filter.getPageSize().intValue())
                    .getResultList();
        } else {
            return query.getResultList();
        }
    }

    Long countByBirthId(Long birthId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Animal> animal = cq.from(Animal.class);

        Predicate deleteDatetimePredicate = cb.isNull(animal.get("deleteDateTime"));

        if (birthId != null) {
            cq.select(cb.count(animal));
            cq.where(deleteDatetimePredicate, cb.equal(animal.get("birth").get("id"), birthId));
        }

        return entityManager.createQuery(cq).getSingleResult();
    }

    private int getFirstResult(AnimalFilter filter) {
        return filter.getPageSize().intValue() * (filter.getPage().intValue() - 1);
    }
}
