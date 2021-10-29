package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Repository;
import pl.com.kocielapki.cattery.cattery.data.Transaction;
import pl.com.kocielapki.cattery.cattery.data.TransactionFilter;

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
public class TransactionSearch {

    @PersistenceContext
    private EntityManager entityManager;

    List<Transaction> findBy(TransactionFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);

        Root<Transaction> transaction = cq.from(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getCustomerFirstName() != null) {
            Predicate predicate = cb.like(transaction.get("customer").get("firstName"),  "%" + filter.getCustomerFirstName() + "%");
            predicates.add(predicate);
        }
        if (filter.getCustomerLastName() != null) {
            Predicate predicate = cb.like(transaction.get("customer").get("lastName"),  "%" + filter.getCustomerLastName() + "%");
            predicates.add(predicate);
        }
        if (filter.getAnimalName() != null) {
            Predicate predicate = cb.like(transaction.get("animal").get("name"),  "%" + filter.getAnimalName() + "%");
            predicates.add(predicate);
        }
        if (filter.getAnimalLineageName() != null) {
            Predicate predicate = cb.like(transaction.get("animal").get("lineageName"),  "%" + filter.getAnimalLineageName() + "%");
            predicates.add(predicate);
        }
        if (filter.getAnimalSaleStatus() != null) {
            Predicate predicate = cb.like(transaction.get("animal").get("saleStatusCode"), filter.getAnimalSaleStatus());
            predicates.add(predicate);
        }
        if (filter.getTransactionStatus() != null) {
            Predicate predicate = cb.like(transaction.get("status"), filter.getTransactionStatus());
            predicates.add(predicate);
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Transaction> query = entityManager.createQuery(cq);
        return query.setFirstResult(getFirstResult(filter))
                .setMaxResults(filter.getPageSize().intValue())
                .getResultList();
    }

    private int getFirstResult(TransactionFilter filter) {
        return filter.getPageSize().intValue() * (filter.getPage().intValue() - 1);
    }
}
