package pl.com.kocielapki.cattery.cattery.api;

import pl.com.kocielapki.cattery.cattery.data.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class TransactionRest {
    private Long id;
    private Customer customer;
    private Long customerId;
    private Animal animal;
    private Long animalId;
    private BigDecimal price;
    private BigDecimal reservationPrice;
    private String finalDate;
    private String reservationDate;
    private String status;
    private TransactionCategory transactionCategory;
    private Long transactionCategoryId;
    private TransactionSubcategory transactionSubcategory;
    private Long transactionSubcategoryId;
    private String note;
    private String source;

    public TransactionRest() {
    }

    public TransactionRest(Long id, Long customerId, Long animalId, BigDecimal price, BigDecimal reservationPrice, String finalDate, String reservationDate, String status, String note, String source) {
        this.id = id;
        this.customerId = customerId;
        this.animalId = animalId;
        this.price = price;
        this.reservationPrice = reservationPrice;
        this.finalDate = finalDate;
        this.reservationDate = reservationDate;
        this.status = status;
        this.note = note;
        this.source = source;
    }

    public TransactionRest(Transaction transaction) {
        this.id = transaction.getId();
        this.customer = transaction.getCustomer();
        this.animal = transaction.getAnimal();
        this.price = transaction.getPrice();
        this.reservationPrice = transaction.getReservationPrice();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(transaction.getReservationDate() != null) {
            this.reservationDate = transaction.getReservationDate().format(formatter);
        }
        if(transaction.getFinalDate() != null) {
            this.finalDate = transaction.getFinalDate().format(formatter);
        }
        this.status = transaction.getStatus();
        this.transactionCategory = transaction.getTransactionCategory();
        this.note = transaction.getNote();
    }




    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getReservationPrice() {
        return reservationPrice;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public Long getTransactionCategoryId() {
        return transactionCategoryId;
    }

    public TransactionSubcategory getTransactionSubcategory() {
        return transactionSubcategory;
    }

    public Long getTransactionSubcategoryId() {
        return transactionSubcategoryId;
    }

    public String getNote() {
        return note;
    }

    public String getSource() {
        return source;
    }
}