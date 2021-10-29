package pl.com.kocielapki.cattery.cattery.data;

import pl.com.kocielapki.cattery.cattery.api.ContactFormRest;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "contact_form")
public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "e_mail")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "message_topic")
    private String messageTopic;
    private String content;
    @Column(name = "regulation_accepted")
    private Boolean regulationAccepted;
    @Column(name = "is_served")
    private boolean served;
    @Column(name = "contact_datetime")
    private LocalDateTime contactDateTime;
    @Column(name = "delete_datetime")
    private LocalDateTime deleteDateTime;

    public ContactForm() {
    }

    public ContactForm(Boolean served) {
        this.served = served;
    }

    public ContactForm(ContactFormRest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.phoneNumber = request.getPhoneNumber();
        this.messageTopic = request.getMessageTopic();
        this.content = request.getContent();
        this.regulationAccepted = request.getRegulationAccepted();
    }

    public ContactForm(Long id, String name, String email, String phoneNumber, String messageTopic, String content, boolean regulationAccepted, boolean isServed, LocalDateTime contactDateTime, LocalDateTime deleteDateTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.messageTopic = messageTopic;
        this.content = content;
        this.regulationAccepted = regulationAccepted;
        this.served = isServed;
        this.contactDateTime = contactDateTime;
        this.deleteDateTime = deleteDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(String messageTopic) {
        this.messageTopic = messageTopic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRegulationAccepted() {
        return regulationAccepted;
    }

    public void setRegulationAccepted(Boolean regulationAccepted) {
        this.regulationAccepted = regulationAccepted;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public LocalDateTime getContactDateTime() {
        return contactDateTime;
    }

    public void setContactDateTime(LocalDateTime contactDateTime) {
        this.contactDateTime = contactDateTime;
    }

    public LocalDateTime getDeleteDateTime() {
        return deleteDateTime;
    }

    public void setDeleteDateTime(LocalDateTime deleteDateTime) {
        this.deleteDateTime = deleteDateTime;
    }
}
