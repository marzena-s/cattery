package pl.com.kocielapki.cattery.cattery.data;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.kocielapki.cattery.cattery.logic.*;
import pl.com.kocielapki.cattery.cattery.api.*;
import pl.com.kocielapki.cattery.domain.DictionaryData;
import pl.com.kocielapki.cattery.cattery.logic.DictionariesService;
import pl.com.kocielapki.cattery.cattery.logic.DictionaryType;
import pl.com.kocielapki.cattery.cattery.logic.utils.LanguagesUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class Endpoint {
    private SpeciesService speciesService;
    private BirthService birthService;
    private AnimalService animalService;
    private AnimalMedicineTherapyService animalMedicineTherapyService;
    private AnimalDiseaseService animalDiseaseService;
    private BreedService breedService;
    private CustomerService customerService;
    private DiseaseService diseaseService;
    private ImageService imageService;
    private MedicineService medicineService;
    private TransactionService transactionService;
    private TransactionCategoryService transactionCategoryService;
    private TransactionSubcategoryService transactionSubcategoryService;
    private ContactFormService contactFormService;
    private DictionariesService dictionariesService;

    public Endpoint(SpeciesService speciesService, BirthService birthService, AnimalService animalService, AnimalMedicineTherapyService animalMedicineTherapyService, AnimalDiseaseService animalDiseaseService, BreedService breedService, CustomerService customerService, DiseaseService diseaseService, ImageService imagesService, MedicineService medicineService, TransactionService transactionService, TransactionCategoryService transactionCategoryService, TransactionSubcategoryService transactionSubcategoryService, ContactFormService contactFormService, DictionariesService dictionariesService) {
        this.speciesService = speciesService;
        this.birthService = birthService;
        this.animalService = animalService;
        this.animalMedicineTherapyService = animalMedicineTherapyService;
        this.animalDiseaseService = animalDiseaseService;
        this.breedService = breedService;
        this.customerService = customerService;
        this.diseaseService = diseaseService;
        this.imageService = imagesService;
        this.medicineService = medicineService;
        this.transactionService = transactionService;
        this.transactionCategoryService = transactionCategoryService;
        this.transactionSubcategoryService = transactionSubcategoryService;
        this.contactFormService = contactFormService;
        this.dictionariesService = dictionariesService;
    }

    @GetMapping(path = "/api/species/{id}", produces = "application/json; charset=UTF-8")
    public Species getSpecies(@PathVariable Long id) {
        return speciesService.get(id);
    }

    @GetMapping(path = "admin/api/birth/{id}", produces = "application/json; charset=UTF-8")
    public BirthRest getBirth(@PathVariable Long id) {
        Birth birth = birthService.get(id);
        return new BirthRest(birth);
    }

    @GetMapping(path = "admin/api/births", produces = "application/json; charset=UTF-8")
    public List<BirthRest> getBirths(
            @RequestParam(name = "mother_id", required = false) Long motherId,
            @RequestParam(name = "father_id", required = false) Long fatherId,
            @RequestParam(name = "birth_name", required = false) String birthName,
            @RequestParam(name = "website_visibility_status", required = false) String websiteVisibilityStatus,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize) {
        List<Birth> births = birthService.findBy(new BirthFilter(motherId, fatherId, birthName, websiteVisibilityStatus, page, pageSize));
        return birthsToResponses(births);
    }

    @PostMapping(path = "/admin/api/birth")
    public void createBirth(@RequestParam("file") MultipartFile file,
                            @RequestParam("name") String name,
                            @RequestParam("birth_date") String birthDate,
                            @RequestParam("mother_id") Long motherId,
                            @RequestParam("father_id") Long fatherId,
                            @RequestParam("note") String note,
                            @RequestParam("website_description") String websiteDescription,
                            @RequestParam("website_details_description") String websiteDetailsDescription,
                            @RequestParam("website_visibility_status") String websiteVisibilityStatus) {

        BirthRest request = new BirthRest(name, birthDate, motherId, fatherId, note, websiteDescription, websiteDetailsDescription, websiteVisibilityStatus, file);
        birthService.create(request);
    }

    @PutMapping(path = "/admin/api/birth/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateBirth(@PathVariable Long id, @RequestBody BirthRest request) {
        birthService.update(request);
    }

    @PutMapping("/admin/api/birth/{id}/photo")
    public void updateBirthPhoto(@RequestParam("file") MultipartFile image, @PathVariable Long id) {
        birthService.updatePhoto(id, image);
    }

    @PutMapping("/admin/api/birth/{id}/details_photo")
    public void addBirthPhotos(@RequestParam("file") MultipartFile image, @PathVariable Long id) {
        birthService.addDetailsPhoto(id, image);
    }

    @GetMapping(path = "/api/animal_disease/{id}", produces = "application/json; charset=UTF-8")
    public AnimalDisease getAnimalDisease(@PathVariable Long id) {
        return animalDiseaseService.get(id);
    }

    @GetMapping(path = "/api/animal_medicine_therapy/{id}", produces = "application/json; charset=UTF-8")
    public AnimalMedicineTherapy getAnimalMedicineTherapy(@PathVariable Long id) {
        return animalMedicineTherapyService.get(id);
    }

    @GetMapping(path = "/api/breed/{id}", produces = "application/json; charset=UTF-8")
    public Breed getBreed(@PathVariable Long id) {
        return breedService.get(id);
    }

    @GetMapping(path = "admin/api/breeds", produces = "application/json; charset=UTF-8")
    public List<Breed> findBreeds(@RequestParam(name = "species_id", required = false) Long speciesId) {
        return breedService.findBy(new BreedFilter(speciesId));
    }

    @GetMapping(path = "admin/api/customer/{id}", produces = "application/json; charset=UTF-8")
    public CustomerRest getCustomer(@PathVariable Long id) {
        Customer customer = customerService.get(id);
        return new CustomerRest(customer);
    }

    @GetMapping(path = "/admin/api/customers", produces = "application/json; charset=UTF-8")
    public List<CustomerRest> findCustomers(
            @RequestParam(name = "first_name", required = false) String firstName,
            @RequestParam(name = "last_name", required = false) String lastName,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false) Long pageSize) {

        List<Customer> customers = customerService.findBy(new CustomerFilter(firstName, lastName, city, page, pageSize));
        return customersToResponses(customers);
    }

    @PostMapping(path = "/admin/api/customer", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void createCustomer(@RequestBody CustomerRest request) {
        customerService.create(request);
    }

    @PutMapping(path = "/admin/api/customer/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateCustomer(@PathVariable Long id, @RequestBody CustomerRest request) {
        customerService.update(request);
    }

    @GetMapping(path = "/api/disease/{id}", produces = "application/json; charset=UTF-8")
    public Disease getDisease(@PathVariable Long id) {
        return diseaseService.get(id);
    }


    @GetMapping(path = "/api/medicine/{id}", produces = "application/json; charset=UTF-8")
    public Medicine getMedicine(@PathVariable Long id) {
        return medicineService.get(id);
    }

    @GetMapping(path = "/admin/api/transaction/{id}", produces = "application/json; charset=UTF-8")
    public TransactionRest getTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.get(id);
        return  new TransactionRest(transaction);
    }

    @PutMapping(path = "/admin/api/transaction/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateTransaction (@PathVariable Long id, @RequestBody TransactionRest request) {
        transactionService.update(request);
    }

    @GetMapping(path = "/admin/api/transactions", produces = "application/json; charset=UTF-8")
    public List<Transaction> findTransactions(@RequestParam(name = "first_name", required = false) String customerFirstName,
                                              @RequestParam(name = "last_name", required = false) String customerLastName,
                                              @RequestParam(name = "animal_name", required = false) String animalName,
                                              @RequestParam(name = "lineage_name", required = false) String animalLineageName,
                                              @RequestParam(name = "sale_status", required = false) String animalSaleStatus,
                                              @RequestParam(name = "transaction_status", required = false) String transactionStatus,
                                              @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                                              @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize) {

       return transactionService.findBy(new TransactionFilter(customerFirstName, customerLastName, animalName, animalLineageName, animalSaleStatus, transactionStatus, page, pageSize, new TransactionCategory("Sprzedaż kota", "i")));
    }

    @PostMapping(path = "/admin/api/transaction", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void createTransaction(@RequestBody TransactionRest request) {

        transactionService.create(request);
    }

    @GetMapping(path = "/api/transaction_category/{id}", produces = "application/json; charset=UTF-8")
    public TransactionCategory getTransactionCategory(@PathVariable Long id) {
        return transactionCategoryService.get(id);
    }

    @GetMapping(path = "/api/transaction_categories", produces = "application/json; charset=UTF-8")
    public List<TransactionCategory> getTransactionCategories() {
        return transactionCategoryService.findAll();
    }

    @GetMapping(path = "/api/transaction_subcategory/{id}", produces = "application/json; charset=UTF-8")
    public TransactionSubcategory getTransactionSubcategory(@PathVariable Long id) {
        return transactionSubcategoryService.get(id);
    }

    @GetMapping(path = "/api/transaction_subcategories", produces = "application/json; charset=UTF-8")
    public List<TransactionSubcategory> getTransactionSubcategories() {
        return transactionSubcategoryService.findAll();
    }

    @PostMapping(path = "/api/contact_form", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void createContact(@RequestBody ContactFormRest request) {
        contactFormService.create(new ContactForm(request));
    }


    @GetMapping(path = "/admin/api/contacts", produces = "application/json; charset=UTF-8")
    public List<ContactFormRest> getContacts(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "served", required = false) Boolean served
    ) {
        List<ContactForm> contactForms = contactFormService.findBy(new ContactFormFilter(name, served, page, pageSize));
        return contactFormsToResponses(contactForms);
    }

    @GetMapping(path = "/admin/api/contact/{id}", produces = "application/json; charset=UTF-8")
    public ContactFormRest getContact(@PathVariable Long id) {
        ContactForm contactForm = contactFormService.get(id);
        return new ContactFormRest(contactForm);
    }

    @DeleteMapping("/admin/api/contact/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactFormService.delete(id);
    }

    @PutMapping(path = "/admin/api/contact/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateContact(@PathVariable Long id, @RequestBody ContactFormRest request) {
        contactFormService.update(id, new ContactForm(request.getServed()));
    }

    @GetMapping(path = "/admin/api/animals", produces = "application/json; charset=UTF-8")
    public List<AnimalRest> getAnimals(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "lineage_name", required = false) String lineageName,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "cattery_status", required = false) String catteryStatus,
            @RequestParam(name = "sale_status", required = false) String saleStatus,
            @RequestParam(name = "website_visibility_status", required = false) String websiteVisibilityStatus,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false) Long pageSize
    ) {

        List<Animal> animals = animalService.findBy(new AnimalFilter(name, lineageName, gender, catteryStatus, saleStatus, websiteVisibilityStatus, page, pageSize));
        return animalsToResponses(animals);
    }

    @GetMapping(path = "/admin/api/animal/{id}", produces = "application/json; charset=UTF-8")
    public AnimalRest getAnimal(@PathVariable Long id) {
        Animal animal = animalService.get(id);
        return new AnimalRest(animal);
    }

    @PostMapping(path = "/admin/api/animal")
    public void createAnimal(@RequestParam("file") MultipartFile file,
                             @RequestParam("name") String name,
                             @RequestParam("breed_id") Long breedId,
                             @RequestParam("birth_id") Long birthId,
                             @RequestParam("lineage_name") String lineageName,
                             @RequestParam("gender_code") String genderCode,
                             @RequestParam("birth_date") String birthDate,
                             @RequestParam("color") String color,
                             @RequestParam("chip_number") String chipNumber,
                             @RequestParam("lineage_number") String lineageNumber,
                             @RequestParam("weight") BigDecimal weight,
                             @RequestParam("cattery_status_code") String catteryStatusCode,
                             @RequestParam("mother_id") Long motherId,
                             @RequestParam("father_id") Long fatherId,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("sale_status_code") String saleStatusCode,
                             @RequestParam("note") String note,
                             @RequestParam("website_description") String websiteDescription,
                             @RequestParam("website_visibility_status") String websiteVisibilityStatus
    ) {

        AnimalRest request = new AnimalRest(name, breedId, lineageName, genderCode, birthDate, color, chipNumber, lineageNumber, weight, catteryStatusCode, motherId, fatherId, price, saleStatusCode, note, websiteDescription, websiteVisibilityStatus, birthId, file);
        animalService.create(request);

    }


    @PutMapping(path = "/admin/api/animal/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateAnimal(@PathVariable Long id, @RequestBody AnimalRest request) {
        animalService.update(request);
    }

    @PutMapping("/admin/api/animal/{id}/photo")
    public void updatePhoto(@RequestParam("file") MultipartFile image, @PathVariable Long id) {
        animalService.updatePhoto(id, image);
    }

    @GetMapping(path = "admin/api/dictionary/{name}", produces = "application/json; charset=UTF-8")
    public List<DictionaryData> getDictionary(@PathVariable String name) {
        return dictionariesService.getDictionary(DictionaryType.valueOf(name.toUpperCase()), LanguagesUtil.getCurrentLanguage());
    }

    @GetMapping(
            value = "/admin/animal/file/{name}",
            produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] getAnimalFile(@PathVariable(name = "name") String fileName) throws IOException {
            return imageService.getImage(fileName);

    }

    @GetMapping(
            value = "/admin/birth/file/{name}",
            produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] getBirthFile(@PathVariable(name = "name") String fileName) throws IOException {
        return imageService.getImage(fileName);
    }


    private List<ContactFormRest> contactFormsToResponses(List<ContactForm> contactForms) {
        List<ContactFormRest> list = new ArrayList<>();
        for (ContactForm contactForm : contactForms) {
            list.add(new ContactFormRest(contactForm));
        }
        return list;
    }

    private List<AnimalRest> animalsToResponses(List<Animal> animals) {
        List<AnimalRest> list = new ArrayList<>();
        for (Animal animal : animals) {
            list.add(new AnimalRest(animal));
        }
        return list;
    }

    private List<BirthRest> birthsToResponses(List<Birth> births) {
        List<BirthRest> list = new ArrayList<>();
        for (Birth birth : births) {
            list.add(new BirthRest(birth));
        }
        return list;
    }

    private List<CustomerRest> customersToResponses(List<Customer> customers) {
        List<CustomerRest> list = new ArrayList<>();
        for (Customer customer : customers) {
            list.add(new CustomerRest(customer));
        }
        return list;
    }
}
