package pl.com.kocielapki.cattery.cattery.data;

import pl.com.kocielapki.cattery.cattery.api.TransactionRest;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    private BigDecimal price;
    @Column(name = "reservation_price")
    private BigDecimal reservationPrice;
    @Column(name = "final_date")
    private LocalDate finalDate;
    @Column(name = "reservation_date")
    private LocalDate reservationDate;
    @Column(name = "status_code")
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_category_id", referencedColumnName = "id")
    private TransactionCategory transactionCategory;
    @ManyToOne
    @JoinColumn(name = "transaction_subcategory_id")
    private TransactionSubcategory transactionSubcategory;
    private String note;

    public Transaction() {
    }

    public Transaction(Long id, Customer customer, Animal animal, BigDecimal price, BigDecimal reservationPrice, LocalDate finalDate, LocalDate reservationDate, String status, TransactionCategory transactionCategory, TransactionSubcategory transactionSubcategory, String note) {
        this.id = id;
        this.customer = customer;
        this.animal = animal;
        this.price = price;
        this.reservationPrice = reservationPrice;
        this.finalDate = finalDate;
        this.reservationDate = reservationDate;
        this.status = status;
        this.transactionCategory = transactionCategory;
        this.transactionSubcategory = transactionSubcategory;
        this.note = note;
    }

    public Transaction(TransactionRest request) {
        this.status = request.getStatus();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
        if (!request.getReservationDate().isEmpty() && request.getReservationDate() != null) {
            this.reservationDate = LocalDate.parse(request.getReservationDate(), formatter);
        }
        if (!request.getFinalDate().isEmpty() && request.getFinalDate() != null) {
            this.finalDate = LocalDate.parse(request.getFinalDate(), formatter);
        }
        this.note = request.getNote();
        this.price =request.getPrice();
        this.reservationPrice =request.getReservationPrice();
        TransactionCategory category = new TransactionCategory("Sprzedaż kota", "i");
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Animal getAnimal() {
        return animal;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getReservationPrice() {
        return reservationPrice;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public TransactionSubcategory getTransactionSubcategory() {
        return transactionSubcategory;
    }

    public String getNote() {
        return note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setReservationPrice(BigDecimal reservationPrice) {
        this.reservationPrice = reservationPrice;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public void setTransactionSubcategory(TransactionSubcategory transactionSubcategory) {
        this.transactionSubcategory = transactionSubcategory;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
