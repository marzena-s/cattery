package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Repository;
import pl.com.kocielapki.cattery.cattery.data.Customer;
import pl.com.kocielapki.cattery.cattery.data.CustomerFilter;

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
public class CustomerSearch {

    @PersistenceContext
    private EntityManager entityManager;

    List<Customer> findBy(CustomerFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> customer = cq.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstName() != null) {
            Predicate namePredicate = cb.like(customer.get("firstName"),  "%" + filter.getFirstName() + "%");
            predicates.add(namePredicate);
        }
        if (filter.getLastName() != null) {
            Predicate namePredicate = cb.like(customer.get("lastName"),  "%" + filter.getLastName() + "%");
            predicates.add(namePredicate);
        }
        if (filter.getCity() != null) {
            Predicate namePredicate = cb.like(customer.get("city"),  "%" + filter.getCity() + "%");
            predicates.add(namePredicate);
        }

        Predicate deleteDatetimePredicate = cb.isNull(customer.get("deleteDateTime"));
        predicates.add(deleteDatetimePredicate);
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Customer> query = entityManager.createQuery(cq);
        if (filter.getPageSize() != null) {
            return query.setFirstResult(getFirstResult(filter))
                    .setMaxResults(filter.getPageSize().intValue())
                    .getResultList();
        } else {
            return query.getResultList();
        }
    }

    private int getFirstResult(CustomerFilter filter) {
        return filter.getPageSize().intValue() * (filter.getPage().intValue() - 1);
    }
}
