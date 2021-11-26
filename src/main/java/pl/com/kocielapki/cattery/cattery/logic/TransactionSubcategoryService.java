package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.TransactionSubcategory;
import pl.com.kocielapki.cattery.cattery.repo.TransactionSubcategoryRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class TransactionSubcategoryService {
   private TransactionSubcategoryRepository transactionSubcategoryRepository;

    public TransactionSubcategoryService(TransactionSubcategoryRepository transactionSubcategoryRepository) {
        this.transactionSubcategoryRepository = transactionSubcategoryRepository;
    }

    public TransactionSubcategory get(Long id){
        return transactionSubcategoryRepository.findById(id).get();
    }

    public List<TransactionSubcategory> findAll() {
        Iterable<TransactionSubcategory> transactionSubcategories = transactionSubcategoryRepository.findAll();
        return StreamSupport.stream(transactionSubcategories.spliterator(), false).collect(Collectors.toList());
    }
}
