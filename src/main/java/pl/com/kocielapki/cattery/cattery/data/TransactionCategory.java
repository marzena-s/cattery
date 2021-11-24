package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;

@Entity
@Table(name = "transactions_categories")
public class TransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "type_code")
    private String type;

    public TransactionCategory() {
    }

    public TransactionCategory(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public TransactionCategory(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

}
