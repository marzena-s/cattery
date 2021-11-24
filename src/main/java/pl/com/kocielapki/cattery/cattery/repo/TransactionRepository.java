package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.Customer;
import pl.com.kocielapki.cattery.cattery.data.Transaction;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long>
{
    List<Transaction> findByCustomerAndStatusIsNot(Customer customer, String status);
}
