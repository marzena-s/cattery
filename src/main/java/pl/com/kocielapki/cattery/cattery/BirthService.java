package pl.com.kocielapki.cattery.cattery;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.kocielapki.cattery.cattery.api.AnimalRest;
import pl.com.kocielapki.cattery.cattery.api.BirthRest;
import pl.com.kocielapki.cattery.cattery.data.Animal;
import pl.com.kocielapki.cattery.cattery.data.Birth;
import pl.com.kocielapki.cattery.cattery.data.BirthFilter;
import pl.com.kocielapki.cattery.cattery.data.Image;
import pl.com.kocielapki.cattery.cattery.repo.BirthRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BirthService {
    private BirthRepository birthRepository;
    private BirthSearch birthSearch;
    private AnimalService animalService;
    private ImageService imageService;

    public BirthService(BirthRepository birthRepository, BirthSearch birthSearch, @Lazy AnimalService animalService, ImageService imageService) {
        this.birthRepository = birthRepository;
        this.birthSearch = birthSearch;
        this.animalService = animalService;
        this.imageService = imageService;
    }

    public Birth get(Long id) {
        Birth birth = birthRepository.findById(id).get();
        birth.setAmount(animalService.countBy(id));
        return birth;
    }

    public void create(BirthRest request) {
        validateData(request);
        Birth birth = new Birth(request);
        createAndSetImage(request, birth);
        birth.setMother(animalService.get(request.getMotherId()));
        birth.setFather(animalService.get(request.getFatherId()));
        birth.setAmount(0L);
        birthRepository.save(birth);
    }

    public List<Birth> findBy(BirthFilter filter) {
        List<Birth> births = birthSearch.findBy(filter);
        for (Birth birth : births) {
            birth.setAmount(animalService.countBy(birth.getId()));
        }
        return births;
    }


    public void update(BirthRest request) {
        Birth birthToUpdate;
        if (SourceUpdateStatus.DELETE.getValue().equals(request.getSource())) {
            birthToUpdate = get(request.getId());
            validateAmountOfAnimals(birthToUpdate, SourceUpdateStatus.DELETE.getValue());
            birthToUpdate.setDeleteDateTime(LocalDateTime.now());
        } else {
            validateData(request);
            Image birthImage = getImage(request);
            birthToUpdate = new Birth(request);
            validateAmountOfAnimals(birthToUpdate, SourceUpdateStatus.UPDATE.getValue());
            birthToUpdate.setMother(animalService.get(request.getMotherId()));
            birthToUpdate.setFather(animalService.get(request.getFatherId()));
            birthToUpdate.setImage(birthImage);
        }
        birthRepository.save(birthToUpdate);
    }

    private Image getImage(BirthRest request) {
        Birth birthToUpdate = get(request.getId());
        return birthToUpdate.getImage();
    }


    private void validateData(BirthRest request) {
        validateName(request.getName(), "Nazwa miotu");
        validateDate(request);

    }

    private void createAndSetImage(BirthRest request, Birth birth) {
        String imageName;
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            imageName = imageService.create(request.getFile());
            birth.setImage(imageService.findImageByName(imageName));
        }
    }

    public void updatePhoto(Long birthId, MultipartFile image) {
        validateFile(image);
        Birth birth = get(birthId);
        if(birth.getImage()!= null) {
            String oldFileName = birth.getImage().getImageFileName();
            imageService.deleteImageFromServer(oldFileName);
            imageService.deleteImage(oldFileName);
        }
        String newFileName = imageService.create(image);
        Image newImage = imageService.findImageByName(newFileName);
        birth.setImage(newImage);
        birthRepository.save(birth);
    }

    public void updatePhotos(Long birthId, MultipartFile image) {
        validateFile(image);
        Birth birth = get(birthId);
        if(birth.getImage()!= null) {
            String oldFileName = birth.getImage().getImageFileName();
            imageService.deleteImageFromServer(oldFileName);
            imageService.deleteImage(oldFileName);
        }
        String newFileName = imageService.create(image);
        Image newImage = imageService.findImageByName(newFileName);
        birth.setImage(newImage);
        birthRepository.save(birth);
    }

    private void validateFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Musisz wybrać zdjęcie");
        }
    }

    private void validateDate(BirthRest request) {
        if (!request.getBirthDate().equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            checkIfDateIsCorrect(LocalDate.parse(request.getBirthDate(), formatter));
        } else {
            throw new IllegalArgumentException("Podaj datę urodzenia");
        }
    }

    private void validateAmountOfAnimals(Birth birth, String source) {
        Long animalAmount = animalService.countBy(birth.getId());
             if (SourceUpdateStatus.DELETE.getValue().equals(source) && animalAmount > 0) {
                throw new IllegalArgumentException("Nie można usunąć miotu, do którego należą zwierzęta. Zacznij od usunięcia zwierzaków.");
            }
    }

//    private void validateAmount(Long amount) {
//        if (amount == null) {
//            throw new IllegalArgumentException("Podaj ilość kotów w miocie");
//        }
//        if (amount <= 0 || amount > 15) {
//            throw new IllegalArgumentException("Podaj poprawną ilość kotów w miocie");
//        }
//    }

    public void validateName(String name, String nameDescription) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Podaj nazwę miotu");
        }

        validateMaxLength(name, 20L, nameDescription);
    }

    private void validateMaxLength(String data, Long maxLength, String dataName) {
        if (data.length() > maxLength) {
            throw new IllegalArgumentException(dataName + " zawiera zbyt wiele znaków, max " + maxLength + " znaków");
        }
    }


    private void checkIfDateIsCorrect(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Podaj datę urodzenia");
        }
        if (date.isAfter(LocalDate.now()) && !date.isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Data urodzenia nie może być późniejsza niż dzisiejsza");
        }
    }
}
