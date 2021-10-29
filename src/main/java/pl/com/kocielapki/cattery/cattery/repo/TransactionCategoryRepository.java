package pl.com.kocielapki.cattery.cattery.repo;

import org.springframework.data.repository.CrudRepository;
import pl.com.kocielapki.cattery.cattery.data.TransactionCategory;

public interface TransactionCategoryRepository extends CrudRepository<TransactionCategory, Long>
{
    TransactionCategory findByName(String name);

}
