package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;

@Entity
@Table(name = "transactions_subcategories")
public class TransactionSubcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "transaction_category_id")
    private TransactionCategory transactionCategory;
    private String name;


    public TransactionSubcategory() {
    }

    public TransactionSubcategory(Long id, TransactionCategory transactionCategory, String name) {
        this.id = id;
        this.transactionCategory = transactionCategory;
        this.name = name;
    }

    public TransactionSubcategory(TransactionCategory transactionCategory, String name) {
        this.transactionCategory = transactionCategory;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public void setName(String name) {
        this.name = name;
    }
}
