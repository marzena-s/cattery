package pl.com.kocielapki.cattery.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.domain.DictionaryData;
import pl.com.kocielapki.cattery.cattery.*;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.Birth;
import pl.com.kocielapki.cattery.cattery.data.Breed;
import pl.com.kocielapki.cattery.cattery.data.Species;
import pl.com.kocielapki.cattery.cattery.repo.*;
import pl.com.kocielapki.cattery.logic.utils.LanguagesUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class DictionariesService {
    private static final Logger log = LoggerFactory.getLogger(DictionariesService.class);

    private SpeciesRepository speciesRepository;
    private AnimalRepository animalRepository;
    private BreedRepository breedRepository;
    private BirthRepository birthRepository;
    private CustomerRepository customerRepository;

    public DictionariesService(SpeciesRepository speciesRepository, AnimalRepository animalRepository, BreedRepository breedRepository, BirthRepository birthRepository, CustomerRepository customerRepository) {
        this.speciesRepository = speciesRepository;
        this.animalRepository = animalRepository;
        this.breedRepository = breedRepository;
        this.birthRepository = birthRepository;
        this.customerRepository = customerRepository;
    }

    public List<DictionaryData> getDictionary(DictionaryType type) {
        return getDictionary(type, LanguagesUtil.getCurrentLanguage());
    }

    public List<DictionaryData> getDictionary(DictionaryType type, Locale locale) {
        return getDictionary(type, LanguagesUtil.prepareLanguageFrom(locale));
    }

    @Cacheable(CacheType.DICTIONARIES)
    public List<DictionaryData> getDictionary(DictionaryType type, pl.com.kocielapki.cattery.logic.Language lang) {
        if (DictionaryType.YES_NO == type) {
            return getYesNo(lang);
        } else if (DictionaryType.GENDER == type) {
            return getGender(lang);
        } else if (DictionaryType.CATTERY_STATUSES == type) {
            return getCatteryStatuses(lang);
        } else if (DictionaryType.WEBSITE_VISIBILITY_STATUSES == type) {
            return getWebsiteVisibilityStatuses(lang);
        } else if (DictionaryType.TRANSACTION_STATUSES == type) {
            return getTransactionStatuses(lang);
        } else if (DictionaryType.SPECIES == type) {
            return getSpecies(lang);
        } else if (DictionaryType.BREEDS == type) {
            return getBreeds(lang);
        } else if (DictionaryType.BIRTHS == type) {
            return getBirths(lang);
        } else if (DictionaryType.ANIMAL_MOTHER == type) {
            return getAnimalParents(lang, DictionaryType.ANIMAL_MOTHER);
        } else if (DictionaryType.ANIMAL_FATHER == type) {
            return getAnimalParents(lang, DictionaryType.ANIMAL_FATHER);
        } else if (DictionaryType.ANIMALS_TRANSACTION == type) {
            return getAnimalParents(lang, DictionaryType.ANIMAL_FATHER);
        }else if (DictionaryType.SALE_STATUSES == type) {
            return getSaleStatuses(lang);
        } else if (DictionaryType.LANGUAGES == type) {
            return getLanguages(lang);
        } else if (DictionaryType.COUNTRIES == type) {
            return getCountries(lang);
        }
        throw new IllegalArgumentException("Dictionary no defined: " + type + " for language: " + lang);
    }

    private DictionaryData getDictionaryElementByValue(String value, DictionaryType type, pl.com.kocielapki.cattery.logic.Language lang) {
        try {
            return getDictionary(type, lang).stream().filter(status -> Objects.equals(status.getValue().toUpperCase(), value.toUpperCase())).findFirst().get();
        } catch (NoSuchElementException ex) {
            log.debug("No value for: value: " + value + " dict: " + type.name() + " lang: " + lang.name());
            throw ex;
        }
    }

    private List<DictionaryData> getYesNo(pl.com.kocielapki.cattery.logic.Language lang) {
        return pl.com.kocielapki.cattery.logic.Language.PL == lang ?
                Arrays.asList(new DictionaryData("true", "Tak", lang.getCode()), new DictionaryData("false", "Nie", lang.getCode())) :
                Arrays.asList(new DictionaryData("true", "Yes", lang.getCode()), new DictionaryData("false", "No", lang.getCode()));
    }

    private List<DictionaryData> getGender(pl.com.kocielapki.cattery.logic.Language lang) {
        return pl.com.kocielapki.cattery.logic.Language.PL == lang ?
                Arrays.asList(new DictionaryData(Gender.MALE.getValue(), "Mężczyzna", lang.getCode()), new DictionaryData(Gender.FEMALE.getValue(), "Kobieta", lang.getCode()), new DictionaryData(Gender.UNKNOWN.getValue(), "Nieznana", lang.getCode())) :
                Arrays.asList(new DictionaryData(Gender.MALE.getValue(), "Male", lang.getCode()), new DictionaryData(Gender.FEMALE.getValue(), "Female", lang.getCode()), new DictionaryData(Gender.UNKNOWN.getValue(), "Unknown", lang.getCode()));
    }


    private List<DictionaryData> getCatteryStatuses(pl.com.kocielapki.cattery.logic.Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == pl.com.kocielapki.cattery.logic.Language.PL) {
            list.add(new DictionaryData(CatteryStatus.CATTERY.getValue(), "Hodowla", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.FOR_SALE.getValue(), "Na sprzedaż", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.SOLD.getValue(), "Sprzedany", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.DIED.getValue(), "Zmarł", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.GIVEN.getValue(), "Oddany", lang.getCode()));
        } else {
            list.add(new DictionaryData(CatteryStatus.CATTERY.getValue(), "Cattery", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.FOR_SALE.getValue(), "For sale", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.SOLD.getValue(), "Sold", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.DIED.getValue(), "Died", lang.getCode()));
            list.add(new DictionaryData(CatteryStatus.GIVEN.getValue(), "Given", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getWebsiteVisibilityStatuses(pl.com.kocielapki.cattery.logic.Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == pl.com.kocielapki.cattery.logic.Language.PL) {
            list.add(new DictionaryData(WebsiteVisibilityStatus.INVISIBLE.getValue(), "Niewidoczny", lang.getCode()));
            list.add(new DictionaryData(WebsiteVisibilityStatus.VISIBLE.getValue(), "Widoczny", lang.getCode()));
            list.add(new DictionaryData(WebsiteVisibilityStatus.NONE.getValue(), "Nie dotyczy", lang.getCode()));
        } else {
            list.add(new DictionaryData(WebsiteVisibilityStatus.INVISIBLE.getValue(), "Invisible", lang.getCode()));
            list.add(new DictionaryData(WebsiteVisibilityStatus.VISIBLE.getValue(), "Visible", lang.getCode()));
            list.add(new DictionaryData(WebsiteVisibilityStatus.NONE.getValue(), "None", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getTransactionStatuses(pl.com.kocielapki.cattery.logic.Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == pl.com.kocielapki.cattery.logic.Language.PL) {
            list.add(new DictionaryData(TransactionStatus.IN_PROGRESS.getValue(), "W trakcie realizacji", lang.getCode()));
            list.add(new DictionaryData(TransactionStatus.CANCELLED.getValue(), "Anulowana", lang.getCode()));
            list.add(new DictionaryData(TransactionStatus.FINISHED.getValue(), "Zakończona", lang.getCode()));
        } else {
            list.add(new DictionaryData(TransactionStatus.IN_PROGRESS.getValue(), "In progress", lang.getCode()));
            list.add(new DictionaryData(TransactionStatus.CANCELLED.getValue(), "Cancelled", lang.getCode()));
            list.add(new DictionaryData(TransactionStatus.FINISHED.getValue(), "Finished", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getSaleStatuses(pl.com.kocielapki.cattery.logic.Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == pl.com.kocielapki.cattery.logic.Language.PL) {
            list.add(new DictionaryData(SaleStatus.FREE.getValue(), "Wolny", lang.getCode()));
            list.add(new DictionaryData(SaleStatus.RESERVED.getValue(), "Zarezerwowany", lang.getCode()));
            list.add(new DictionaryData(SaleStatus.SOLD.getValue(), "Sprzedany", lang.getCode()));
            list.add(new DictionaryData(SaleStatus.NONE.getValue(), "Nie dotyczy", lang.getCode()));
        } else {
            list.add(new DictionaryData(SaleStatus.FREE.getValue(), "Free", lang.getCode()));
            list.add(new DictionaryData(SaleStatus.RESERVED.getValue(), "Reserved", lang.getCode()));
            list.add(new DictionaryData(SaleStatus.SOLD.getValue(), "Sold", lang.getCode()));
            list.add(new DictionaryData(SaleStatus.NONE.getValue(), "None", lang.getCode()));
        }
        return list;
    }


    private List<DictionaryData> getSpecies(pl.com.kocielapki.cattery.logic.Language lang) {
        Iterable<Species> species = speciesRepository.findAll();
        StreamSupport.stream(species.spliterator(), false).collect(Collectors.toList());

        List<DictionaryData> data = getDictionaryDataSpecies(lang, species);

        return data;
    }

    private List<DictionaryData> getBreeds(pl.com.kocielapki.cattery.logic.Language lang) {
        Iterable<Breed> breeds = breedRepository.findAll();
        StreamSupport.stream(breeds.spliterator(), false).collect(Collectors.toList());

        List<DictionaryData> data = getDictionaryDataBreeds(lang, breeds);

        return data;
    }

    private List<DictionaryData> getBirths(pl.com.kocielapki.cattery.logic.Language lang) {
        Iterable<Birth> births = birthRepository.findAll();
        StreamSupport.stream(births.spliterator(), false).collect(Collectors.toList());

        List<DictionaryData> data = getDictionaryDataBirths(lang, births);

        return data;
    }

    private List<DictionaryData> getAnimalParents(pl.com.kocielapki.cattery.logic.Language lang, DictionaryType type) {
        String status = CatteryStatus.CATTERY.getValue();
        String gender = null;
        if (type == DictionaryType.ANIMAL_FATHER) {
            gender = Gender.MALE.getValue();
        } else if (type == DictionaryType.ANIMAL_MOTHER) {
            gender = Gender.FEMALE.getValue();
        }
        Iterable<Animal> animals = animalRepository.findByDeleteDateTimeIsNullAndCatteryStatusCodeAndGenderCode(status, gender);
        StreamSupport.stream(animals.spliterator(), false).collect(Collectors.toList());

        List<DictionaryData> data = getDictionaryDataWithAnimals(lang, animals);

        return data;
    }

    private List<DictionaryData> getDictionaryDataSpecies(pl.com.kocielapki.cattery.logic.Language lang, Iterable<Species> species) {
        List<DictionaryData> data = new ArrayList<>();
        for (Species speciesElement : species) {
            data.add(new DictionaryData(speciesElement.getId(), speciesElement.getName(), lang.getCode()));
        }
        return data;
    }

    private List<DictionaryData> getDictionaryDataBreeds(pl.com.kocielapki.cattery.logic.Language lang, Iterable<Breed> breeds) {
        List<DictionaryData> data = new ArrayList<>();
        for (Breed breed : breeds) {
            data.add(new DictionaryData(breed.getId(), breed.getName(), lang.getCode()));
        }
        return data;
    }

    private List<DictionaryData> getDictionaryDataBirths(pl.com.kocielapki.cattery.logic.Language lang, Iterable<Birth> births) {
        List<DictionaryData> data = new ArrayList<>();
        for (Birth birth : births) {
            data.add(new DictionaryData(birth.getId(), birth.getName(), lang.getCode()));
        }
        return data;
    }

    private List<DictionaryData> getDictionaryDataWithAnimals(pl.com.kocielapki.cattery.logic.Language lang, Iterable<Animal> animals) {
        List<DictionaryData> data = new ArrayList<>();
        for (Animal animal : animals) {
            data.add(new DictionaryData(animal.getId(), animal.getName(), lang.getCode()));
        }
        return data;
    }

    private List<DictionaryData> getLanguages(pl.com.kocielapki.cattery.logic.Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (pl.com.kocielapki.cattery.logic.Language.US == lang || pl.com.kocielapki.cattery.logic.Language.EN == lang) {
            list.add(new DictionaryData("en", "English", lang.getCode()));
            list.add(new DictionaryData("pl", "Polish", lang.getCode()));
        } else if (pl.com.kocielapki.cattery.logic.Language.PL == lang) {
            list.add(new DictionaryData("en", "Angielski", lang.getCode()));
            list.add(new DictionaryData("pl", "Polski", lang.getCode()));
        }

        return list;
    }


    private List<DictionaryData> getCountries(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            String code = locale.getCountry().toLowerCase();
            String value = locale.getDisplayCountry(Locale.forLanguageTag(lang.toString()));
            list.add(new DictionaryData(code, value, lang.getCode()));
        }
        return list;
    }
}
