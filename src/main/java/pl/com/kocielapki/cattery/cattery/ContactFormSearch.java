package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Repository;
import pl.com.kocielapki.cattery.cattery.data.ContactForm;
import pl.com.kocielapki.cattery.cattery.data.ContactFormFilter;

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
public class ContactFormSearch {

    @PersistenceContext
    private EntityManager entityManager;

    List<ContactForm> findBy(ContactFormFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContactForm> cq = cb.createQuery(ContactForm.class);

        Root<ContactForm> contactForm = cq.from(ContactForm.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getName()!=null) {
            Predicate namePredicate = cb.like(contactForm.get("name"), "%" + filter.getName() + "%");
            predicates.add(namePredicate);
        }

        if(filter.getServed()) {
            Predicate servedPredicate = cb.equal(contactForm.get("served"), false);
            predicates.add(servedPredicate);
        }

        Predicate deleteDatetimePredicate=cb.isNull(contactForm.get("deleteDateTime"));
        predicates.add(deleteDatetimePredicate);
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<ContactForm> query = entityManager.createQuery(cq);
        return query.setFirstResult(getFirstResult(filter))
                .setMaxResults(filter.getPageSize().intValue())
                .getResultList();
    }

    private int getFirstResult(ContactFormFilter filter) {
        return filter.getPageSize().intValue() * (filter.getPage().intValue() - 1);
    }

}
