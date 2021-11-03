package pl.com.kocielapki.cattery.cattery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.api.TransactionRest;
import pl.com.kocielapki.cattery.cattery.data.TransactionFilter;
import pl.com.kocielapki.cattery.cattery.repo.TransactionRepository;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AnimalService animalService;
    private CustomerService customerService;
    private TransactionSearch transactionSearch;
    private TransactionCategoryService transactionCategoryService;

    public TransactionService(TransactionRepository transactionRepository, AnimalService animalService, CustomerService customerService, TransactionSearch transactionSearch, TransactionCategoryService transactionCategoryService) {
        this.transactionRepository = transactionRepository;
        this.animalService = animalService;
        this.customerService = customerService;
        this.transactionSearch = transactionSearch;
        this.transactionCategoryService = transactionCategoryService;
    }

    public Transaction get(Long id) {
        return transactionRepository.findById(id).get();
    }

    public List<Transaction> findBy(TransactionFilter filter) {
        return transactionSearch.findBy(filter);
    }

    public void create(TransactionRest request) {
        validateTransactionData(request.getPrice());
        validateTransactionData(request.getReservationPrice());
        Transaction transaction = new Transaction(request);
        setTransactionStatus(transaction);
        setTransactionCategory(transaction);
        setAnimal(request, transaction);
        transaction.setCustomer(customerService.get(request.getCustomerId()));
        transactionRepository.save(transaction);
    }


    public void update(TransactionRest request) {
        Transaction transactionToUpdate;
        if (SourceUpdateStatus.CANCEL.getValue().equals(request.getSource())) {
            transactionToUpdate = get(request.getId());
            transactionToUpdate.setStatus(TransactionStatus.CANCELLED.getValue());
            Animal animalToUpdate = animalService.get(transactionToUpdate.getAnimal().getId());
            animalToUpdate.setSaleStatusCode(SaleStatus.FREE.getValue());
            animalToUpdate.setCatteryStatusCode(CatteryStatus.FOR_SALE.getValue());
        } else {
            validateTransactionData(request.getPrice());
            validateTransactionData(request.getReservationPrice());
            transactionToUpdate = new Transaction(request);
            setTransactionStatus(transactionToUpdate);
            setTransactionCategory(transactionToUpdate);
            setAnimal(request, transactionToUpdate);
            transactionToUpdate.setCustomer(customerService.get(request.getCustomerId()));
            transactionRepository.save(transactionToUpdate);
        }
        transactionRepository.save(transactionToUpdate);
    }

    private void setAnimal(TransactionRest request, Transaction transactionToUpdate) {
        Animal animal = animalService.get(request.getAnimalId());
        transactionToUpdate.setAnimal(animal);
        setAnimalSaleStatus(transactionToUpdate, animal);
    }

    private void setTransactionCategory(Transaction transaction) {
        transaction.setTransactionCategory(transactionCategoryService.get("Sprzedaż kota"));
    }

    private void setAnimalSaleStatus(Transaction transaction, Animal animal) {
        if(transaction.getFinalDate()!=null || transaction.getPrice()!=null){
            animal.setSaleStatusCode(SaleStatus.SOLD.getValue());
            animal.setCatteryStatusCode(CatteryStatus.SOLD.getValue());
        } else {
            animal.setSaleStatusCode(SaleStatus.RESERVED.getValue());
        }
    }

    private void setTransactionStatus(Transaction transaction) {
        if(transaction.getFinalDate()!=null){
            transaction.setStatus(TransactionStatus.FINISHED.getValue());
        } else {
            transaction.setStatus(TransactionStatus.IN_PROGRESS.getValue());
        }

    }

    public void validateTransactionData(BigDecimal price) {
        if (price != null) {
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Kwota nie może być ujemna");
            } else if (price.compareTo(BigDecimal.valueOf(10000l)) > 10000) {
                throw new IllegalArgumentException("Kwota max 10000");
            }
        }
    }


}
