package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.api.CustomerRest;
import pl.com.kocielapki.cattery.cattery.data.Customer;
import pl.com.kocielapki.cattery.cattery.data.CustomerFilter;
import pl.com.kocielapki.cattery.cattery.logic.utils.EmailUtil;
import pl.com.kocielapki.cattery.cattery.repo.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerService {
   private CustomerRepository customerRepository;
   private TransactionService transactionService;
   private CustomerSearch customerSearch;

    public CustomerService(CustomerRepository customerRepository, @Lazy TransactionService transactionService, CustomerSearch customerSearch) {
        this.customerRepository = customerRepository;
        this.transactionService = transactionService;
        this.customerSearch = customerSearch;
    }

    public Customer get(Long id){
        return customerRepository.findById(id).get();
    }

    public void create(CustomerRest request) {
        validateCustomerData(request);
        Customer customer = new Customer(request);
        customerRepository.save(customer);
    }

    public List<Customer> findBy(CustomerFilter filter) {
        return customerSearch.findBy(filter);
    }

    public void update(CustomerRest request) {
        Customer customerToUpdate;
        if(SourceUpdateStatus.DELETE.getValue().equals(request.getSource())){
            customerToUpdate = get(request.getId());
            if(transactionService.checkIfCustomerExistsAndStatus(customerToUpdate, TransactionStatus.CANCELLED.getValue())){
                throw new IllegalArgumentException("Nie można usunąć, zacznij od anulacji transakcji");
            }
            customerToUpdate.setDeleteDateTime(LocalDateTime.now());
        } else {
            validateCustomerData(request);
            customerToUpdate = new Customer(request);
        }
        customerRepository.save(customerToUpdate);
    }

    private void validateCustomerData(CustomerRest request) {
        validateName(request.getFirstName(), "Imię");
        validateName(request.getLastName(), "Nazwisko");
        if(request.getEmail() != null && !request.getEmail().equals("")) {
            validateEmail(request.getEmail());
        }
        validatePhoneNumber(request.getPhoneNumber());
    }

    public void validateName(String name, String nameType){
        if(name == null || name.equals("")){
            throw new IllegalArgumentException(nameType+ " nie może być puste");
        }

        validateMaxLength(name, 100L, nameType);
    }

    private void validateEmail(String email) {
        EmailUtil.validateEmail(email);

        validateMaxLength(email, 100L, "E-mail");

    }

    private void validateMaxLength(String data, Long maxLength, String dataName) {
        if (data.length() > maxLength) {
            throw new IllegalArgumentException(dataName + " zawiera zbyt wiele znaków, max " + maxLength + " znaków");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() >= 1) {
            validateMaxLength(phoneNumber, 100L, "Numer telefonu");
            if (!phoneNumber.matches("^\\+?\\d+(( |-)\\d+)*")) {
                throw new IllegalArgumentException("Numer telefonu może zawierać tylko cyfry, znaki '+' i '-' oraz spacje");
            }
            if (phoneNumber.length() < 6) {
                throw new IllegalArgumentException("Numer telefonu za krótki");
            }
        }
    }


}
