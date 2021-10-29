package pl.com.kocielapki.cattery.cattery.data;

public class TransactionFilter {
    private Long id;
    private String customerFirstName;
    private String customerLastName;
    private String animalName;
    private String animalLineageName;
    private String animalSaleStatus;
    private String transactionStatus;
    private Long page;
    private Long pageSize;
    private Customer customer;
    private Animal animal;
    private TransactionCategory category;

    public TransactionFilter(String customerFirstName, String customerLastName, String animalName, String animalLineageName, String animalSaleStatus, String transactionStatus, Long page, Long pageSize, TransactionCategory category) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.animalName = animalName;
        this.animalLineageName = animalLineageName;
        this.animalSaleStatus = animalSaleStatus;
        this.transactionStatus = transactionStatus;
        this.page = page;
        this.pageSize = pageSize;
        this.category=category;
    }

    public TransactionFilter(String transactionStatus, Long page, Long pageSize, Customer customer, Animal animal) {
        this.transactionStatus = transactionStatus;
        this.page = page;
        this.pageSize = pageSize;
        this.customer = customer;
        this.animal = animal;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getAnimalLineageName() {
        return animalLineageName;
    }

    public String getAnimalSaleStatus() {
        return animalSaleStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Animal getAnimal() {
        return animal;
    }

    public TransactionCategory getCategory() {
        return category;
    }
}
