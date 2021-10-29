package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.TransactionCategory;
import pl.com.kocielapki.cattery.cattery.repo.TransactionCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class TransactionCategoryService {
    private TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    public TransactionCategory get(Long id) {
        return transactionCategoryRepository.findById(id).get();
    }

    public TransactionCategory get(String name) {
        return transactionCategoryRepository.findByName(name);
    }

    public List<TransactionCategory> findAll() {
        Iterable<TransactionCategory> transactionCategories = transactionCategoryRepository.findAll();
        return StreamSupport.stream(transactionCategories.spliterator(), false).collect(Collectors.toList());
    }


}
