package pl.com.kocielapki.cattery.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.com.kocielapki.cattery.cattery.logic.ApplicationConfigService;
import pl.com.kocielapki.cattery.cattery.logic.Language;
import pl.com.kocielapki.cattery.cattery.logic.utils.LanguagesUtil;

import java.util.*;

@Service
public class AuthenticatedUser {
    private ApplicationConfigService applicationConfigService;

    public AuthenticatedUser(ApplicationConfigService applicationConfigService) {
        this.applicationConfigService = applicationConfigService;
    }

    public String getAppVersion() {
        return applicationConfigService.getVersion();
    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }


    public List<Menu> getMenu() {

        Language currentLang = LanguagesUtil.getCurrentLanguage();

            return new ArrayList<>(Arrays.asList(
                    new Menu(chooseMenuName("Contact form", "Formularz kontaktowy", currentLang), "/admin/contact-form"),
                    new Menu(chooseMenuName("Animals", "Zwierzęta", currentLang), "#", new ArrayList<>(Arrays.asList(
                            new Menu(chooseMenuName("All", "Wszystkie", currentLang), "/admin/animals", Collections.emptyList()),
                            new Menu(chooseMenuName("Births", "Mioty", currentLang), "/admin/births", Collections.emptyList())

                    ))),
                    new Menu(chooseMenuName("Customers", "Klienci", currentLang), "/admin/customers"),
                    new Menu(chooseMenuName("Sales", "Ewidencja sprzedaży", currentLang), "/admin/sales")));

    }

    private String chooseMenuName(String englishName, String polishName, Language lang) {

        if (lang == Language.PL) {
            return polishName;
        } else {
            return englishName;
        }

    }
}

