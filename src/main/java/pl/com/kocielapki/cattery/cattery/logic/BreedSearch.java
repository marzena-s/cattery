package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Repository;
import pl.com.kocielapki.cattery.cattery.data.Breed;
import pl.com.kocielapki.cattery.cattery.data.BreedFilter;

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
public class BreedSearch {

    @PersistenceContext
    private EntityManager entityManager;

    List<Breed> findBy(BreedFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Breed> cq = cb.createQuery(Breed.class);

        Root<Breed> breed = cq.from(Breed.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getSpeciesId()!=null) {
//            Predicate namePredicate = cb.like(breed.get("speciesId"), filter.getSpeciesId());
            Predicate namePredicate = cb.equal(breed.get("id"), filter.getSpeciesId());
            predicates.add(namePredicate);
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Breed> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
