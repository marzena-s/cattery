package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.ContactForm;
import pl.com.kocielapki.cattery.cattery.data.ContactFormFilter;
import pl.com.kocielapki.cattery.cattery.logic.utils.EmailUtil;
import pl.com.kocielapki.cattery.cattery.repo.ContactFormRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ContactFormService {
    private ContactFormRepository contactFormRepository;
    private ContactFormSearch contactFormSearch;

    public ContactFormService(ContactFormRepository contactFormRepository, ContactFormSearch contactFormSearch) {
        this.contactFormRepository = contactFormRepository;
        this.contactFormSearch = contactFormSearch;
    }

    public ContactForm get(Long id) {
        return contactFormRepository.findById(id).get();
    }

    public void create(ContactForm contactForm) {
        validate(contactForm);
        contactForm.setContactDateTime(LocalDateTime.now());
        contactForm.setServed(false);
        contactFormRepository.save(contactForm);
    }

    public List<ContactForm> findBy(ContactFormFilter filter) {
        return contactFormSearch.findBy(filter);
    }


    public void delete(Long id) {
        ContactForm contactToUpdate = contactFormRepository.findById(id).get();
        contactToUpdate.setDeleteDateTime(LocalDateTime.now());
        contactFormRepository.save(contactToUpdate);
    }

    public void update(Long id, ContactForm contactForm) {
        ContactForm contactToUpdate = contactFormRepository.findById(id).get();
        contactToUpdate.setServed(contactForm.isServed());
        contactFormRepository.save(contactToUpdate);
    }


    private void validate(ContactForm data) {
        validateEmail(data.getEmail());
        validateRegulationAccept(data.getRegulationAccepted());
        validateData(data.getName(), "Imię lub nick", 100L);
        validateData(data.getMessageTopic(), "Temat wiadomości", 1000L);
        validateData(data.getContent(), "Treść", 10000L);
        validatePhoneNumber(data.getPhoneNumber());
    }

    private void validateEmail(String email) {
        EmailUtil.validateEmail(email);

        validateMaxLength(email, 100L, "E-mail");

    }

    private void validateData(String data, String dataName, Long maxLength) {
        validateMaxLength(data, maxLength, dataName);
        validateText(data, dataName);
    }

    private void validateText(String data, String dataName) {
        if (data == null || !data.matches(".*[a-zA-Z]+.*")) {
            throw new IllegalArgumentException(dataName + " musi zawierać tekst");
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

    private void validateMaxLength(String data, Long maxLength, String dataName) {
        if (data.length() > maxLength) {
            throw new IllegalArgumentException(dataName + " zawiera zbyt wiele znaków, max " + maxLength + " znaków");
        }
    }

    private void validateRegulationAccept(Boolean regulationAccepted) {
        if (regulationAccepted == null || !regulationAccepted) {
            throw new IllegalArgumentException("Nie zaakceptowano zgody");
        }
    }

}
