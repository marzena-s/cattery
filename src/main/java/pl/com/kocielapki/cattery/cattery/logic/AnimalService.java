package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.kocielapki.cattery.cattery.api.AnimalRest;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.AnimalFilter;
import pl.com.kocielapki.cattery.cattery.data.Birth;
import pl.com.kocielapki.cattery.cattery.data.Image;
import pl.com.kocielapki.cattery.cattery.repo.AnimalRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class AnimalService {
    private AnimalRepository animalRepository;
    private AnimalSearch animalSearch;
    private CacheService cacheService;
    private BreedService breedService;
    private ImageService imageService;
    private BirthService birthService;

    public AnimalService(AnimalRepository animalRepository, AnimalSearch animalSearch, CacheService cacheService, BreedService breedService, ImageService imageService, @Lazy BirthService birthService) {
        this.animalRepository = animalRepository;
        this.animalSearch = animalSearch;
        this.cacheService = cacheService;
        this.breedService = breedService;
        this.imageService = imageService;
        this.birthService = birthService;
    }

    public Animal get(Long id) {
        return animalRepository.findById(id).get();
    }

    public void create(AnimalRest request) {
        validateAnimalData(request);

        Animal animal = new Animal(request);
        createAndSetImage(request, animal);
        validateWebsiteStatus(animal);
        setSaleStatus(request, animal);
        animal.setBreed(breedService.get(request.getBreedId()));
        Birth birth =  getBirthAndSetFields(request, animal);
        animal.setBirth(birth);
        animalRepository.save(animal);
        cacheService.invalidateDictionaries();
    }

    private void createAndSetImage(AnimalRest request, Animal animal) {
        String imageName;
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            imageName = imageService.create(request.getFile());
            animal.setImage(imageService.findImageByName(imageName));
        }
    }

    private void setSaleStatus(AnimalRest request, Animal animal) {
        if(!request.getCatteryStatusCode().equals(CatteryStatus.FOR_SALE.getValue())){
            animal.setSaleStatusCode(SaleStatus.NONE.getValue());
        } else{
            animal.setSaleStatusCode(SaleStatus.FREE.getValue());
        }
    }

    private Birth getBirthAndSetFields(AnimalRest request, Animal animal) {
        Birth birth;
        if (request.getMotherId() == 0 && request.getFatherId() == 0) {
            birth = null;
            validateIfIsNullOrEmpty(request.getBirthDate(), AnimalDataName.BIRTH_DATE.getValue());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
            animal.setBirthDate(LocalDate.parse(request.getBirthDate(), formatter));
        } else {
            animal.setMother(get(request.getMotherId()));
            animal.setFather(get(request.getFatherId()));
            birth = getBirth(request);
            animal.setBirthDate(birth.getBirthDate());
        }
        return birth;
    }

    private Birth getBirth(AnimalRest request) {
        Birth birth;
        if (request.getBirthId() == null) {
            throw new IllegalArgumentException("Brak pasującego miotu do rodziców. Stwórz miot lub wybierz innych rodziców.");
        } else {
            birth = birthService.get(request.getBirthId());
        }
        return birth;
    }


    public void update(AnimalRest request) {
        Animal animalToUpdate;
        if (SourceUpdateStatus.DELETE.getValue().equals(request.getSource())) {
            animalToUpdate = get(request.getId());
            validateAnimalSaleStatus(animalToUpdate);
            animalToUpdate.setDeleteDateTime(LocalDateTime.now());
        } else {
            validateAnimalData(request);
            Image animalImage = getImage(request);
            animalToUpdate = new Animal(request);
            validateParents(request, animalToUpdate);
            animalToUpdate.setImage(animalImage);
            animalToUpdate.setBreed(breedService.get(request.getBreedId()));
            Birth birth = getBirthAndSetFields(request, animalToUpdate);
            validateWebsiteStatus(animalToUpdate);
            animalToUpdate.setBirth(birth);
        }
        animalRepository.save(animalToUpdate);
    }

    private Image getImage(AnimalRest request) {
        Animal animalToUpdate = get(request.getId());
        return animalToUpdate.getImage();
    }

    private void validateParents(AnimalRest request, Animal animalToUpdate) {
        if(request.getMotherId().equals(animalToUpdate.getId()) || request.getFatherId().equals(animalToUpdate.getId())){
            throw new IllegalArgumentException("Nie można wybrać siebie jako rodzica");
        }
    }

    private void validateAnimalSaleStatus(Animal animal) {
        if(animal.getSaleStatusCode().equals(SaleStatus.SOLD.getValue()) || animal.getSaleStatusCode().equals(SaleStatus.RESERVED.getValue())){
            throw new IllegalArgumentException("Nie można usunąć sprzedanego i zarezerwowanego kota - zacznij od anulacji sprzedaży");
        }
    }

    public List<Animal> findBy(AnimalFilter filter) {
        return animalSearch.findBy(filter);
    }

    public Long countBy(Long birthId) {
        return animalSearch.countByBirthId(birthId);
    }

    public void updatePhoto(Long animalId, MultipartFile image) {
        validateFile(image);
        Animal animal = get(animalId);
        if(animal.getImage()!= null) {
            String oldFileName = animal.getImage().getImageFileName();
            imageService.deleteImageFromServer(oldFileName);
            imageService.deleteImage(oldFileName);
        }
        String newFileName = imageService.create(image);
        Image newImage = imageService.findImageByName(newFileName);
        animal.setImage(newImage);
        animalRepository.save(animal);
    }

    private void validateFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Musisz wybrać zdjęcie");
        }
    }

    private void validateAnimalData(AnimalRest request) {
        validateIfIsNullOrEmpty(request.getBreedId(), AnimalDataName.BREED.getValue());
        validateIfIsNullOrEmpty(request.getName(), AnimalDataName.NAME.getValue());
        validateMaxLength(request.getName(), 30L, AnimalDataName.NAME.getValue());
        validateIfIsNullOrEmpty(request.getGenderCode(), AnimalDataName.GENDER.getValue());
        validateIfIsNullOrEmpty(request.getCatteryStatusCode(), AnimalDataName.CATTERY_STATUS.getValue());
        validateIfIsNullOrEmpty(request.getSaleStatusCode(), AnimalDataName.SALE_STATUS.getValue());
    }

    private void validateWebsiteStatus(Animal animal) {
        if(animal.getWebsiteVisibilityStatus().equals(WebsiteVisibilityStatus.VISIBLE.getValue())){
            if(animal.getLineageName() == null || "".equals(animal.getLineageName())){
                throw new IllegalArgumentException("Uzupełnij nazwę z rodowodu, gdy status na stronę jest widoczny");
            }
            if(animal.getWebsiteDescription() == null || "".equals(animal.getWebsiteDescription())){
                throw new IllegalArgumentException("Uzupełnij opis na stronę, gdy status na stronie jest widoczny");
            }
            if(animal.getImage() == null){
                throw new IllegalArgumentException("Uzupełnij zdjęcie główne, gdy status na stronie jest widoczny");
            }
        }
    }

    private void validateIfIsNullOrEmpty(String data, String dataName) {
        if (data == null || "".equals(data)) {
            throw new IllegalArgumentException("Uzupełnij pole: " + dataName);
        }
    }

    private void validateIfIsNullOrEmpty(Long data, String dataName) {
        if (data == null) {
            throw new IllegalArgumentException("Uzupełnij pole: " + dataName);
        }
    }

    private void validateMaxLength(String data, Long maxLength, String dataName) {
        if (data.length() > maxLength) {
            throw new IllegalArgumentException(dataName + " zawiera zbyt wiele znaków, max " + maxLength + " znaków");
        }
    }

}
