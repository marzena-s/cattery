package pl.com.kocielapki.cattery.cattery.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.com.kocielapki.cattery.logic.CacheService;
import pl.com.kocielapki.cattery.logic.CacheType;
import pl.com.kocielapki.cattery.logic.DictionariesService;
import pl.com.kocielapki.cattery.logic.DictionaryType;
import pl.com.kocielapki.cattery.logic.utils.LanguagesUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ApplicationController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
    private DictionariesService dictionariesService;
    private CacheService cacheService;


    public ApplicationController(DictionariesService dictionariesService) {
        this.dictionariesService = dictionariesService;
    }



    @GetMapping({"/", "/start"})
    public String home(Model model) {
        return "cattery_templates/home";
    }

    @GetMapping({"/rodo"})
    public String rodo() {
        return "cattery_templates/rodo";
    }

    @GetMapping({"/regulamin"})
    public String regulation() {
        return "cattery_templates/regulation";
    }

    @GetMapping({"/polityka_prywatnosci"})
    public String privacyPolicy() {
        return "cattery_templates/privacy-policy";
    }

    @GetMapping({"/opinie"})
    public String opinions() { return "cattery_templates/opinions"; }

    @GetMapping({"/kontakt"})
    public String contact() {
        return"cattery_templates/contact";
    }

    @GetMapping({"/kukuruku"})
    public String loginAdmin() {
        return"cattery_templates/login";
    }

    @GetMapping({"/logout"})
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("logout", true);
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        cacheService.invalidateDictionaries();
        log.debug(CacheType.DICTIONARIES + " cache invalidated");
        cacheService.invalidateMenu();
        log.debug(CacheType.MENU + " cache invalidated");
        return "cattery_templates/login";
    }

    @GetMapping({"/o_hodowli"})
    public String aboutCattery() {
        return"cattery_templates/about_cattery";
    }

    @GetMapping({"/nasze_koty"})
    public String ourAnimals() {
        return"cattery_templates/our_animals";
    }

    @GetMapping({"/kocieta"})
    public String kittens() {
        return"cattery_templates/kittens";
    }

    @GetMapping({"/opieka"})
    public String careForCats() {
        return"cattery_templates/care_for_cats";
    }

    @GetMapping({"/admin"})
    public String admin() {
        return"cattery_templates/admin";
    }

    @GetMapping({"/admin/contact-form"})
    public String contactFormAdmin(Model model) {
        model.addAttribute("yesNoDict", dictionariesService.getDictionary(DictionaryType.YES_NO, LanguagesUtil.getCurrentLanguage()));
        return"cattery_templates/contact_form_admin";
    }

    @GetMapping({"/admin/animals"})
    public String animalsAdmin(Model model) {
        model.addAttribute("genderDict", dictionariesService.getDictionary(DictionaryType.GENDER, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("catteryStatusCodeDict", dictionariesService.getDictionary(DictionaryType.CATTERY_STATUSES,LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("saleStatusDict", dictionariesService.getDictionary(DictionaryType.SALE_STATUSES, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("websiteVisibilityStatusDict", dictionariesService.getDictionary(DictionaryType.WEBSITE_VISIBILITY_STATUSES, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("speciesDict", dictionariesService.getDictionary(DictionaryType.SPECIES, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("breedsDict", dictionariesService.getDictionary(DictionaryType.BREEDS, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("birthsDict", dictionariesService.getDictionary(DictionaryType.BIRTHS, LanguagesUtil.getCurrentLanguage()));

        return"cattery_templates/animals_admin";
    }

    @GetMapping({"/admin/births"})
    public String birthsAdmin(Model model) {
        model.addAttribute("catteryStatusCodeDict", dictionariesService.getDictionary(DictionaryType.CATTERY_STATUSES,LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("websiteVisibilityStatusDict", dictionariesService.getDictionary(DictionaryType.WEBSITE_VISIBILITY_STATUSES, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("mothersDict", dictionariesService.getDictionary(DictionaryType.ANIMAL_MOTHER, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("fathersDict", dictionariesService.getDictionary(DictionaryType.ANIMAL_FATHER, LanguagesUtil.getCurrentLanguage()));

        return"cattery_templates/births_admin";
    }

    @GetMapping({"/admin/customers"})
    public String customersAdmin(Model model) {
        model.addAttribute("countriesDict", dictionariesService.getDictionary(DictionaryType.COUNTRIES,LanguagesUtil.getCurrentLanguage()));

        return"cattery_templates/customers_admin";
    }

    @GetMapping({"/admin/sales"})
    public String salesAdmin(Model model) {
        model.addAttribute("saleStatusDict", dictionariesService.getDictionary(DictionaryType.SALE_STATUSES, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("transactionStatusDict", dictionariesService.getDictionary(DictionaryType.TRANSACTION_STATUSES, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("animalsDict", dictionariesService.getDictionary(DictionaryType.ANIMAL_MOTHER, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("customersDict", dictionariesService.getDictionary(DictionaryType.ANIMAL_FATHER, LanguagesUtil.getCurrentLanguage()));
        return"cattery_templates/transactions_admin";
    }

    @GetMapping({"/overview"})
    public String presentation() {
        return"cattery_templates/application_overview";
    }

}
