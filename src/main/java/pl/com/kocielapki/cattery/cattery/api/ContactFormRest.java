package pl.com.kocielapki.cattery.cattery.api;

import pl.com.kocielapki.cattery.cattery.data.ContactForm;

import java.time.format.DateTimeFormatter;

public class ContactFormRest {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String messageTopic;
    private String content;
    private Boolean regulationAccepted;
    private Boolean served;
    private String contactDateTime;


    public ContactFormRest() {
    }

    public ContactFormRest(ContactForm contactForm) {
        this.id = contactForm.getId();
        this.name = contactForm.getName();
        this.email = contactForm.getEmail();
        this.phoneNumber = contactForm.getPhoneNumber();
        this.messageTopic = contactForm.getMessageTopic();
        this.content = contactForm.getContent();
        this.regulationAccepted = contactForm.getRegulationAccepted();
        this.served = contactForm.isServed();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
        this.contactDateTime = contactForm.getContactDateTime().format(dateTimeFormatter);
    }


    public ContactFormRest(String name, String email, String phoneNumber, String messageTopic, String content, boolean regulationAccepted, String contactDateTime) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.messageTopic = messageTopic;
        this.content = content;
        this.regulationAccepted = regulationAccepted;
        this.contactDateTime = contactDateTime;
    }

    public Long getId() {
        return id;
    }

    public Boolean getServed() {
        return served;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessageTopic() {
        return messageTopic;
    }

    public String getContent() {
        return content;
    }

    public Boolean getRegulationAccepted() {
        return regulationAccepted;
    }

    public String getContactDateTime() {
        return contactDateTime;
    }
}
