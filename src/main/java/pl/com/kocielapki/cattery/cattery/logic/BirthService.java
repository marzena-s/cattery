package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.kocielapki.cattery.cattery.api.BirthRest;
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
        validateWebsiteStatus(request);
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
        Birth birthToUpdate = get(request.getId());
        if (SourceUpdateStatus.DELETE.getValue().equals(request.getSource())) {
            validateAmountOfAnimals(birthToUpdate, SourceUpdateStatus.DELETE.getValue());
            birthToUpdate.setDeleteDateTime(LocalDateTime.now());
        } else if (SourceUpdateStatus.UPDATE.getValue().equals(request.getSource())) {
            validateData(request);
            Image birthImage = birthToUpdate.getImage();
            Set<Image> birthDetailImages = birthToUpdate.getBirthsImages();
            birthToUpdate = new Birth(request);
            validateAmountOfAnimals(birthToUpdate, SourceUpdateStatus.UPDATE.getValue());
            validateWebsiteStatus(request);
            birthToUpdate.setMother(animalService.get(request.getMotherId()));
            birthToUpdate.setFather(animalService.get(request.getFatherId()));
            birthToUpdate.setImage(birthImage);
            birthToUpdate.setBirthsImages(birthDetailImages);
        } else if (SourceUpdateStatus.DELETE_BIRTH_IMAGE.getValue().equals(request.getSource())) {
            Set<Image> images = birthToUpdate.getBirthsImages();
            Image imageToDelete = imageService.get(request.getImageToDeleteId());
            images.removeIf(e -> e.equals(imageToDelete));
            birthToUpdate.setBirthsImages(images);
            imageService.deleteImage(imageToDelete.getImageFileName());
            imageService.deleteImageFromServer(imageToDelete.getImageFileName());
        }
        birthRepository.save(birthToUpdate);
    }

    private void validateData(BirthRest request) {
        validateName(request.getName(), "Nazwa miotu");
        validateDate(request);

    }

    private void validateWebsiteStatus(BirthRest request) {
        if (request.getWebsiteVisibilityStatus().equals(WebsiteVisibilityStatus.VISIBLE.getValue())) {
            if (request.getName() == null || "".equals(request.getName())) {
                throw new IllegalArgumentException("Uzupe??nij nazw??");
            }
            if (request.getWebsiteDescription() == null || "".equals(request.getWebsiteDescription())) {
                throw new IllegalArgumentException("Uzupe??nij opis na stron??, gdy status na stronie jest widoczny");
            }
            if (request.getWebsiteDetailsDescription() == null || "".equals(request.getWebsiteDetailsDescription())) {
                throw new IllegalArgumentException("Uzupe??nij opis szczeg????owy na stron??, gdy status na stronie jest widoczny");
            }
            if (request.getFile() != null) {
                if ("".equals(request.getFile().getOriginalFilename())) {
                    throw new IllegalArgumentException("Uzupe??nij zdj??cie g????wne, gdy status na stronie jest widoczny");
                }
            }
        }
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
        if (birth.getImage() != null) {
            String oldFileName = birth.getImage().getImageFileName();
            imageService.deleteImageFromServer(oldFileName);
            imageService.deleteImage(oldFileName);
        }
        String newFileName = imageService.create(image);
        Image newImage = imageService.findImageByName(newFileName);
        birth.setImage(newImage);
        birthRepository.save(birth);
    }

    public void addDetailsPhoto(Long birthId, MultipartFile image) {
        validateFile(image);
        Birth birth = get(birthId);
        validateDetailsPhotosAmount(birth);
        String fileName = imageService.create(image);
        birth.getBirthsImages().add(imageService.findImageByName(fileName));
        birthRepository.save(birth);
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Musisz wybra?? zdj??cie");
        }
    }

    private void validateDetailsPhotosAmount(Birth birth) {
        Set<Image> images = birth.getBirthsImages();
        if (images.size() >= 6) {
            throw new IllegalArgumentException("Nie mo??na doda?? wi??cej zdj????.");
        }
    }

    private void validateDate(BirthRest request) {
        if (!request.getBirthDate().equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            checkIfDateIsCorrect(LocalDate.parse(request.getBirthDate(), formatter));
        } else {
            throw new IllegalArgumentException("Podaj dat?? urodzenia");
        }
    }

    private void validateAmountOfAnimals(Birth birth, String source) {
        Long animalAmount = animalService.countBy(birth.getId());
        if (SourceUpdateStatus.DELETE.getValue().equals(source) && animalAmount > 0) {
            throw new IllegalArgumentException("Nie mo??na usun???? miotu, do kt??rego nale???? zwierz??ta. Zacznij od usuni??cia zwierzak??w.");
        }
    }

    public void validateName(String name, String nameDescription) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Podaj nazw?? miotu");
        }

        validateMaxLength(name, 20L, nameDescription);
    }

    private void validateMaxLength(String data, Long maxLength, String dataName) {
        if (data.length() > maxLength) {
            throw new IllegalArgumentException(dataName + " zawiera zbyt wiele znak??w, max " + maxLength + " znak??w");
        }
    }

    private void checkIfDateIsCorrect(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Podaj dat?? urodzenia");
        }
        if (date.isAfter(LocalDate.now()) && !date.isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Data urodzenia nie mo??e by?? p????niejsza ni?? dzisiejsza");
        }
    }
}
