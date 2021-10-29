package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @Column(name = "password_hash")
    private String passwordHash;
    private String language;
    @Column(name = "first_and_last_name")
    private String firstAndLastName;
    @Column(name = "is_enabled")
    private Boolean isEnabled;
    @Column(name = "registration_datetime")
    private LocalDateTime registrationDateTime;
    @Column(name = "delete_datetime")
    private LocalDateTime deleteDateTime;

    public User() {
    }

    public User(Long id, String login, String passwordHash, String language, String firstAndLastName, Boolean isEnabled, LocalDateTime registrationDateTime, LocalDateTime deleteDateTime) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.firstAndLastName = firstAndLastName;
        this.isEnabled = isEnabled;
        this.registrationDateTime = registrationDateTime;
        this.deleteDateTime = deleteDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFirstAndLastName() {
        return firstAndLastName;
    }

    public void setFirstAndLastName(String firstAndLastName) {
        this.firstAndLastName = firstAndLastName;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    public LocalDateTime getDeleteDateTime() {
        return deleteDateTime;
    }

    public void setDeleteDateTime(LocalDateTime deleteDateTime) {
        this.deleteDateTime = deleteDateTime;
    }
}